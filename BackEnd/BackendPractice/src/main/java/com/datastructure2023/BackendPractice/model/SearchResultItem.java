package com.datastructure2023.BackendPractice.model;

import java.util.ArrayList;

public class SearchResultItem {
    public String title;
    public String link;
    public ArrayList<String> children;

    public SearchResultItem(String title, String link) {
        this.title = title;
        this.link = link;
        children = new ArrayList<>();
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return this.link;
    }

    public void setChild(String url){
        this.children.add(url);
    }

    public void setChild(ArrayList<String> urls){
        this.children.addAll(urls);
    }

    public ArrayList<String> getChildren(){
        return this.children;
    }
}
