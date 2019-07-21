import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class HtmlCrawler {
    private List<String> html;
    private String uniqueIdentifier;

    public HtmlCrawler(List<String> html, String uniqueIdentifier) {
        this.html = html;
        this.uniqueIdentifier = uniqueIdentifier;
    }


    public List<String> getCrawlerKeyPoints(){
        for (int i = 0; i < html.size(); i++) {
            if(html.get(i).contains("<a")){
                HtmlElement element = formElementBody(i, html);
                List<String> elementBody = element.getElementBody();
                Optional<String> requiredElement = elementBody
                        .stream()
                        .filter(s -> s.contains(uniqueIdentifier))
                        .findAny();
                if(requiredElement.isPresent())
                    return formKeyPoints(elementBody);
            }
        }
        throw new RuntimeException("No element with such unique identifier: "+uniqueIdentifier);
    }

    private List<String> formKeyPoints(List<String> body) {
        List<String> keyPoints = new ArrayList<>();
        for (String row :
                body) {
            if(row.contains("=")) {
                String[] kp = row.split("=");
                keyPoints.addAll(Arrays.asList(kp));
            }else{
                keyPoints.add(row);
            }
        }
        return keyPoints.stream()
                .map(s -> s.trim())
                .collect(Collectors.toList());
    }

    private HtmlElement formElementBody(int startIndex, List<String> html) {
        List<String> body = new ArrayList<>();
        int endIndex = startIndex-1;
        do{
            endIndex++;
            body.add(html.get(endIndex));
        }while(!html.get(endIndex).contains("</a>"));

        return new HtmlElement(body, startIndex, endIndex);
    }


    public HtmlElement findRequiredElement(List<String> targetHtml, List<String> keyPoints){
        List<HtmlElement> htmlElements = new ArrayList<>();
        for (int i = 0; i < targetHtml.size(); i++) {
            if(targetHtml.get(i).contains("<a")){
                HtmlElement element = formElementBody(i, targetHtml);
                List<String> elementBody = element.getElementBody();
                for (String keyP :
                        keyPoints) {

                    Optional<String> requiredElement = elementBody
                            .stream()
                            .filter(s -> s.contains(keyP))
                            .findAny();
                    if(requiredElement.isPresent())
                        element.increaseRating();
                }
                htmlElements.add(element);
            }
        }

        return htmlElements.stream()
                .max(Comparator.comparing(htmlElement -> htmlElement.getRating())).get();

    }
}
