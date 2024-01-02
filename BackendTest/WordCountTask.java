import java.io.IOException;
import java.util.concurrent.RecursiveTask;

public class WordCountTask extends RecursiveTask<Integer> {
    private String url;
    private String keyword;

    public WordCountTask(String url, String keyword) {
        this.url = url;
        this.keyword = keyword;
    }

    @Override
    protected Integer compute() {
        try {
            WordCounter wordCounter = new WordCounter(url);
            return wordCounter.countKeyword(keyword);
        } catch (IOException e) {
            System.err.println("處理 URL 時出錯: " + url + " - " + e.getMessage());
            return 0;
        }
    }
}
