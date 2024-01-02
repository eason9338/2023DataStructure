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

public class WebCrawler {

    private WebTree webTree; // 存儲建立的網頁樹結構
    private int maxDepth = 1; // 設置最大爬取深度
    private Set<String> allVisitedUrls; // 存儲所有訪問過的URL
    private ArrayList<Keyword> keywords; // 用於計算分數的關鍵詞列表
    private double scoreThreshold = 100; // 分數門檻
    private ArrayList<WebPage> highScorePages; // 存儲分數高於門檻的頁面

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

    private void crawlWebPages(String url, WebNode parentNode, int depth) {
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
    
            Elements links = document.select("a[href]");
            for (Element link : links) {
                String childUrl = link.absUrl("href");
                crawlWebPages(childUrl, currentNode, depth + 1);
            }
    
        } catch (IOException e) {
            System.err.println("訪問URL時出錯: " + url + " - " + e.getMessage());
            // 當主網頁出現異常時，創建一個分數為0的WebPage對象，並不繼續爬取子網頁
            WebPage errorPage = new WebPage(url);
            errorPage.score = 0; // 分數設為0
            WebNode errorNode = new WebNode(errorPage);
            parentNode.addChild(errorNode);
        }
    }
    
    
    // 檢查URL是否有效的方法
    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
    

    // 其他方法...
    public WebTree getWebTree(){
        return this.webTree;
    }

    public ArrayList<WebPage> getHighScorePages(){
        return this.highScorePages;
    }
}
