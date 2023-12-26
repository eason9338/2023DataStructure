import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
     public String url;
     public WordCounter counter;
     public double score;

     //改成存入url及其子網頁
     public WebPage(String url) {
         this.url = url;
         this.counter = new WordCounter(url);
     }

	 //取得url
	 public String getUrl(){
		return this.url;
	 }

     // setScore 方法用於設定分數
     public void setScore(ArrayList<Keyword> keywords) throws IOException {
         score = 0;

         for (Keyword k : keywords) {
             score += k.weight * counter.countKeyword(k.name);
         }
     }
}