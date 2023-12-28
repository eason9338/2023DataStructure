import java.io.IOException;
import java.util.ArrayList;

public class WebTree {
    public WebNode root;

    // 存入 WebPage 物件，初始化 WebTree
    public WebTree(WebPage rootPage) {
        this.root = new WebNode(rootPage);
    }

    // 設定每個節點的分數（Post-order traversal）
    public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
        setPostOrderScore(root, keywords);
    }

    // 遞迴遍歷每個節點，計算分數
    private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {
        for (WebNode w : startNode.children) {
            setPostOrderScore(w, keywords);
        }

        // 如果是根節點，將總分設定到主網頁的 score 中
        if (startNode.webPage.getUrl().equals(root.webPage.getUrl())) {
            System.out.println("a");
            startNode.webPage.setScore(keywords); // 計算主網頁的分數
            startNode.webPage.score = startNode.nodeScore; // 將總分設定到主網頁的 score 中
        } else {
            startNode.setNodeScore(keywords); // 設定其他節點的分數
        }
    }

    // 計算整個樹的總分
    public double getTotalScore() {
        return eulerPrintTree(root);
    }

    // 遞迴遍歷樹，計算總分
    private double eulerPrintTree(WebNode startNode) {
        double totalScore = startNode.nodeScore;

        for (WebNode w : startNode.children) {
            totalScore += eulerPrintTree(w);
        }

        return totalScore;
    }
}
