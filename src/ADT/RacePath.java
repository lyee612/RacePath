
package ADT;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RacePath <T> implements RacePathInterface<T> {
   
    private int numberOfStations = 0;
    private Node firstNode= null;
    private Node lastNode = null;
    private Node currentNode = null;
    
    public RacePath(){}

    @Override
    // add station completed
    public boolean addStation(T newStation) {
        Node newNode = new Node(newStation);
        if(numberOfStations==0){ // adding first station to path
            firstNode = newNode;
            lastNode = newNode;
            currentNode=newNode;
        }
        else{  //already have existing station in path
            lastNode.next = newNode;  // adding new station after existing station
            newNode.prev = lastNode; // the station before new station is existing station
            lastNode = newNode;  // new station become last station
        }
        numberOfStations++; // increment total number of station in path
        return true;   
        }
    
    
    @Override  //check whether the station to be added already exist in path or not
    public boolean isExits(String stationName){
        Node temp = firstNode;
        while(temp != null){
            Station station = (Station)temp.data;
            if(station.getStationName().compareToIgnoreCase(stationName)==0){
                return true; // return true if station to be added already exist in current path
            }
            if(temp.next==null){
                break;
            }
            temp=temp.next;
        }
        return false;
    }
    
     @Override  // remove a station by user's choice
    public boolean removeStation(T station){
        boolean remove = false; 
        Node temp = firstNode;
        while(!remove){ 
        if(temp.data==station){ 
            if(temp==firstNode){  // if the station to be removed is first in the path
                firstNode= temp.next; 
                temp.next=null;
                temp.prev=null;
                remove = true;
                numberOfStations--;
            }
            else{ //if the station to be removed is in between the path or last in path
                temp.prev.next= temp.next; 
                if(temp != lastNode){ // if not the last node. EG: A<-->B<-->C >>> A<->C, B is removed
                    temp.next.prev = temp.prev; 
                }
                else{ // if station delete is lastNode , remove the lastNode by making previous node lastNode
                    lastNode = temp.prev;
                }
            temp.next=null; // remove any reference to tempNode
            temp.prev=null; // remove any pointing reference for tempNode
            remove = true; // remove success
            }
        }     
        else // station to be deleted not found
           temp= temp.next; //make temp to nextNode to be compared
        }
        return remove; 
    }
     
     
    @Override  //return the station name to compare with when removing station
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
    
    @Override  //move player's current position in path based on diceValue
    public void movePosition(int diceValue){ 
        if (diceValue >0){
            for (int i = 0; i < diceValue; i++){
                if(!checkWin())  //if player not win, then move the current position to next station
                    currentNode = currentNode.next;
                else{  //if player already won, stop moving player's position
                    break;
                }
            }
        }
        else{  //if diceValue < 0
            for (int i = 0; i > diceValue; i--){
                if(!(currentNode == firstNode)){ //if player's current position not at starting point
                    currentNode = currentNode.prev;
                }
            }
        } 
        Station currentStation = (Station) currentNode.data;
        System.out.println("Current Station is: " + currentStation.getStationName());
    }
    
    // return the station number
    public int getNumOfStations(){
        return numberOfStations;
    }
    
    
    @Override  // get station name
    public String getCurrentStationName(){
        Station currentStation = (Station) currentNode.data; //get currentNode data 
        return currentStation.getStationName();
    }  
   
    @Override  //check whether player won
    public boolean checkWin(){  
        if (currentNode==lastNode) //if current station = last station, player won, return true
            return true;
        else return false;
    }
    
    @Override // print total stations in path
    public String toString(){
     Node temp = firstNode;
     String str = "";
        while(temp != null){
            Station station = (Station)temp.data;
            str = str + station.getStationName()+ "\n";
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
