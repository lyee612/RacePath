/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;

/**
 *
 * @author Zhen Yee
 */
import java.util.*;
public class Player {
    private String name;
    private long startTime;
    private long endTime;
    private long result;
    
    public Player(String name){
        this.name = name;
        this.startTime = System.currentTimeMillis();    
    }
    
    public String getName(){
        return name;
    }
    
    public long getStartTime(){
        return startTime;
    }
    
    public long getEndTime(){
        return endTime;
    }
    public void setEndTime(long endTime){
        this.endTime = endTime;
    }
    
    public long getResult(){
        return result;
    }
    
    public long calculateResult(long startTime, long endTime){
        result = endTime - startTime;
        return result;
    }
}
