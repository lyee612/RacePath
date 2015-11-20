/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

/**
 *
 * @author Zhen Yee
 */
import java.util.Calendar;
public class Player {
    private String name;
    private String id;
    private Calendar startTime;
    private Calendar endTime;
    private long result;
    
    public Player(){
        this.name = name;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.result = result;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
     public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    
    public Calendar getStartTime(){
        return startTime;
    }
    public void setStartTime(Calendar startTime){
        this.startTime = startTime;
    }
    
    public Calendar getEndTime(){
        return endTime;
    }
    public void setEndTime(Calendar endTime){
        this.endTime = endTime;
    }
    
    public long getResult(){
        return result;
    }
    
    public void setResult(long result){
        this.result = result;
    }
    
    public long calculateResult(Calendar startTime, Calendar endTime){
        result = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        return result;
    }
}
