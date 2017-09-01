package test3;

/**
 * Author: Ryan Wong Date: Aug 31, 2017 Project Name: TEST3 
 *  Description: Record for each disaster being described in each line of T08.1.
 */
public class disasterRecord
{

    private int tableNum = -999;
    private int tableSubNum = -999;
    private int recordNum = -999;
    private String dateDay = "";
    private int dateNum = -999;
    private String dateMonth = "";
    private int dateYear = -999;
    private int totalDeaths = 0;
    private int recordWounded = 0;

    public disasterRecord()
    {
    }

    public disasterRecord(int tableNum, int tableSubNum, int recordNum,
            String dateDay, int dateNum, String dateMonth, int dateYear,
            int totalDeaths, int recordWounded)
    {
        this.tableNum = tableNum;
        this.tableSubNum = tableSubNum;
        this.recordNum = recordNum;
        this.dateDay = dateDay;
        this.dateNum = dateNum;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.totalDeaths = totalDeaths;
        this.recordWounded = recordWounded;
    }

    public int getTableNum()
    {
        return tableNum;
    }

    public void setTableNum(int tableNum)
    {
        this.tableNum = tableNum;
    }

    public int getTableSubNum()
    {
        return tableSubNum;
    }

    public void setTableSubNum(int tableSubNum)
    {
        this.tableSubNum = tableSubNum;
    }

    public int getRecordNum()
    {
        return recordNum;
    }

    public void setRecordNum(int recordNum)
    {
        this.recordNum = recordNum;
    }

    public String getDateDay()
    {
        return dateDay;
    }

    public void setDateDay(String dateDay)
    {
        this.dateDay = dateDay;
    }

    public int getDateNum()
    {
        return dateNum;
    }

    public void setDateNum(int dateNum)
    {
        this.dateNum = dateNum;
    }

    public String getDateMonth()
    {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth)
    {
        this.dateMonth = dateMonth;
    }

    public int getDateYear()
    {
        return dateYear;
    }

    public void setDateYear(int dateYear)
    {
        this.dateYear = dateYear;
    }

    public int getTotalDeaths()
    {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths)
    {
        this.totalDeaths = totalDeaths;
    }

    public int getRecordWounded()
    {
        return recordWounded;
    }

    public void setRecordWounded(int recordWounded)
    {
        this.recordWounded = recordWounded;
    }

}
