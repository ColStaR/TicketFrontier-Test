/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;


import java.io.*;

/**
 * TicketFrontier Test 3
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

        // This file reading code comes from an earlier Java project for
        // a class at the SRJC, CS 17.11.It has been modified slightly to
        // accept the filename scheme.
        try {
            String line = "";
            FileReader fileReader =  new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");        
        }
    }
    
}
