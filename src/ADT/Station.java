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
    public String getQues(int i){
        return questions[i-1];
    }
    
    public boolean checkAns(int i, String ans){
        if(answers[i-1]==ans)
            return true;
        else return false;
    }
}
