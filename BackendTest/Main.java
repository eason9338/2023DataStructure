import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException{
        WebPage rootPage = new WebPage("https://www.104.com.tw/jobs/main/");
        WebTree tree = new WebTree(rootPage);
        ArrayList<Keyword> keywordList = new ArrayList<Keyword>();

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

        tree.setPostOrderScore(keywordList);
        System.out.println(tree.eulerPrintTree());
    }
}