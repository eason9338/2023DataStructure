package com.datastructure2023.BackendPractice;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

public class KeywordList {
	private ArrayList<Keyword> lst;
	
	public KeywordList() throws FileNotFoundException{
		this.lst = new ArrayList<Keyword>();
		File keywordFile = new File("../resources/Keyword.txt");
		Scanner sc = new Scanner(keywordFile);

		while(sc.hasNextLine()) {
			String keywordName = sc.next();
			float keywordWeight = sc.nextFloat();
			Keyword newKeyword = new Keyword(keywordName, keywordWeight);

			//Keyword newKeyword = new Keyword(keywordName, keywordWeight);
			lst.add(newKeyword);

		}

		sc.close();
    }
	
	public void add(Keyword keyword){
		lst.add(keyword);
    }

	public ArrayList<Keyword> getKeywordsList(){
		return this.lst;
	}

	

	// //Quick sort
	// public void sort(){
	// 	if(lst.size() == 0)
	// 	{
	// 		System.out.println("InvalidOperation");
	// 	}
	// 	else 
	// 	{
	// 		quickSort(0, lst.size()-1);
	// 	}
	// }
	
	// private void quickSort(int leftbound, int rightbound){
	// 	if (leftbound < rightbound) 
	// 	{
	// 		int i = leftbound - 1;
			
	// 		for (int j = leftbound; j <= rightbound - 1; j++) 
	// 		{
	// 			if (lst.get(j).count < lst.get(rightbound).count) 
	// 			{
	// 				i++;
	// 				swap(i, j);
	// 			}
	// 		}
	// 		swap((i+1), rightbound);

	// 		quickSort(leftbound, i);
	// 		quickSort(i + 2, rightbound);
	// 	}
	// }

	// private void swap(int aIndex, int bIndex){
	// 	Keyword temp = lst.get(aIndex);
	// 	lst.set(aIndex, lst.get(bIndex));
	// 	lst.set(bIndex, temp);
	// }
	
	
}