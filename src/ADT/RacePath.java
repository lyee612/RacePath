/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Zhen Yee
 */
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RacePath <T> implements RacePathInterface<T> {
    
    //private T[] station;
    private int numberOfStations = 0;
    private Node firstNode= null;
    private Node lastNode = null;
    private Node currentNode = null;

    /*public RacePath(){
        this(DEFAULT_INITIAL_PATHSIZE);
    }
    
   public RacePath(int initialPathSize){
        numberOfStations = 0;
        station = (T[]) new Object[initialPathSize];
    }
   
   @Override
    public T[] createRacePath(int pathSize) {
         station = (T[]) new Object [pathSize];
         return station;
    }*/
    
   /* @Override
    public boolean addStation(T newStation){
        if(isPathFull()){
            return false;
        }
        else
            station[numberOfStations] = newStation;
            numberOfStations++;
            return true;
    }*/

    @Override
    public boolean addStation(T newStation) {
      Node newNode = new Node(newStation);
        
        if(numberOfStations==0){
            firstNode = newNode;
            lastNode = newNode;
        }
        else{
            lastNode.next = newNode;
            newNode.prev = lastNode;
            lastNode = newNode;
        }
        numberOfStations++;
        return true;   
        }
    
    public int getNumOfStations(){
            return numberOfStations;
    }
    
        /*boolean isSuccessful = true;
        if((newPosition >= 1) && (newPosition <= numberOfStations +1) ){
            if(isPathFull()){
               isSuccessful = false;
            }
        makeRoom(newPosition);
        station[newPosition] = newStation;
        numberOfStations++;
        }
        else
            isSuccessful = false;
        
        return isSuccessful;*/
    
    
    @Override
    public boolean removeStation(T station){
        boolean remove = false;
        Node temp = firstNode;
        while(!remove){
        if(temp.data==station){
             if(temp==firstNode){
                 firstNode= temp.next;
                 lastNode=temp.next;
                 temp.next=null;
                 temp.prev=null;
                 remove = true;
                 numberOfStations--;
             }
             else{
            temp.prev.next= temp.next;
            temp.next = null;   
            numberOfStations--;
            remove = true;
             }
        }     
        else
            temp= temp.next;
        }
        return remove; 
    }
  
    
   /* private boolean isPathFull(){
        return numberOfStations == station.length;
    }
    
    private void makeRoom(int newPosition){
        int newIndex = newPosition - 1;
        int lastIndex = numberOfStations -1;
        
        for (int i = lastIndex; i>=newIndex; i--){
            station[i+1] = station[i];
        }
    }
    
    private void removeGap(int givenPosition){
        int removedIndex = givenPosition - 1 ;
        int lastIndex = numberOfStations -1 ;
        
        for(int i= removedIndex; i<lastIndex; i++){
            station[i] = station[i+1];
        }
    }
    
    @Override
    public void changeStationSquence(){
        Random random = new Random();
        int randomIndex = random.nextInt(0)+ numberOfStations -1; // set the range of random number
        for(int i = numberOfStations-1; i>0; i--){
            T a = station[randomIndex];
            station[randomIndex] = station[i];
            station[i] = a;
        }
    }*/
    public String toString(){
     Node temp = firstNode;
     String str = "";
        while(temp != null){
            Station station = (Station)temp.data;
            str = str + station.getStationName()+ "\n";
            /*if(getPosition() == temp){
                output += "  *** Current Position ***";
            }*/
            temp = temp.next;
        }
        return str;
    }

    private class Node{
        private T data;
        private Node next;
        private Node prev;
        
        public Node(T data){
            this.data = data;
        }
                
        public String toString(){
            return data.toString();
        }
    }

}
