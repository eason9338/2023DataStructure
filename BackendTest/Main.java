import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        // String rootUrl = "https://www.104.com.tw/jobs/main/students/?tab=parttime&keyword=%E5%AF%A6%E7%BF%92&ro=2&pagesize=50&jobsource=students_PC&showDutyTime=1&hotJob=0&order=12&asc=0"; 
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

        // // 打印網址列表
        // webTree.setPostOrderScore(keywordList);
        // //System.out.println(webTree.eulerPrintTree());
        // webCrawler.printList();
        
        //上面的部分output會是Total Score: 1.2
        //https://www.104.com.tw/jobs/main/

        //google api執行爬蟲部分，By 李淳澔，要上面功能救註解掉下面的
        //SearchResultHandler searchResultHandler = new SearchResultHandler("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");
        // ArrayList<SearchResultItem> results = searchResultHandler.search("實習北部");
        // for(SearchResultItem item: results) {
        //     System.out.print(item.getTitle());
        //     System.out.println(item.getLink());
        // }

        SearchResultHandler searchResultHandler = new SearchResultHandler("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");
        ArrayList<SearchResultItem> results = searchResultHandler.search("實習");
        // for(SearchResultItem result: results) {
        //     System.out.print(result.getTitle() + " ");
        //     System.out.println(result.getLink());
        // }
        // 創建 WebPage 對象的列表

        ArrayList<WebPage> webPages = new ArrayList<WebPage>();
        ArrayList<WebPage> highScorePages = new ArrayList<WebPage> (); // 存儲分數高於閾值的頁面

        for(SearchResultItem result: results) {
            WebPage page = new WebPage(result.getLink());
            webPages.add(page);
        }

        // 使用 WebList 類來排序
        WebList webList = new WebList(webPages);

        for(WebPage w: webPages){
            WebCrawler webCrawler = new WebCrawler(w.getUrl(), keywordList);
           
            // 獲取建立的 WebTree
            WebTree webTree = webCrawler.getWebTree();

            // 使用 WebTree 中的方法計算分數
            try {
                webTree.setPostOrderScore(keywordList);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 獲取計算後的總分
            double totalScore = Math.round(webTree.getTotalScore() * 10.0) / 10.0;
            System.out.println("總分: " + totalScore);

            //加入高分子網頁
            for(WebPage web: webCrawler.getHighScorePages()){
                highScorePages.add(web);
            }
        }
        // 對 WebList 實例進行排序
        webList.sortByScore();
    }
}
