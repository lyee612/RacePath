package ADT;

import java.util.*;
public class Player {
    private String name;
    private long startTime;
    private long endTime;
    private long result;
    
    public Player(){}
    
    public Player(String name){
        this.name = name;
        this.startTime = System.currentTimeMillis()/1000;    
    }
    
    public String getPlayerName(){
        return name;
    }

    public long getResult(){
        return result;
    }
    
    public long calculateResult(){
        endTime = System.currentTimeMillis()/1000;
        result = endTime - startTime;
        return result;
    }
}
