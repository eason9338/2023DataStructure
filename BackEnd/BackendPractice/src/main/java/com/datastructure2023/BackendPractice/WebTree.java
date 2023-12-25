package com.datastructure2023.BackendPractice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebTree {
    public List<WebNode> nodes;

    // 存入 WebPage 物件
    public WebTree(WebPage rootPage) {
        this.nodes = new ArrayList<>();
        this.nodes.add(new WebNode(rootPage));
    }

    public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
        setPostOrderScore(nodes.get(0), keywords);
    }

    private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
        for (WebNode w : startNode.children) {
            setPostOrderScore(w, keywords);
        }
        startNode.setNodeScore(keywords);
    }

    public double eulerPrintTree() {
        return eulerPrintTree(nodes.get(0));
    }

    // 計算 tree 的 totalScore
    private double eulerPrintTree(WebNode startNode) {
        double totalScore = startNode.nodeScore;

        for (WebNode w : startNode.children) {
            totalScore += eulerPrintTree(w);
        }

        return totalScore;
    }
}
