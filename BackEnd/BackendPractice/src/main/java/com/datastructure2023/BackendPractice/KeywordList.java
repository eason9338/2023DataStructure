package com.datastructure2023.BackendPractice;

import java.util.ArrayList;

public class KeywordList {
	private ArrayList<Keyword> lst;
	
	public KeywordList(){
		this.lst = new ArrayList<Keyword>();
    }
	
	public void add(Keyword keyword){
		lst.add(keyword);
    }

	public ArrayList<Keyword> getKeywordsList(){
		return this.lst;
	}
	
	
}