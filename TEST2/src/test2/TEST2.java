package test2;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

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

        // Extracts the list of tables in the body of the document.
        // Each table is seperated by a <tr> tag, so selecting the content between
        // <tr> and </tr> gives us information on a single table.
        Elements contentTableList = doc.select("tr");
        Vector<tableEntity> tableVector = new Vector<>();

        // Walk through all of the tables in the list, parsing the necessary information.
        for (Element a : contentTableList)
        {
            tableEntity newTable = new tableEntity(getTitleFromTable(a), getLinkFromTable(a, urlBegin));
            tableVector.add(newTable);
//            System.out.println(a);
//        System.out.println("Link: " + getLinkFromTable(a, urlBegin));
//        System.out.println("Title: " + getTitleFromTable(a));
//        System.out.println("#####");
        }
        
        for (tableEntity currTable: tableVector){
            System.out.println(currTable.output());
        }
    }

    // Parses the title of the given table, and returns a string.
    public static String getTitleFromTable(Element a) {
        String title = a.select("td").text();
        return title;
    }
    
    // Parses the absolute URL link of the given table, and returns it as a string.
    // Links only appear as relative within JSoup.. This combines the beginning links and the relative links to make a full URL.
    public static String getLinkFromTable(Element a, String starterURL) {
        String link = a.select("a").attr("href");
        String urlFull = starterURL + link;
        return urlFull;
    }
    
    // A class to represent a single table..
    public static class tableEntity{
        private String title = "";
        private String url = "";
        
        public tableEntity(String title, String url) {
            this.title = title;
            this.url = url;
        }
        
        public String getTitle(){
            return title;
        }
        
        public String getURL(){
            return url;
        }
        
        public void setTitle(String title){
            this.title = title;
        }
        
        public void setURL(String url){
            this.url = url;
        }
        
        public String output(){
            return title + "    " + url;
        }
    }
}
