import java.util.ArrayList;

public class SearchResultItem {
    public String title;
    public String link;
    public ArrayList<String> children;

    public SearchResultItem(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return this.link;
    }

    public void setChild(ArrayList<String> children){
        this.children  = children;
    }

    public ArrayList<String> getChildren(){
        return this.children;
    }
}
