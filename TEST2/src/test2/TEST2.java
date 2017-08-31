package test2;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Ticket Frontier Technical Test, Test 2.
 *
 * @author Ryan Wong
 */
public class TEST2
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        String url = "http://www2.stat.duke.edu/courses/Spring01/sta114/data/andrews.html";
        String urlBegin = "http://www2.stat.duke.edu/courses/Spring01/sta114/data/";
        Document doc = Jsoup.connect(url).get();
//        Element rawText = doc.body();
//        String line = rawText.toString();
//        System.out.println(line);        

        Elements content = doc.select("tr");

        for (Element a : content)
        {
//            System.out.println(a);
            String title = a.select("td").text();
            String link = a.select("a").attr("href");
            System.out.println("Title: " + title.toString());

            // Links only appear as relative within JSoup.. This combines the beginning links and the relative links to make a full URL.
            String urlFull = urlBegin + link;
            System.out.println("Link: " + urlFull);
            System.out.println("#####");

        }

    }

}
