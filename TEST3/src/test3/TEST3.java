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
        String fileName = args[0];
        ArrayList<disasterRecord> recordsList = new ArrayList<disasterRecord>();

        // This file reading code comes from an earlier Java project for
        // a class at the SRJC, CS 17.11.It has been modified slightly to
        // accept the filename scheme.
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

//        for(disasterRecord record: recordsList){
//            System.out.println(record.getRecordNum());
//        }
//        groupByYear(recordsList);
        groupByWeekday(recordsList);
    }

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
        int recordWounded = Integer.parseInt(dividedLine[10]);

        // Construct a new disasterRecord, and add it to the program's list.
        disasterRecord newRecord = new disasterRecord(tableNum, tableSubNum,
                recordNum, dateDay, dateNum, dateMonth, dateYear, totalDeaths,
                recordWounded);
        record.add(newRecord);
    }

    // Algorithm that processes the record list, grouping each record by year
    // and finding data on the number of accidents, average wounded, total
    // wounded, average killed, and total killed for a given year, and then 
    // outputting that data..
    private static void groupByYear(ArrayList<disasterRecord> recordList)
    {
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
                            + ", # of Accidents: " + numAccidents
                            + ", Ave. Wounded: " + averageWounded
                            + ", total Wounded: " + totalWounded
                            + ", Ave. Killed " + averageKilled
                            + ", total Killed: " + totalKilled);
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
        for (disasterRecord record : recordList)
        {
            for (weekdayRecord day : weekdays){
                if(record.getDateDay().equals(day.getName())) {
                    System.out.println(day.getName() + " = " + record.getDateDay());
                    
                }
            }
        }
    }
}
