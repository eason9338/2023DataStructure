import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
    public WebNode root;

    // 存入 WebPage 物件，初始化 WebTree
    public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}

    // 設定每個節點的分數（Post-order traversal）
    public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		setPostOrderScore(root, keywords);
	}

    // 遞迴遍歷每個節點，計算分數
    private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
        for (WebNode w : startNode.children) {
            setPostOrderScore(w, keywords);
        }

        // 計算startNode節點的分數，其分數由其子節點的分數總和以及該節點自身的分數組成
        startNode.setNodeScore(keywords);
        startNode.nodeScore += startNode.children.stream().mapToDouble(child -> child.nodeScore).sum();
    }


    // 計算整個樹的總分
    public double getTotalScore() {
        return root.nodeScore;
    }
}
