package demo.spark;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by xx on 10/19/16.
 */
public class Availatime {
    private String date;
    private String startTime;
    private String endTime;
    private String tendency;
    private String userName;

    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public String getTendency(){
        return tendency;
    }
    public String getDate(){return date;}
    public String getUserName(){return userName;}
    public Availatime(String date, String startTime, String endTime, String tendency, String userName){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
        this.userName = userName;
    }
    public void setAvailatime(String date, String startTime, String endTime, String tendency,String userName){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
        this.userName = userName;
    }
}
