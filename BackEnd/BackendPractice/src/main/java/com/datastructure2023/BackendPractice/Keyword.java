package com.datastructure2023.BackendPractice;

public class Keyword {
	public String name; 
  public float weight;
    
    public Keyword(String name, float weight){
		this.name = name;
		this.weight = weight;
    }
    
    @Override
    public String toString(){
    	return "["+name+","+weight+"]";
    }
    
    public String getName()
    {
    	return name;
    }
    
    public float getWeight()
    {
    	return weight;
    }
}

// // ArrayList<Keyword> keywordList = new ArrayList<>();

//         // Add keywords and their weights to the list
//         keywordList.add(new Keyword(input, 0.9f));
//         keywordList.add(new Keyword("實習", 0.9));
//         keywordList.add(new Keyword("實習生", 0.9));
//         keywordList.add(new Keyword("大一", 0.2));
//         keywordList.add(new Keyword("大二", 0.4));
//         keywordList.add(new Keyword("大三", 0.6));
//         keywordList.add(new Keyword("大四", 0.8));
//         keywordList.add(new Keyword("碩一", 0.9));
//         keywordList.add(new Keyword("碩二", 1.0));
//         keywordList.add(new Keyword("應屆畢業生", 1.0));
//         keywordList.add(new Keyword("兼職", 0.5));
//         keywordList.add(new Keyword("轉正職機會", 0.7));
//         keywordList.add(new Keyword("不限年資", 0.6));
//         keywordList.add(new Keyword("不需負擔管理責任", 0.8));
//         keywordList.add(new Keyword("職缺", 0.9));
//         keywordList.add(new Keyword("薪水", 0.5));
//         keywordList.add(new Keyword("勞保", 0.5));
//         keywordList.add(new Keyword("短期實習", 0.7));
//         keywordList.add(new Keyword("面試", 0.6));
//         keywordList.add(new Keyword("長期實習", 0.7));
//         keywordList.add(new Keyword("培訓", 0.6));

// 核心關鍵字是 '實習/實習生'（1.0），'職缺'(0.9)
// 由於隨學年增加實習的需求亦增加，故學年越高權重越高，同時對應屆畢業生有高權重（1.0），
// 考慮到兼職機會，'Part time' 權重為 0.5。而'轉正職機會'有利於職業發展（0.7），
// '不限年資'、'不需負擔管理責任，可表示是初階職缺、非管理職，故工作更適配畢業生（0.7）。
// '短期實習'，'長期實習'，由於與實習的時間相關（0.7）
// 面試'（0.6）和 '培訓'（0.6），這兩個詞彙多出現在初階職缺(0.6)
// 其餘關鍵字 '薪水'（0.3）、'勞保'（0.3），其餘相關性相對邊緣的都設0.3