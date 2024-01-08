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

        // 存儲所有子節點 URL 的列表
        ArrayList<String> allChildrenUrls = new ArrayList<>();
        
        for(WebPage w: webPages){
            WebCrawler webCrawler = new WebCrawler(w.getUrl(), keywordList);
            WebTree webTree = webCrawler.getWebTree();

            // 計算每個頁面的分數
            try {
                webTree.setPostOrderScore(keywordList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 為每個 SearchResultItem 設置子節點
            for (SearchResultItem resultItem : results) {
                if (resultItem.getLink().equals(w.getUrl())) {
                    ArrayList<String> childrenUrls = webTree.root.getChildren();
                    resultItem.setChild(childrenUrls);
                    allChildrenUrls.addAll(childrenUrls); // 將子節點 URL 添加到總列表中
                    break;
                }
            }
        }

        // 對結果進行處理並顯示
        processWebPages(highScorePages, "以下為高分子網頁:", results, allChildrenUrls);
        processWebPages(webPages, "以下為一般網頁", results, allChildrenUrls);
        //把一開始googlesearch到的array加到最後
    }

    private static void processWebPages(ArrayList<WebPage> webPages, String header, ArrayList<SearchResultItem> results, ArrayList<String> childrenUrls) {
        System.out.println(header);
        
        for (SearchResultItem item : results) {
            System.out.println("標題: " + item.getTitle());
            System.out.println("連結: " + item.getLink());

            // 打印子節點 URL
            if (item.getChildren() != null) {
                for (String childUrl : item.getChildren()) {
                    System.out.println("子節點 URL: " + childUrl);
                }
            }

            System.out.println(); // 增加一個空白行以分隔不同的結果
        }

        // 打印所有子節點的 URL
        System.out.println("所有子節點 URL:");
        for (String childUrl : childrenUrls) {
            System.out.println(childUrl);
        }
    }
}
