import java.util.List;

public class WebList {
    private List<WebPage> webPages;

    // constructor
    public WebList(List<WebPage> webPages) {
        this.webPages = webPages;
    }

    // 對WebPage列表按照分數降序進行排序
    public void sortByScore() {
        if (webPages != null && webPages.size() > 1) {
            quickSort(0, webPages.size() - 1);
        }
    }

    // 使用快速排序算法對WebList進行排序
    private void quickSort(int low, int high) {
        if (low < high) {
            int partitionIndex = partition(low, high);
            quickSort(low, partitionIndex - 1);
            quickSort(partitionIndex + 1, high);
        }
    }

    // 快速排序的分割方法，返回分割點的索引
    private int partition(int low, int high) {
        if (webPages == null || webPages.isEmpty()) {
            return -1; // 防止weblist是空的情況
        }
    
        double pivot = webPages.get(high).score;
        int i = low - 1;
    
        for (int j = low; j < high; j++) {
            if (webPages.get(j).score >= pivot) {
                i++;
                swap(i, j);
            }
        }
    
        swap(i + 1, high);
        return i + 1;
    }
    

    // 交換WebPage列表中兩個元素的位置
    private void swap(int i, int j) {
        if (i != j && i >= 0 && j >= 0 && i < webPages.size() && j < webPages.size()) {
            WebPage temp = webPages.get(i);
            webPages.set(i, webPages.get(j));
            webPages.set(j, temp);
        }
    }
}
