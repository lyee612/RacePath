/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Java;
import java.util.*;
import ADT.*;



public class TestRace {

    static Scanner sc = new Scanner(System.in);
    static int life = 5;
    static int numOfPlayer = 0;
    static final String[] quesA = {"20 - 1 =", "7 * 7 ="};
    static final String[] quesB={"Human is homo-sapien.( 1=true; 0=false)", "Cooper is cute( 1=true; 0=false)"};
    static final String[] ansA = {"19", "49"};
    static final String[] ansB = {"1", "1"};
    static final Station stationA = new Station("Station A", quesA, ansA);
    static final Station stationB = new Station("Station B",quesB, ansB);
    
    public static void main(String[] args) {
        mainMenu(); 
    }
    
    public static void mainMenu(){
       
        welcomeBanner();
        int choice = menu();
        while(choice!=-1)
            
            if (choice==1){
                startGame();  
                choice = menu();
               }
            else if(choice==2){
                managePath();
                choice = menu();
            }
             else if(choice==3){
                topPlayer();
                choice = menu();
            }
            else{
                System.out.println("Invalid Input!!");
                choice = menu();
            }
        
         endGame();
    }
    
    public static void welcomeBanner(){
        System.out.println("Welcome to Cooper Games Inc.!!");
        System.out.println("====================================");
    }
    
    public static int menu(){
        System.out.println("1. Start Game (1)");
        System.out.println("2. Race Path Manage (2)");
        System.out.println("3. Top 10 Player (3)");
        System.out.println("4. Exit (-1)");
        System.out.println("Please select your choice:");
        int choice = sc.nextInt();
        return choice;
        }
     
    public static void startGame(){
        System.out.println("Please enter your name: ");
        String playerName = sc.nextLine(); 
        
       // topPlayerList<Player> player = new topPlayerList<Player>();
        
        Player player1 = new Player(playerName);
        System.out.println("You now have "+ life  );
        
       /* Station Temp = new Station();
        
        Die dice = new Die();
        int faceValue;
        boolean result;
        String question,answer,reply,extra,end;
        //String reply;   
        tStart = System.currentTimeMillis();//Time Start count
        System.out.println("Total Station : " + routeA.numberOfEntries);
        do
        {
            if(life < 1){
                loseGame();
                break;
            }
            System.out.println("________________________________________________________" + newLine);
            System.out.println("Roll a Dice >>>>");
            reply = "y";
            promptEnterKey();
     
            if(reply.compareToIgnoreCase("y") != 0){
                System.out.println("Game Over");
                break;
            }
                
            System.out.println();
            routeA.move();
            
            Temp = routeA.getPositionData();
            if(Temp == null){
                break;
            }
            else{
                System.out.println("move "+routeA.getFaceValue()+ " step");
                System.out.println("<<< Station List >>>");
                System.out.println(routeA.toString());     
                promptEnterKey();
                System.out.println(newLine +"You are now at station "+Temp.getName());
                System.out.println(newLine + "Question :");
                

                do{
                    faceValue = dice.roll();    //roll dice
                    question = Temp.getQuestion(faceValue);     //get question base on dice
                    System.out.println(question);       //display question
                    answer = sc.nextLine();  // get answer
                    result = Temp.checkAnswer(faceValue,answer);     //check answer
                       
                    if(!result){
                        life--;
                        System.out.println("You still have " + life + " life(s). Please try another question.");
                    }
                        
                }while(!result && life > 0);
                System.out.println("You still have " + life + " life(s)");
            }     
        }while(reply.compareToIgnoreCase("y") == 0);   
        
        if(life > 0 && Temp == null){
            winGame();
            if(checkExtra)
                extra();
        }*/
       
    
    }
    
    public static void managePath(){
        RacePath<Station> path = new RacePath<Station>();
        
        System.out.println("1. Add Station (1)");
        System.out.println("2. Delete Station (2)");
        System.out.println("3. Back (3)");
        int choice = sc.nextInt();
        if (choice ==1 ){
           
            System.out.println("Enter Station (A/B/C/D) :");
            String stationName = "Station "+ sc.next().toUpperCase();
            if(stationA.getStationName()==stationName){
                path.addStation(stationA);
                System.out.println("You success added "+ stationName + "to Race Path");
            }
            else if(stationB.getStationName()==stationName){
                path.addStation(stationB);
                System.out.println("You success added "+ stationName + "to Race Path");
            }
            else
                System.out.println("Station adding failed..");
                System.out.println("Current Stations in Race Path :");
                System.out.println("=================================");
                System.out.println(path.toString()); 
        }  
        //return routeA;
    }
    
    public static void topPlayer(){
    
    }
    
    public static void endGame(){
        System.out.println("Thanks for playing!!");
        System.out.println("See you again!!");
    }
    
    
    }



