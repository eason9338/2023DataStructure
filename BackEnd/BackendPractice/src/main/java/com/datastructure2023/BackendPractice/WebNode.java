package com.datastructure2023.BackendPractice;

import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
    public WebNode parent;               
    public ArrayList<WebNode> children;  
    public WebPage webPage;              
    public double nodeScore;            

    // constructor
    public WebNode(WebPage webPage) {
        this.webPage = webPage;
        this.children = new ArrayList<WebNode>();
    }

    // 根據關鍵字列表計算節點分數
    public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
       
		// 根據關鍵字設定相關網頁的分數
        webPage.setScore(keywords);
        nodeScore = webPage.score;

        // 把子網頁的分數做統計，計算整個網頁的總分
        for (WebNode w : children) {
            nodeScore += w.nodeScore;
        }
    }

    // 將子節點加入當前節點
    public void addChild(WebNode child) {
        this.children.add(child);
        child.parent = this;
    }

    // 檢查當前節點是否為其父節點的最後一個子節點
    public boolean isTheLastChild() {
        if (this.parent == null)
            return true;
        ArrayList<WebNode> siblings = this.parent.children;
        return this.equals(siblings.get(siblings.size() - 1));
    }

    // 取得節點在樹中的深度
    public int getDepth() {
        int retVal = 1;
        WebNode currNode = this;
        while (currNode.parent != null) {
            retVal++;
            currNode = currNode.parent;
        }
        return retVal;
    }
}
