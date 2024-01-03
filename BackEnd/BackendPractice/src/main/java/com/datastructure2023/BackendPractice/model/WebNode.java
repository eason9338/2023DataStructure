package com.datastructure2023.BackendPractice.model;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;


public class WebNode {
    public WebNode parent;               
    public ArrayList<WebNode> children;  
    public WebPage webPage;              
    public double nodeScore;            

    // 構造函數
    public WebNode(WebPage webPage) {
        this.webPage = webPage;
        this.children = new ArrayList<>();
    }

    // 根據關鍵字列表計算節點分數
    public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
        // 根據關鍵字設置當前網頁的分數
        webPage.setScore(keywords);
        nodeScore = webPage.score;
        
        // 為每個子節點創建並執行計算分數的任務
        List<ForkJoinTask<Double>> tasks = new ArrayList<>();
        for (WebNode child : children) {
            ScoreTask task = new ScoreTask(child, keywords);
            tasks.add(task.fork()); // 啟動子任務
        }

        // 等待所有子任務完成並更新當前節點分數
        for (ForkJoinTask<Double> task : tasks) {
            nodeScore += task.join();
        }
    }

    // 將子節點加入當前節點
    public void addChild(WebNode child) {
        if (this.children.size() < 10) { // 確保每個父節點下最多只有10個子節點
            this.children.add(child);
            child.parent = this;
        }
    }

    // 檢查當前節點是否為其父節點的最後一個子節點
    public boolean isTheLastChild() {
        if (this.parent == null) {
            return true;
        }
        return this.equals(parent.children.get(parent.children.size() - 1));
    }

    // 獲取節點在樹中的深度
    public int getDepth() {
        int depth = 1;
        WebNode current = this;
        while (current.parent != null) {
            depth++;
            current = current.parent;
        }
        return depth;
    }

    // 用於計算特定 WebNode 分數的 RecursiveTask
    private static class ScoreTask extends RecursiveTask<Double> {
        private WebNode node;
        private ArrayList<Keyword> keywords;

        public ScoreTask(WebNode node, ArrayList<Keyword> keywords) {
            this.node = node;
            this.keywords = keywords;
        }

        @Override
        protected Double compute() {
            try {
                node.setNodeScore(keywords); // 遞迴計算子節點分數
                return node.nodeScore;
            } catch (IOException e) {
                System.err.println("計算節點分數時出錯: " + e.getMessage());
                return 0.0;
            }
        }
    }
}
