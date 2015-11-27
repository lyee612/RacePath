package ADT;
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
    
    //print Station's Question based on randomQuestion value passed in
    public void printQues( int i){
        System.out.println("------------");
        System.out.println(stationName);
        System.out.println("------------");
        System.out.println(questions[i]);
        System.out.print("Enter your answer >>> ");
    }
    
    // check whether player answer match with the question's answer
    public boolean checkAns(int i, String ans){
        if(answers[i].compareToIgnoreCase(ans)==0){ // if answer match player's answer
            System.out.println("*********Correct answer!! You may proceed************ ");
            System.out.println();
            return true;
        }
        else{  // if answer does not match player's answer
            System.out.println("*********Sorry, WRONG...*********");
            System.out.println(" You can not proceed to roll dice..Please try harder this time. ");
            System.out.println();
            return false;
        }
    }  
}
