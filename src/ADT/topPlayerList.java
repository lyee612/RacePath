package ADT;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class topPlayerList<T> implements topPlayerListInterface<T> {
 
    private Node firstNode;
    private Node beforeNode;
    private Node currentNode;
    private static int totalPlayerInList = 0;
    
    public topPlayerList(){
        firstNode = null;
    }
    
    @Override
    public boolean addPlayerToList(T newEntry){
        Node newNode = new Node(newEntry);
        boolean success=false;
            if(isEmpty()){
                firstNode = newNode;
                totalPlayerInList++;
                success = true;
            }
            else{ 
                currentNode = firstNode;
                for(int i = 0;i<(totalPlayerInList+1);i++){
                        //compare old player time and new player time
                        if(comparePlayersDuration(newNode.data,currentNode.data)){
                            //if firstNode duration longer than newNode 
                            if(i==0){ 
                                newNode.next = currentNode;
                                firstNode = newNode; // new entry will be the head 
                                success= true;
                                totalPlayerInList++;
                                if(totalPlayerInList>10){
                                    eliminatePlayer();
                                }
                                break;
                            }
                            else{ 
                                newNode.next = currentNode;
                                beforeNode.next = newNode; //new entry will be inserted into middle of node
                                success = true;
                                totalPlayerInList++;
                                if(totalPlayerInList>10){
                                    eliminatePlayer();
                                }
                                break;
                            }
                        }
                        else{ // new player duration is longer than existing player 
                            beforeNode = currentNode;
                            currentNode = currentNode.next;  // move to compare new node with next existing node
                            if(currentNode==null){
                                beforeNode.next=newNode;
                                newNode=currentNode;
                                success = true;
                                totalPlayerInList++;
                                if(totalPlayerInList>10){
                                    eliminatePlayer();
                                }
                                break;
                            } //end if current node == null
                        }
            }//close for loop
        }// end else for !isEmpty()
        return success; // return false if adding to list failed..
    } // end of addPlayerToList()
    
    
    @Override //compare players' duration
    public boolean comparePlayersDuration(T newP,T existP){
        Player newPlayer = (Player) newP;
        Player existPlayer = (Player) existP;
        if(newPlayer.getResult()<existPlayer.getResult())
            return true; //return true if newPlayer duration < existPlayer duration
        return false;
    }
       
    @Override //eliminate player ranked 11 from the list
    public void eliminatePlayer(){
        Node deleteNode=firstNode;
        Node beforeDelete =null;
        for(int i =0; i<10;i++){ //find the player ranked 11 (int = 9) 
           beforeDelete=deleteNode;
           deleteNode=deleteNode.next;
        }
        beforeDelete.next=null; 
    }
    
    @Override //return list of player in ranking
    public String displayRanking(){
        String outputStr = "";
        Node tempNode = firstNode;
        int i = 1;
        while (tempNode != null) {
            Player temp = (Player) tempNode.data;
            outputStr += i + "\t"+ temp.getPlayerName() + "\t\t" + temp.getResult() + " s\n";
            tempNode = tempNode.next;
            i++;
        }
        return outputStr;
    }

    @Override //check whether any player exist in ranking
    public boolean isEmpty() {
        return firstNode == null;
    }
 
    private class Node{
        private T data;
        private Node next;
        
        private Node(T data) {
        this.data = data;
        this.next = null;
        }
    } 
}
