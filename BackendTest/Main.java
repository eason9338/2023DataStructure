import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
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

        SearchResultHandler searchResultHandler = new SearchResultHandler("AIzaSyDaMRJAuiClBmf_TatB_3PMtsKqiRt18XM", "e618816a42f6949e9");
        ArrayList<SearchResultItem> results = searchResultHandler.search("實習");

        ArrayList<WebPage> webPages = new ArrayList<WebPage>();
        ArrayList<WebPage> highScorePages = new ArrayList<WebPage>(); // 存儲分數高於閾值的頁面

        for(SearchResultItem result: results) {
            WebPage page = new WebPage(result.getLink());
            webPages.add(page);
        }

        // 使用 WebList 類來排序
        WebList webList = new WebList(webPages);
        WebList highScoreList = new WebList(highScorePages);

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
            //System.out.println("總分: " + totalScore);

            highScorePages.addAll(webTree.getHighScorePages(300, webTree.root));
        }
        // 對 WebList 實例進行排序
        webList.sortByScore();
        highScoreList.sortByScore();

        processWebPages(highScorePages, "以下為高分子網頁:");
        processWebPages(webPages, "以下為一般網頁");

    }

    private static void processWebPages(ArrayList<WebPage> webPages, String header) {
        System.out.println(header);
        ArrayList<SearchResultItem> resultsAfterSort = new ArrayList<>();

        for (WebPage w : webPages) {
            String url = w.getUrl();
            String title = "";

            try {
                Document doc = Jsoup.connect(url).get();
                title = doc.title();
            } catch (IOException e) {
                System.err.println("存取URL時發生錯誤: " + e.getMessage());
                title = "無法取得標題";
            }

            SearchResultItem item = new SearchResultItem(title, url);
            resultsAfterSort.add(item);
        }

        for (SearchResultItem item : resultsAfterSort) {
            System.out.println("標題: " + item.getTitle());
            System.out.println("連結: " + item.getLink());
            System.out.println(); // 增加一個空白行以分隔不同的結果
        }
    }
}
