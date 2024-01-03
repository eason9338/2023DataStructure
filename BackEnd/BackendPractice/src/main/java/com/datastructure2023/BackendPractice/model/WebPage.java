package com.datastructure2023.BackendPractice.model;

import com.datastructure2023.BackendPractice.service.WordCounter;
import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
    public String url;
    public double score;
    private WordCounter counter;
 
    // 改成存入 url
    public WebPage(String url) {
        this.url = url;
        counter = new WordCounter();
        counter.urlSetter(url);
    }

    // 取得 url
    public String getUrl() {
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
