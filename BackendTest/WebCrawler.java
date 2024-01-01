import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class WebCrawler {

    private WebTree webTree; // 存儲構建的網頁樹結構
    private int maxDepth = 0; // 設置最大爬取深度
    private Set<String> allVisitedUrls; // 存儲所有訪問過的URL
    private ArrayList<Keyword> keywords; // 用於計算分數的關鍵詞列表
    private double scoreThreshold = 100; // 分數閾值
    private ArrayList<WebPage> highScorePages; // 存儲分數高於閾值的頁面

    public WebCrawler(String rootUrl, ArrayList<Keyword> keywords) {
        this.allVisitedUrls = new HashSet<>();
        this.keywords = keywords;
        this.highScorePages = new ArrayList<>();

        // 啟用自定義信任管理器
        enableTrustAllCerts();

        WebPage rootPage = new WebPage(rootUrl);
        this.webTree = new WebTree(rootPage);
        crawlWebPages(rootUrl, this.webTree.root, 0);
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

    // 遞迴爬取網頁鏈接的方法
    private void crawlWebPages(String url, WebNode parentNode, int depth) {
        if (depth > maxDepth || allVisitedUrls.contains(url)) {
            return;
        }

        try {
            Document document = Jsoup.connect(url)
                                     .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                                     .get();
            WebPage currentPage = new WebPage(url);
            currentPage.setScore(keywords); // 正常設置分數

            // 檢查分數是否高於閾值，如果是，則添加到 highScorePages 列表
            if (currentPage.score > scoreThreshold) {
                highScorePages.add(currentPage);
            }

            WebNode currentNode = new WebNode(currentPage);
            parentNode.addChild(currentNode);
            allVisitedUrls.add(url);

            Elements links = document.select("a[href]");
            for (Element link : links) {
                String childUrl = link.absUrl("href");
                crawlWebPages(childUrl, currentNode, depth + 1);
            }

        } catch (IOException e) {
            WebPage errorPage = new WebPage(url);
            errorPage.score = 0; // 分數設為 0
            WebNode errorNode = new WebNode(errorPage);
            parentNode.addChild(errorNode);
            allVisitedUrls.add(url);
            System.out.println("訪問URL時出錯: " + url + " - " + e.getMessage());
        }
    }

    // 計算整個 WebTree 的分數
    public void calculateTotalScore() throws IOException {
        this.webTree.setPostOrderScore(keywords);
    }

    // 獲取 WebTree 總分
    public double getTotalScore() {
        return this.webTree.getTotalScore();
    }

    // 獲取 WebTree 實例
    public WebTree getWebTree() {
        return this.webTree;
    }

    // 打印所有訪問過的 URL
    public void printVisitedUrls() {
        for (String url : allVisitedUrls) {
            System.out.println(url);
        }
    }

    // 獲取分數高於閾值的頁面
    public ArrayList<WebPage> getHighScorePages() {
        return highScorePages;
    }

    // 其他方法...
}
