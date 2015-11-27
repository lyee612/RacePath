
package ADT;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RacePath <T> implements RacePathInterface<T> {
    
    //private T[] station;
    private int numberOfStations = 0;
    private Node firstNode= null;
    private Node lastNode = null;
    private Node currentNode = null;
    
    
    public RacePath(){
        
    }

    @Override
    // add station completed
    public boolean addStation(T newStation) {
      Node newNode = new Node(newStation);
        
        if(numberOfStations==0){ // 1st station added
            firstNode = newNode;
            lastNode = newNode;
            currentNode=newNode;
        }
        else{
            lastNode.next = newNode;
            newNode.prev = lastNode;
            lastNode = newNode;
        }
        numberOfStations++;
        return true;   
        }
    // return the station number
    public int getNumOfStations(){
        return numberOfStations;
    }
    // get station name
    public String getCurrentStationName(){
        Station currentStation = (Station) currentNode.data;
        return currentStation.getStationName();
    }  
    
    public boolean isExits(String stationName){
        Node temp = firstNode;
        while(temp != null){
            Station station = (Station)temp.data;
            if(station.getStationName().compareToIgnoreCase(stationName)==0){
                return true;
            }
            if(temp.next==null){
                break;
            }
            temp=temp.next;
        }
        return false;
    }
    
    @Override
    public boolean removeStation(T station){
        boolean remove = false; 
        Node temp = firstNode;
        while(!remove){ 
        if(temp.data==station){
            if(temp==firstNode){ 
                firstNode= temp.next; 
                //lastNode=temp.next; error!siao eh
                temp.next=null;
                temp.prev=null;
                remove = true;
                numberOfStations--;
            }
            else{
            temp.prev.next= temp.next;
            if(temp != lastNode){ // if not the last node. the A<->B<->C --> A<-C
                temp.next.prev = temp.prev; 
            }
            else{ // if lastNode , move the lastNode lalala~
                lastNode = temp.prev;
            }
            temp.next=null;
            temp.prev=null;
            remove = true;
            }
        }     
        else
           temp= temp.next;
        }
        return remove; 
    }
    //return the station name to compare when remove
    public String findStation(int i){
        Node temp = firstNode;
        for(int j=0;j<i;j++){
            if(temp.next==null){ // the station remove is not exist!
                return "";
            }
            temp = temp.next;
        }
        Station sta = (Station)temp.data;
        return sta.getStationName();
    }
    
    public void movePosition(int diceValue){
        
        if (diceValue >0){
            for (int i = 0; i < diceValue; i++){
                if(!checkWin())
                    currentNode = currentNode.next;
                else{
                    break;
                }
            }
        }
        else{
            for (int i = 0; i > diceValue; i--){
                if(!(currentNode == firstNode)){
                    currentNode = currentNode.prev;
                }
            }
        } 
        Station currentStation = (Station) currentNode.data;
        System.out.println("Current Station is: " + currentStation.getStationName());
    }
    
    public boolean checkWin(){
        if (currentNode==lastNode)
            return true;
        else return false;
    }

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
            this.prev= null;
            this.next=null;
        }
                
        public String toString(){
            return data.toString();
        }
    }

}
