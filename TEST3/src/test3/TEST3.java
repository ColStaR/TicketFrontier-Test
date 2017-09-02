/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;

import java.io.*;
import java.util.*;

/**
 * TicketFrontier Test 3
 *
 * @author Ryan
 */
public class TEST3
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // If the program does not have 1 argument passed into it, end program.
        if (args.length != 1)
        {
            System.out.println("This program requires a single argument when run.");
            System.out.println("This argument should be the name of the table to"
                    + " be analyzed, e.g. \" T08.1\".");
            System.out.println("Please run the program again with this argument.");
            System.out.println("\n This program will now close.");
            System.exit(1);
        }

        String fileName = args[0];
        ArrayList<disasterRecord> recordsList = new ArrayList<disasterRecord>();

        // Open and parse the array of disasterRecords, storing them into recordsList.
        openDisasterFile(recordsList, fileName);

        // Output the records grouped by Year.
        groupByYear(recordsList);
        System.out.println("            ");
        // Output the records grouped by Weekday.
        groupByWeekday(recordsList);
    }

        // A function that reads a file given its name and parses it into a
    // disasterRecord array for storage.
    // This file reading code comes from an earlier Java project for
    // a class at the SRJC, CS 17.11.It has been modified slightly to
    // accept the filename scheme.
    public static void openDisasterFile(ArrayList<disasterRecord> recordsList, String fileName)
    {
        try
        {
            String line = "";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null)
            {
                parseLine(recordsList, line);
//                System.out.println("#####");
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex)
        {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    // parseLine takes the incoming line from the open file and stores them
    // in a usable form with a disasterRecord for each. Each new disasterRecord
    // then gets added to the array of disasterRecords, record.
    public static void parseLine(ArrayList<disasterRecord> record, String line)
    {
        // The current lines being parsed from the table are filled with spaces,
        // which will complicate a simple string split due to differing numbers
        // of spaces as record numbers increase. As such, we will clean the
        // date by removing all excess spaces.
        // The regex help comes from a Stack Overflow example here:
        // https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
        String shortLine = line.replaceAll(" +", " ");

        // Through rigorous research, I found that splitting between spaces
        // divides the String into 11 substrings. Since this number does not
        // change in size, it is set to an array for performance.
        String dividedLine[] = new String[11];
        dividedLine = shortLine.split(" ");
        // Useful Indexes:
        // 1 = table number
        // 2 = sub-table number
        // 3 = record number
        // 4 = day
        // 5 = day of month
        // 6 = month
        // 7 = year
        // 8 = Running total of deaths that year
        // 9 = Killed in that accident.
        // 10 = Wounded in that accident

        // Begin storing data from the table's string.
        int tableNum = Integer.parseInt(dividedLine[1]);
        int tableSubNum = Integer.parseInt(dividedLine[2]);
        int recordNum = Integer.parseInt(dividedLine[3]);
        String dateDay = dividedLine[4];
        int dateNum = Integer.parseInt(dividedLine[5]);
        String dateMonth = dividedLine[6];
        int dateYear = Integer.parseInt(dividedLine[7]);
        int totalDeaths = Integer.parseInt(dividedLine[8]);
        int recordDeaths = Integer.parseInt(dividedLine[9]);
        int recordWounded = Integer.parseInt(dividedLine[10]);

        // Construct a new disasterRecord, and add it to the program's list.
        disasterRecord newRecord = new disasterRecord(tableNum, tableSubNum,
                recordNum, dateDay, dateNum, dateMonth, dateYear, totalDeaths,
                recordDeaths, recordWounded);
        record.add(newRecord);
    }

    // Algorithm that processes the record list, grouping each record by year
    // and finding data on the number of accidents, average wounded, total
    // wounded, average killed, and total killed for a given year, and then 
    // outputting that data..
    private static void groupByYear(ArrayList<disasterRecord> recordList)
    {
        // Note: it may have been more code efficient to make a class for each
        // year to track its statistics as I did with groupByWeekday. However,
        // doing it like this seemed more time-efficient at the time. Were I
        // to do it again, I probably would implement this program more
        // similarly to groupByWeekday.
        int year = 0;
        int numAccidents = 0;
        int totalWounded = 0;
        int totalKilled = 0;
        double averageWounded = 0;
        double averageKilled = 0;
        boolean firstRun = true;

        System.out.println("BY YEAR");

        // Group By Year Algorithm begins below.
        for (disasterRecord record : recordList)
        {
            // Updating year's data with records from the same year.
            if (record.getDateYear() == year)
            {
                numAccidents++;
                totalWounded += record.getRecordWounded();
                // The records keep a running count of total deaths already, 
                // so no calculations needed. We just grab the last count.
                totalKilled = record.getTotalDeaths();
            } else
            {
                // The first run needs to be seeded data without outputting any.
                if (firstRun)
                {
                    // Start the algorithm with the first year's data.
                    year = record.getDateYear();
                    numAccidents = 1;
                    totalWounded = record.getRecordWounded();
                    totalKilled = record.getTotalDeaths();
                    firstRun = false;
                } else
                // Processing all subsequent records on a new year.
                {
                    // Calculate Averages for the previous year.
                    averageWounded = totalWounded / numAccidents;
                    averageKilled = totalKilled / numAccidents;
                    // Show Output for the previous year.
                    System.out.println("Year: " + year
                            + " | # of Accidents: " + numAccidents
                            + " | Ave. Wounded: " + averageWounded
                            + " | total Wounded: " + totalWounded
                            + " | Ave. Killed " + averageKilled
                            + " | total Killed: " + totalKilled);
                    // Start the calculations for the new year.
                    year = record.getDateYear();
                    numAccidents = 1;
                    totalWounded = record.getRecordWounded();
                    totalKilled = record.getTotalDeaths();
                }
            }
        }
    }

    // Algorithm that processes the record list, grouping each record by day of
    // the week and finding data on the number of accidents, total wounded,  
    // and total killed for a given year, and then outputting that data..
    private static void groupByWeekday(ArrayList<disasterRecord> recordList)
    {
        // Array to store the weekdayRecord objects. Since we will be scanning
        // through the days and not adding or removing any, an array offers
        // the best performance for the many scans we will be doing.
        weekdayRecord[] weekdays = new weekdayRecord[7];

        // Create a weekdayRecord for every day of the week.
        weekdayRecord Sun = new weekdayRecord("Sun");
        weekdayRecord Mon = new weekdayRecord("Mon");
        weekdayRecord Tue = new weekdayRecord("Tue");
        weekdayRecord Wed = new weekdayRecord("Wed");
        weekdayRecord Thu = new weekdayRecord("Thu");
        weekdayRecord Fri = new weekdayRecord("Fri");
        weekdayRecord Sat = new weekdayRecord("Sat");

        // Store the weekdayRecords in the weekdays array.
        weekdays[0] = Sun;
        weekdays[1] = Mon;
        weekdays[2] = Tue;
        weekdays[3] = Wed;
        weekdays[4] = Thu;
        weekdays[5] = Fri;
        weekdays[6] = Sat;

        // Group By Weekday Algorithm begins below.
        // Walk through each record in the list.
        for (disasterRecord record : recordList)
        {
            // For each record, compare it to the avaiable weekdays to find the
            // weekday that matches the record.
            for (weekdayRecord day : weekdays)
            {
                // Once the matching weekday has been found, begin calculations.
                // Takes the current data per weekday, and adds the record's
                // date to it.
                if (record.getDateDay().equals(day.getName()))
                {
                    day.setNumAccidents(day.getNumAccidents() + 1);
                    day.setTotalWounded(day.getTotalWounded() + record.getRecordWounded());
                    // Each record also includes numbers of those who died in the
                    // recorded accident. But the first one reports a previous
                    // annual death count of -9999, and uses that recorded
                    // accident's death count as the annual total. The code
                    // below is a workaround for that situation, treating the
                    // recorded accident's death count as the annual one.
                    if (record.getRecordDeaths() < 0)
                    {
                        day.setTotalKilled(day.getTotalKilled() + record.getTotalDeaths());
                    } else
                    {
                        day.setTotalKilled(day.getTotalKilled() + record.getRecordDeaths());
                    }
                }
            }
        }

        // Header output.
        System.out.println("BY WEEKDAY");
        // Show Output and data for each weekday.
        for (weekdayRecord day : weekdays)
        {
            System.out.println("Day: " + day.getName()
                    + " | # of Accidents: " + day.getNumAccidents()
                    + " | Total Wounded: " + day.getTotalWounded()
                    + " | Total Killed: " + day.getTotalKilled());
        }
    }
}
