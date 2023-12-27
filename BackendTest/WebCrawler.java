import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebCrawler {
    private static final int MAX_DEPTH = 0; // 設定最大爬取深度
    private List<WebPage> allVisitedUrls;

    // 把url存入tree中
    public WebTree crawlWebTree(String url) {
        List<WebPage> allVisitedUrls = new ArrayList<>();
        crawlWebPages(url, 0, allVisitedUrls);

        WebPage rootPage = new WebPage(url);
        allVisitedUrls.add(rootPage);

        return new WebTree(rootPage);
    }

    private void crawlWebPages(String url, int depth, List<WebPage> allVisitedUrls) {
        if (depth > MAX_DEPTH) {
            return; // 超過最大深度，停止爬取
        }

        try {
            // 透過 Jsoup 取得網頁的 Document 物件
            Document document = Jsoup.connect(url).get();

            //正規化URL
            normalizeUrl(url);

            // 透過ahref取得所有網址
            Elements links = document.select("a[href]");

            // 把網址加到list中
            for (Element link : links) {
                String childUrl = link.attr("abs:href"); // 取得絕對路徑的連結

                // 檢查是否已經存在於列表中，且子網頁以"https://"開頭
                String normalizedChildUrl = normalizeUrl(childUrl);
                if (!containsUrl(allVisitedUrls, normalizedChildUrl) && normalizedChildUrl.startsWith("https://")) {
                    // 如果不存在且子網頁以"https://"開頭，則建WebPage並加到List
                    WebPage childPage = new WebPage(normalizedChildUrl);
                    allVisitedUrls.add(childPage);

                    // 遞迴呼叫 crawlWebPages 方法取得子網頁的子網頁，深度加1
                    crawlWebPages(childUrl, depth + 1, allVisitedUrls);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String normalizeUrl(String url) {
        // 實現URL正規化邏輯，例如轉換為小寫，移除末尾斜杠等
        return url.toLowerCase().replaceAll("/$", "");
    }

    private boolean containsUrl(List<WebPage> pages, String url) {
        for (WebPage page : pages) {
            if (page.getUrl().equals(url)) {
                return true;
            }
        }
        return false;
    }

    public void printList(){
        for (WebPage page : allVisitedUrls) {
            System.out.println(page.url);
        }
    }
}
