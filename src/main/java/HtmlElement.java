import java.util.ArrayList;
import java.util.List;

public final class HtmlElement {

    private List<String> elementBody;
    private Integer rating=0;

    private int lineStart;
    private int lineEnd;

    public HtmlElement(List<String> elementBody, int lineStart, int lineEnd) {
        this.elementBody = elementBody;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
    }

    public void increaseRating(){
        rating++;
    }

    public List<String> getElementBody() {
        return new ArrayList<>(elementBody);
    }

    public Integer getRating() {
        return rating;
    }

    public int getLineStart() {
        return lineStart;
    }

    public int getLineEnd() {
        return lineEnd;
    }
}
