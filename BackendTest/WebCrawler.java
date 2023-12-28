import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    private static final int MAX_DEPTH = 0; // 設定最大爬取深度
    private List<WebPage> allVisitedUrls; // 存放所有已訪問過的網址
    private WebTree webTree; // 儲存建立的網頁樹結構

    // 創建 WebCrawler 實例時進行網站爬取，建立 WebTree
    public WebCrawler(String rootUrl) {
        this.allVisitedUrls = new ArrayList<>();
        crawlWebPages(rootUrl, 0); // 開始進行網頁爬取

        WebPage rootPage = new WebPage(rootUrl);
        this.allVisitedUrls.add(rootPage);

        this.webTree = buildWebTree(rootPage);
    }

    // 獲取 WebTree 
    public WebTree getWebTree() {
        return webTree;
    }

    // 遞迴爬取網頁鏈接的方法
    private void crawlWebPages(String url, int depth) {
        if (depth > MAX_DEPTH) {
            return; // 超過最大深度，停止爬取
        }

        try {
            Document document = Jsoup.connect(url).get();
            normalizeUrl(url);

            Elements links = document.select("a[href]");

            for (Element link : links) {
                String childUrl = link.attr("abs:href");
                String normalizedChildUrl = normalizeUrl(childUrl);
                // 檢查是否已經存在於列表中，且子網頁以 "https://" 開頭
                if (!containsUrl(allVisitedUrls, normalizedChildUrl) && normalizedChildUrl.startsWith("https://")) {
                    WebPage childPage = new WebPage(normalizedChildUrl);
                    allVisitedUrls.add(childPage);
                    crawlWebPages(childUrl, depth + 1);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 建立 WebTree 的方法
    private WebTree buildWebTree(WebPage rootPage) {
        WebTree tree = new WebTree(rootPage);

        for (WebPage crawledPage : allVisitedUrls) {
            if (!crawledPage.getUrl().equals(rootPage.getUrl())) {
                tree.root.addChild(new WebNode(crawledPage));
            }
        }
        return tree;
    }

    // 正規化 URL 的方法
    private String normalizeUrl(String url) {
        return url.toLowerCase().replaceAll("/$", "");
    }

    // 檢查網址是否已經存在於列表中的方法
    private boolean containsUrl(List<WebPage> pages, String url) {
        for (WebPage page : pages) {
            if (page.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    // 打印所有訪問過的網址的方法
    public void printList() {
        for (WebPage page : allVisitedUrls) {
            System.out.println(page.url);
        }
    }

    
}
