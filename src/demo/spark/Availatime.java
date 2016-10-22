package demo.spark;

/**
 * Created by xx on 10/19/16.
 */
public class Availatime {
    private String startTime;
    private String endTime;
    private String tendency;

    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public String getTendency(){
        return tendency;
    }
    public Availatime(String startTime, String endTime, String tendency){
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
    }
    public void setAvailatime(String startTime, String endTime, String tendency){
        this.startTime = startTime;
        this.endTime = endTime;
        this.tendency = tendency;
    }
}
