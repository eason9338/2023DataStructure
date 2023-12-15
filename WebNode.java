import java.io.IOException;
import java.util.ArrayList;

public class WebNode
{
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;// Webpage total Score
	
	//constructor
	public WebNode(WebPage webPage)
	{
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	//計算網頁分數
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException
	{
		webPage.setScore(keywords);
		nodeScore = webPage.score;
		for(WebNode w:children) {
			nodeScore += w.nodeScore;
		}
		
	}

	//加入子網頁
	public void addChild(WebNode child)
	{
		this.children.add(child);
		child.parent = this;
	}

	//確定是否最後的子網業
	public boolean isTheLastChild()
	{
		if (this.parent == null)
			return true;
		ArrayList<WebNode> siblings = this.parent.children;

		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth()
	{
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null)
		{
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}
