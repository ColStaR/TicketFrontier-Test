package test3;

/**
 * Author: Ryan Wong Date: Aug 31, 2017 Project Name: TEST3 Description: Record
 * for each disaster being described in each line of T08.1.
 */
public class weekdayRecord
{

    private String name = "";
    private int numAccidents = 0;
    private int totalWounded = 0;
    private int totalKilled = 0;

    public weekdayRecord(String name, int numAccidents, int totalWounded, int totalKilled)
    {
        this.name = name;
        this.numAccidents = numAccidents;
        this.totalWounded = totalWounded;
        this.totalKilled = totalKilled;
    }
    
    public weekdayRecord(String name)
    {
        this.name = name;
    }

    public weekdayRecord()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumAccidents()
    {
        return numAccidents;
    }

    public void setNumAccidents(int numAccidents)
    {
        this.numAccidents = numAccidents;
    }

    public int getTotalWounded()
    {
        return totalWounded;
    }

    public void setTotalWounded(int totalWounded)
    {
        this.totalWounded = totalWounded;
    }

    public int getTotalKilled()
    {
        return totalKilled;
    }

    public void setTotalKilled(int totalKilled)
    {
        this.totalKilled = totalKilled;
    }

}
