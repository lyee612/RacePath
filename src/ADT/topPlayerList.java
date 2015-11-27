package ADT;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class topPlayerList<T> implements topPlayerListInterface<T> {
 
    private Node firstNode;
    private Node beforeNode;
    private Node currentNode;
    
    public topPlayerList(){
        firstNode = null;
    }
    
    @Override
    public boolean addPlayerToList(T newEntry){
        Node newNode = new Node(newEntry);
        Player newPlayer = (Player) newNode.data;
        boolean success=false;
            if(isEmpty()){
                firstNode = newNode;
                success = true;
            }
            else{ 
                currentNode = firstNode;
                for(int i = 0;i<10;i++){
                        Player existPlayer = (Player) currentNode.data;
                        //compare old player time and new player time
                        if( newPlayer.getResult()<existPlayer.getResult()){
                            //if firstNode duration longer than newNode 
                            if(i==0){ 
                                newNode.next = currentNode;
                                firstNode = newNode; // new entry will be the head 
                                success= true;
                                break;
                            }
                            else{ 
                                newNode.next = currentNode;
                                beforeNode.next = newNode; //new entry will be inserted into middle of node
                                success = true;
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
                                break;
                            } //end if current node == null
                        }
            }//close for loop
        }// end else for !isEmpty()
        return success; // return false if adding to list failed..
    } // end of addPlayerToList()
       
    @Override //return list of player in ranking
    public String displayRanking(){
        String outputStr = "";
        Node tempNode = firstNode;
        int i = 1;
        while (tempNode != null) {
            Player temp = (Player) tempNode.data;
            outputStr += i + "\t"+ temp.getPlayerName() + "\t\t" + temp.getResult() + " seconds.\n";
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
