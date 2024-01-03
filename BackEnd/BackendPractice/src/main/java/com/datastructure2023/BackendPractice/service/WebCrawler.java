package com.datastructure2023.BackendPractice.service;

import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.SecureRandom;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import com.datastructure2023.BackendPractice.model.WebPage;
import com.datastructure2023.BackendPractice.model.WebTree;
import com.datastructure2023.BackendPractice.model.WebNode;
import com.datastructure2023.BackendPractice.model.Keyword;


@Service
public class WebCrawler {

    private WebTree webTree; // 存儲建立的網頁樹結構
    private int maxDepth = 1; // 設置最大爬取深度
    private Set<String> allVisitedUrls; // 存儲所有訪問過的URL
    private ArrayList<Keyword> keywords; // 用於計算分數的關鍵詞列表
    private double scoreThreshold = 100; // 分數門檻
    private ArrayList<WebPage> highScorePages; // 存儲分數高於門檻的頁面
    private ForkJoinPool pool; // 用於並行處理的 ForkJoinPool

    public WebCrawler(String rootUrl, ArrayList<Keyword> keywords) {
        this.allVisitedUrls = new HashSet<>();
        this.keywords = keywords;
        this.highScorePages = new ArrayList<>();
        this.pool = new ForkJoinPool(); // 初始化 ForkJoinPool

        enableTrustAllCerts();

        WebPage rootPage = new WebPage(rootUrl);
        this.webTree = new WebTree(rootPage);
        CrawlTask task = new CrawlTask(rootUrl, this.webTree.root, 0);
        pool.invoke(task); // 使用 ForkJoinPool 開始任務
    }

    // 自定義的信任管理器，信任所有 SSL 證書
    private void enableTrustAllCerts() {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 檢查URL是否有效的方法
    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    // 內部類，用於並行處理網頁爬取任務的 RecursiveAction
    private class CrawlTask extends RecursiveAction {
        private String url;
        private WebNode parentNode;
        private int depth;

        public CrawlTask(String url, WebNode parentNode, int depth) {
            this.url = url;
            this.parentNode = parentNode;
            this.depth = depth;
        }

        @Override
        protected void compute() {
            if (depth > maxDepth || allVisitedUrls.contains(url) || !isValidUrl(url)) {
                return;
            }

            try {
                Document document = Jsoup.connect(url).get();
                WebPage currentPage = new WebPage(url);
                currentPage.setScore(keywords);

                if (currentPage.score > scoreThreshold) {
                    highScorePages.add(currentPage);
                }

                WebNode currentNode = new WebNode(currentPage);
                parentNode.addChild(currentNode);
                allVisitedUrls.add(url);

                List<CrawlTask> tasks = new ArrayList<>();
                Elements links = document.select("a[href]");
                for (Element link : links) {
                    String childUrl = link.absUrl("href");
                    CrawlTask task = new CrawlTask(childUrl, currentNode, depth + 1);
                    tasks.add(task);
                    task.fork();
                }

                for (CrawlTask task : tasks) {
                    task.join();
                }

            } catch (IOException e) {
                //System.err.println("訪問URL時出錯: " + url + " - " + e.getMessage());
                WebPage errorPage = new WebPage(url);
                errorPage.score = 0;
                WebNode errorNode = new WebNode(errorPage);
                parentNode.addChild(errorNode);
            }
        }
    }

    // 其他方法...
    public WebTree getWebTree() {
        return this.webTree;
    }

    public ArrayList<WebPage> getHighScorePages() {
        return this.highScorePages;
    }
}
