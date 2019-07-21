import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path originFilePath = Paths.get("src\\main\\resources\\startbootstrap-sb-admin-2-examples\\sample-0-origin.html");
        Path otheSampleFilePath = Paths.get("src\\main\\resources\\startbootstrap-sb-admin-2-examples\\sample-2-container-and-clone.html");
        String targetElementId = "\"make-everything-ok-button\"";

        if(args.length==2){
            originFilePath = Paths.get(args[0]);
            otheSampleFilePath = Paths.get(args[1]);
        }
        if(args.length==3){
            targetElementId = args[3];
        }

        List<String> originHtml = readHtml(originFilePath);
        List<String> otherSampleHtml = readHtml(otheSampleFilePath);

        HtmlCrawler htmlCrawler = new HtmlCrawler(originHtml, targetElementId);
        List<String> crawlerKeyPoints = htmlCrawler.getCrawlerKeyPoints();

        HtmlElement requiredElement = htmlCrawler.findRequiredElement(otherSampleHtml, crawlerKeyPoints);

        System.out.println("Search results");
        System.out.println("Start Line: "+(requiredElement.getLineStart()+1));
        System.out.println("End Line: "+(requiredElement.getLineEnd()+1));
        System.out.println("Rating the number of matching key points: "+requiredElement.getRating());
        System.out.println("The body of the desired item:");
        requiredElement.getElementBody().stream().forEach(s -> System.out.println(s));
    }

    public static List<String> readHtml(Path path){
        List<String> lines = new ArrayList<>();

        try (Stream<String> lineStream = Files.lines(path)) {
            lines = lineStream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
