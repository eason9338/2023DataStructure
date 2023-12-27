import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException{
        
        String rootUrl = "https://career.104.com.tw/?utm_source=104&utm_medium=jobbank_home-header-tab";
        
        ArrayList<Keyword> keywordList = new ArrayList<Keyword>();

        WebCrawler webCrawler = new WebCrawler();
        WebTree webTree = webCrawler.crawlWebTree(rootUrl);

        try {
            File file = new File("Keywords.txt");
            Scanner sc = new Scanner(file);
            while(sc.hasNext()) {
                String name = sc.next();
                float weight = Float.parseFloat(sc.next());
                Keyword newKeyword = new Keyword(name, weight);
                keywordList.add(newKeyword);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
        }

        webTree.setPostOrderScore(keywordList);
        //System.out.println(webTree.eulerPrintTree());
        webCrawler.printList();
    }
}