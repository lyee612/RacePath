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

public class RacePath<T> implements RacePathInterface<T> {
    
    private T[] station;
    private int numberOfStations;
    private static final int DEFAULT_INITIAL_PATHSIZE = 10;
    private Node firstNode;
    
    public RacePath(){
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
    }
    
    @Override
    public boolean addStation(T newStation){
        if(isPathFull()){
            return false;
        }
        else
            station[numberOfStations] = newStation;
            numberOfStations++;
            return true;
    }

    @Override
    public boolean addStation(int newPosition, T newStation) {
       boolean isSuccessful = true;
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
        
        return isSuccessful;
    }
    
    @Override
    public T removeStation(int givenPosition){
        T result = null;
        if((givenPosition >= 0) && (givenPosition <= numberOfStations+1)){
            result = station[givenPosition-1];
            
            if(givenPosition < numberOfStations){
                removeGap(givenPosition);
            }
            numberOfStations--;
        }
        return result;
    }
    
    private boolean isPathFull(){
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
    }

    private class Node{
        private T data;
        private Node next;
        
        private Node(T data) {
        this.data = data;
        this.next = null;
        }

        private Node(T data,Node next) {
        this.data = data;
        this.next = next;
        }
        
    }

}
