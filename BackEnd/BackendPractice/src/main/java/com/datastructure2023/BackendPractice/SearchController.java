package com.datastructure2023.BackendPractice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.datastructure2023.BackendPractice.service.SearchResultHandler;
import com.datastructure2023.BackendPractice.service.WebCrawler;
import com.datastructure2023.BackendPractice.service.WebList;
import com.datastructure2023.BackendPractice.model.Keyword;
import com.datastructure2023.BackendPractice.model.SearchResultItem;
import com.datastructure2023.BackendPractice.model.WebPage;
import com.datastructure2023.BackendPractice.model.WebTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;


@RestController
public class SearchController {

    @PostMapping("/searchResult")
    public ResponseEntity<?> search(@RequestBody String query) {
        try {
            ArrayList<Keyword> keywordList = new ArrayList<Keyword>();

            try {
                Resource resource = new ClassPathResource("Keyword.txt");
                InputStream inputStream = resource.getInputStream();
                Scanner sc = new Scanner(inputStream);
                while (sc.hasNext()) {
                    String name = sc.next();
                    float weight = Float.parseFloat(sc.next());
                    Keyword newKeyword = new Keyword(name, weight);
                    keywordList.add(newKeyword);
                }
                sc.close();
            } catch (IOException e) {
                System.out.println("找不到文件");
            }

            SearchResultHandler searchResultHandler = new SearchResultHandler();
            ArrayList<SearchResultItem> results = searchResultHandler.search("實習");

            ArrayList<WebPage> webPages = new ArrayList<WebPage>();
            ArrayList<WebPage> highScorePages = new ArrayList<WebPage>(); // 存儲分數高於閾值的頁面


            for(SearchResultItem result: results) {
                WebPage page = new WebPage(result.getLink());
                webPages.add(page);
                String url = result.getLink();
                WebCrawler webCrawler = new WebCrawler(url, keywordList);
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

            // 使用 WebList 類來排序
            WebList webList = new WebList(webPages);
            WebList highScoreList = new WebList(highScorePages);

            // 對 WebList 實例進行排序
            webList.sortByScore();
            highScoreList.sortByScore();

            ArrayList<SearchResultItem> finalResults = new ArrayList<>();
            finalResults.addAll(processWebPages(highScorePages, "以下為高分子網頁:"));
            finalResults.addAll(processWebPages(webPages, "以下為一般網頁"));
            System.out.println(finalResults); // 打印 finalResults 檢查內容
            return ResponseEntity.ok(finalResults);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("搜尋時發生錯誤: " + e.getMessage());
        }
    }

    private ArrayList<SearchResultItem> processWebPages(ArrayList<WebPage> webPages, String header) {
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
        System.out.println("完成");
        return resultsAfterSort;
    }
}