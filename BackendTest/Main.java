import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        // String rootUrl = "http://soslab.nccu.edu.tw/Courses.html"; 
        // ArrayList<Keyword> keywordList = new ArrayList<Keyword>();

        // try {
        //     File file = new File("Keywords.txt");
        //     Scanner sc = new Scanner(file);
        //     while (sc.hasNext()) {
        //         String name = sc.next();
        //         float weight = Float.parseFloat(sc.next());
        //         Keyword newKeyword = new Keyword(name, weight);
        //         keywordList.add(newKeyword);
        //     }
        //     sc.close();
        // } catch (FileNotFoundException e) {
        //     System.out.println("找不到文件");
        // }


        // WebCrawler webCrawler = new WebCrawler(rootUrl);

        // // 獲取建立的 WebTree 實例
        // WebTree webTree = webCrawler.getWebTree();

        // // 使用 WebTree 中的方法計算分數
        // try {
        //     webTree.setPostOrderScore(keywordList);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // // 獲取計算後的總分
        // double totalScore = webTree.getTotalScore();
        // System.out.println("Total Score: " + totalScore);

        // // 打印網址列表
        // webTree.setPostOrderScore(keywordList);
        // //System.out.println(webTree.eulerPrintTree());
        // webCrawler.printList();


        //google api執行爬蟲部分，By 李淳澔，要上面功能救註解掉下面的
        SearchResultHandler searchResultHandler = new SearchResultHandler("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");
        ArrayList<SearchResultItem> results = searchResultHandler.search("實習");
        for(SearchResultItem item: results) {
            System.out.print(item.getTitle());
            System.out.println(item.getLink());
        }
    }
}
