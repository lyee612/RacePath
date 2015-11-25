/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author lyee
 */
public class Station {
    String stationName;
    String[] questions;
    String[] answers;
    
    public Station(){}
    
    public Station(String name, String[] ques, String[] ans){
        this.stationName= name;
        this.questions = ques;
        this.answers = ans;
    }
    public String getStationName(){
        return stationName;
    }
    public void printQues( int i){
        System.out.println(questions[i]);
        System.out.print("Enter your answer >>> ");
    }
    
    public boolean checkAns(int i, String ans){
        if(answers[i].compareToIgnoreCase(ans)==0){
            System.out.println("*********Correct answer!! You may proceed************ ");
            return true;
        }
        else{ 
            System.out.println("*********Sorry, WRONG...*********");
            System.out.println(" You can not proceed to roll dice..Please try harder this time. ");
            return false;
    }}
    
  
}
