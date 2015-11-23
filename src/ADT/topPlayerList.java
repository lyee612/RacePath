/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Zhen Yee
 */

import Java.Player;
import ADT.RacePath;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class topPlayerList<T extends Comparable<T>> implements topPlayerListInterface<T> {
   // private RacePath[] station;
    private int position; // singlePlayer position index
    private int numberOfPlayers;
    private T currentDuration;
    private Node firstNode;
    private static final int DEFAULT_TOPLIST = 10;
    
    public topPlayerList(){
        this(DEFAULT_TOPLIST);
    }
    
    public topPlayerList(int DEFAULT_TOPLIST){
        firstNode = null;
        numberOfPlayers = 0;
        position = 0;
        currentDuration = null;
    }
    
    public void rollDice(){
         Random random = new Random();
         int step = random.nextInt(-2) + 3 ; //the number will be generated between -2 and 3
         
         if(step == 0) //only allow -2,-1,1,2,3, so rollDice again
             rollDice();
         if(step>0)
             moveForward(step);
         if(step<0)
             moveBackward(step);
    }
    
    public int moveForward (int step){
        return position + step;
    }
    
    public int moveBackward (int step){
        return position - step;
    }
    
    public boolean addPlayerToList( T newDuration,T newPlayer){
        Node newNode = new Node(newPlayer,null);
        if(position == station.length -1){
            if(isEmpty()){
                firstNode = newNode;
                currentDuration = newDuration;
            }
            else{
                Node currentNode = firstNode;
                if(numberOfPlayers <= DEFAULT_TOPLIST) {
                if(currentDuration.compareTo(newDuration) < 0 )
                    newNode.next = currentNode;
                else
                    currentNode.next = newNode;
                }
            } 
        }
          return true;
    }
    
    public boolean isEmpty(){
        return firstNode == null ;
    }
    
    private class Node{
        private T data;
        private Node next;
        
        private Node(T data) {
        this.data = data;
        this.next = null;
        }

        private Node(T data, Node next) {
        this.data = data;
        this.next = next;
        }
        
    }
    
    public String displayPlayerRanking(){
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";
            currentNode = currentNode.next;
    }
       return outputStr;
    }
 
}
