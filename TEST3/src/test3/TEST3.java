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
        groupByYear(recordsList);
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

    private static void groupByYear(ArrayList<disasterRecord> recordList)
    {
        int year = 0;
        int numAccidents = 0;
        int totalWounded = 0;
        int totalKilled = 0;
        double averageWounded = 0;
        double averageKilled = 0;
        boolean firstRun = true;

        for (disasterRecord record : recordList)
        {
            if (record.getDateYear() == year)
            {
                numAccidents++;
                totalWounded += record.getRecordWounded();
                totalKilled = record.getTotalDeaths();
            } else
            {
                if (firstRun)
                {
                    // Start the first Year
                    year = record.getDateYear();
                    numAccidents = 1;
                    totalWounded = record.getRecordWounded();
                    totalKilled = record.getTotalDeaths();
                    firstRun = false;
                } else
                {
                    // Calculate Averages
                    averageWounded = totalWounded / numAccidents;
                    averageKilled = totalKilled / numAccidents;
                    // Show Output
                    System.out.println("Year: " + year
                            + ", # of Accidents: " + numAccidents
                            + ", Ave. Wounded: " + averageWounded
                            + ", total Wounded: " + totalWounded
                            + ", Ave. Killed " + averageKilled
                            + ", total Killed: " + totalKilled);
                    // Start Next Year
                    year = record.getDateYear();
                    numAccidents = 1;
                    totalWounded = record.getRecordWounded();
                    totalKilled = record.getTotalDeaths();
                }
            }
        }
    }
}
