import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WordCounter {
    private String urlStr; // 目標 URL
    private String content; // 存儲 URL 內容

    // 輸入URL
    public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    //將 URL 轉為String
    private String fetchContent() throws IOException {
        URL url = new URL(this.urlStr); 
        URLConnection conn = url.openConnection(); 
        InputStream in = conn.getInputStream(); 
        BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
        StringBuilder retVal = new StringBuilder(); 

        String line;

        while ((line = br.readLine()) != null) {
            retVal.append(line).append("\n");
        }

        return retVal.toString(); 
    }

    //計算指定關鍵字在 URL 內容中出現的次數
    public int countKeyword(String keyword) throws IOException {
        if (content == null) {
            content = fetchContent(); //以防沒有抓到內容
        }

        content = content.toUpperCase(); 
        keyword = keyword.toUpperCase(); 

        int retVal = boyerMoore(content, keyword); // 使用 Boyer-Moore 來計算關鍵字出現次數

        return retVal; 
    }

    // Boyer-Moore 
    public int boyerMoore(String T, String P) {
        int i = P.length() - 1;
        int j = P.length() - 1;
        int l = 0;
        int matchCount = 0;


        while (i < T.length() - 1) {
            if (T.charAt(i) == P.charAt(j)) {
                if (j == 0) {
                    matchCount++;
                    i = i + P.length();
                } else {
                    i--;
                    j--;
                }
            } else {
                l = last(T.charAt(i), P);
                i = i + P.length() - min(j, 1 + l);
                j = P.length() - 1;
            }
        }

        if (matchCount > 0) {
            return matchCount;
        } else {
            return -1;
        }
    }

    private int last(char c, String P) {
        int lastIndex = -1;
        int index = P.indexOf(c);

        while (index != -1) {
            lastIndex = index;
            index = P.indexOf(c, index + 1);
        }

        return lastIndex; 
    }

    private int min(int a, int b) {
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else
            return a;
    }
}
