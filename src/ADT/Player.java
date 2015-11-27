package ADT;

import java.util.*;
public class Player {
    private String name;
    private long startTime;
    private long endTime;
    private long result;
    
    public Player(){}
    
    // create a new player & record player's starting time
    public Player(String name){
        this.name = name;
        this.startTime = System.currentTimeMillis()/1000;  //change to seconds instead of millisecond by divide 1000
    }
    //  return player Name 
    public String getPlayerName(){
        return name;
    }
    // return player's time duration to win
    public long getResult(){
        return result;
    }
    // calculate player's time duration to win
    public void calculateResult(){
        endTime = System.currentTimeMillis()/1000; //change to seconds instead of millisecond by divide 1000
        result = endTime - startTime;
    }
}
