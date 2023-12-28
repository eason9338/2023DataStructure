package com.datastructure2023.BackendPractice;

import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class InputHandler {
    
    static class Input {
        private String input;
       
        public String getInput() { return input; }
      
        /*
        rootUrl是input的URL
        String rootUrl = "https://example.com";

        // 創建 WebCrawler 並調用 crawlWebTree 方法
        S

        // 設定keywordlist
        ArrayList<Keyword> keywordList = new ArrayList<>();

        Add keywords and their weights to the list
        keywordList.add(new Keyword(input, 0.9f));
        keywordList.add(new Keyword("實習", 0.9));
        keywordList.add(new Keyword("實習生", 0.9));
        keywordList.add(new Keyword("大一", 0.2));
        keywordList.add(new Keyword("大二", 0.4));
        keywordList.add(new Keyword("大三", 0.6));
        keywordList.add(new Keyword("大四", 0.8));
        keywordList.add(new Keyword("碩一", 0.9));
        keywordList.add(new Keyword("碩二", 1.0));
        keywordList.add(new Keyword("應屆畢業生", 1.0));
        keywordList.add(new Keyword("兼職", 0.5));
        keywordList.add(new Keyword("轉正職機會", 0.7));
        keywordList.add(new Keyword("不限年資", 0.6));
        keywordList.add(new Keyword("不需負擔管理責任", 0.8));
        keywordList.add(new Keyword("職缺", 0.9));
        keywordList.add(new Keyword("薪水", 0.5));
        keywordList.add(new Keyword("勞保", 0.5));
        keywordList.add(new Keyword("短期實習", 0.7));
        keywordList.add(new Keyword("面試", 0.6));
        keywordList.add(new Keyword("長期實習", 0.7));
        keywordList.add(new Keyword("培訓", 0.6));

        // 創建 WebTree 對象並調用 setPostOrderScore 方法
        for(Keyword keywords:keywordList){
                webTree.setPostOrderScore(keywords);
        }
       
        }*/
    

        public void setInput(String data) { input = data; }
    }

    @PostMapping("/add")
    public Map<String, String> addNumbers(@RequestBody Input input) {
        String result = "Your input is: " + input.getInput();
        return Collections.singletonMap("result", result);
    }
}
