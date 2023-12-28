import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String rootUrl = "https://www.104.com.tw/jobs"; 
        ArrayList<Keyword> keywordList = new ArrayList<Keyword>();

        try {
            File file = new File("Keywords.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String name = sc.next();
                float weight = Float.parseFloat(sc.next());
                Keyword newKeyword = new Keyword(name, weight);
                keywordList.add(newKeyword);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        }


        WebCrawler webCrawler = new WebCrawler("https://www.104.com.tw/jobs/main/");

        // 獲取建立的 WebTree 實例
        WebTree webTree = webCrawler.getWebTree();

        // 使用 WebTree 中的方法計算分數
        try {
            webTree.setPostOrderScore(keywordList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 獲取計算後的總分
        double totalScore = webTree.getTotalScore();
        System.out.println("Total Score: " + totalScore);

        // 打印網址列表
        webTree.setPostOrderScore(keywordList);
        //System.out.println(webTree.eulerPrintTree());
        webCrawler.printList();
    }
}
