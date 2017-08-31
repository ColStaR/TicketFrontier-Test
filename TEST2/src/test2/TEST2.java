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

        // Walk through all of the tables in the list, populating the 
        // tableVector data structure with tableEntities..
        for (Element a : contentTableList) {
            tableEntity newTable = new tableEntity(getNumberFromTable(a), getTitleFromTable(a), getLinkFromTable(a, urlBegin));
            tableVector.add(newTable);
        }
        
        // If there is a single argument in the command, search the tableVector
        // to see if there are any matches.
        if(args.length == 1){
            for(tableEntity currTable : tableVector) {
                // Compare the number of the tables in tableVector to the
                // number given in the argument.
                if(currTable.getNumber().equals(args[0])) {
                    System.out.println("Match found! " + currTable.output());
                    // DO TABLE DOWNLADING HERE
                }
            }
        // If there are no arguments or more than one, assume default operation
        // and simply output the contents of tableVector line by line.
        } else {
            for (tableEntity currTable: tableVector){
                System.out.println(currTable.output());
            }
        }
    }
    
    // Parses the number of the given table, and returns a string.
    public static String getNumberFromTable(Element a) {
        String fullTitle = a.select("td").text();
        // Splits the fullTitle string into an array seperated by spaces.
        // Since the number string is between the first and second spaces,
        // we capture the second string in the array for the number.
        String number = fullTitle.split(" ")[1];
        return number;
    }
    
    // Parses the title of the given table, and returns a string.
    public static String getTitleFromTable(Element a) {
        String fullTitle = a.select("td").text();
        // Similar to the explanation in the method getNumberFromTable, this
        // title string is gathered by taking the contents after the third space
        // to the end.. This is accomplished with the limiter in the split
        // function, which stops splitting after three splits. We can then
        // grab the third substring in the array, which is the title.
        String title = fullTitle.split(" ", 3)[2];
        return title;
    }
    
    // Parses the absolute URL link of the given table, and returns it as a string.
    // Links only appear as relative within JSoup.. This combines the beginning links and the relative links to make a full URL.
    public static String getLinkFromTable(Element a, String starterURL) {
        String link = a.select("a").attr("href");
        String urlFull = starterURL + link;
        return urlFull;
    }
    
    // A class to represent a single table. being scrubbed from the page.
    public static class tableEntity{
        private String number = "";
        private String title = "";
        private String url = "";
        
        public tableEntity(String number, String title, String url) {
            this.number = number;
            this.title = title;
            this.url = url;
        }
        
        public String getNumber(){
            return number;
        }
        
        public String getTitle(){
            return title;
        }
        
        public String getURL(){
            return url;
        }
        
        public void setNumber(String number){
            this.number = number;
        }
                
        public void setTitle(String title){
            this.title = title;
        }
        
        public void setURL(String url){
            this.url = url;
        }
        
        // Outputs a string of the given table's number, title, a tab character, and then the table's url.
        public String output(){
            return "Table " + number + " " + title + '\t' + url;
        }
    }
}
