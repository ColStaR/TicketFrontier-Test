package test2;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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
        // TO DO: Implement 404 Page Not Found error.

        // Extracts the list of tables in the body of the document.
        // Each table is seperated by a <tr> tag, so selecting the content between
        // <tr> and </tr> gives us information on a single table.
        Elements contentTableList = doc.select("tr");

        // A vector will store the table data types and act as our general
        // storage data structure for the tables.
        Vector<tableEntity> tableVector = new Vector<>();

        // Walk through all of the tables in the list, populating the 
        // tableVector data structure with tableEntities.
        for (Element a : contentTableList)
        {
            tableEntity newTable = new tableEntity(getNumberFromTable(a),
                    getTitleFromTable(a), getLinkFromTable(a, urlBegin));
            tableVector.add(newTable);
        }

        // If there is a single argument in the command, search the tableVector
        // to see if there are any matches.
        if (args.length == 1)
        {
            boolean matchFound = false;

            for (tableEntity currTable : tableVector)
            {
                // Compare the number of the tables in tableVector to the
                // number given in the argument.
                if (currTable.getNumber().equals(args[0]))
                {
                    System.out.println("Match found!");
                    System.out.println("Downloading Table " + currTable.getNumber() + "...");
                    downloadFile(currTable.getURL());
                    System.out.println("File Downloaded as output.dat!");
                    matchFound = true;
                }

                // After going through all the records and no match is found, 
                // end the program.
                if (!matchFound)
                {
                    System.out.println("No matching table found.");
                    System.out.println("Exiting program.");
                    System.exit(1);
                }
            }

        } // If there are more than 1 arguments, end the program.
        else if (args.length > 1)
        {
            System.out.println("Too many arguments found. This program"
                    + "can only accept 0 or 1 arguments.");
            System.out.println("Exiting program.");
            System.exit(1);
        } // If there are no arguments, assume default operation
        // and simply output the contents of tableVector line by line.
        // Only the lines with month names in the title will be output.
        else
        {
            // This table contains the names of the months to search the titles
            // with. Do note that each month name string has spaces to 
            // distinguish it as individiual words, as opposed to parts of
            // words like "Mays" with "May".
            String monthNames[] =
            {
                " January ", " Febuary ", " March ",
                " April ", " May ", " June ", " July ", " August ",
                " September ", " October ", " November ", " December "
            };
            // Walk through each table, finding and outputting tables with 
            // month names as requested.
            for (tableEntity currTable : tableVector)
            {
                for (String month : monthNames)
                {
                    if (currTable.title.contains(month))
                    {
                        System.out.println(currTable.output());
                    }
                }
            }
        }
    }

    // Parses the number of the given table, and returns a string.
    public static String getNumberFromTable(Element a)
    {
        String fullTitle = a.select("td").text();
        // Splits the fullTitle string into an array seperated by spaces.
        // Since the number string is between the first and second spaces,
        // we capture the second string in the array for the number.
        String number = fullTitle.split(" ")[1];
        return number;
    }

    // Parses the title of the given table, and returns a string.
    public static String getTitleFromTable(Element a)
    {
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
    public static String getLinkFromTable(Element a, String starterURL)
    {
        String link = a.select("a").attr("href");
        String urlFull = starterURL + link;
        return urlFull;
    }

    // downloadFile will download a given file from the URL stated, and 
    // save the file in the TEST2 root directory with the name output.dat.
    // This code was retrieved from the following URL on 8/30/2017 at 11:27 PM
    // https://www.mkyong.com/java/java-how-to-download-a-file-from-the-internet/
    // I have modified the source code somewhat in order to fit my own work.
    // Full credit goes to the original author, mkyong.
    public static void downloadFile(String url)
    {
        try
        {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("output.dat");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // A class to represent a single table. being scrubbed from the page.
    public static class tableEntity
    {

        private String number = "";
        private String title = "";
        private String url = "";

        public tableEntity(String number, String title, String url)
        {
            this.number = number;
            this.title = title;
            this.url = url;
        }

        public String getNumber()
        {
            return number;
        }

        public String getTitle()
        {
            return title;
        }

        public String getURL()
        {
            return url;
        }

        public void setNumber(String number)
        {
            this.number = number;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public void setURL(String url)
        {
            this.url = url;
        }

        // Outputs a string of the given table's number, title, a tab character, and then the table's url.
        public String output()
        {
            return "Table " + number + " " + title + '\t' + url;
        }
    }
}
