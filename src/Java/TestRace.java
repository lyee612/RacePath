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
    
    static final String[] quesA = {"20 - 1 =", "7 * 7 =","890 * 23 =","Time is Valuable.[True(1)/False(0)]","22 % 2 ="};
    static final String[] quesB={"Human is homo-sapien.( 1=true; 0=false)", "Cooper is cute( 1=true; 0=false)","1242 - 901 =","7232 - 22=","672 * 90 ="};
    static final String[] quesC = {"1. Which country currently emits the most greenhouse gases? A. United States B. China C. India D. England","2 ^ 3= ", "3 * 7 = ","Any number can divide by 0? [Can(1)/Cannot(0)]","3 ^ 3 = "};
    static final String[] quesD = {"5+|-11|+11 = ", "-8+5(2-4) = ", "48/(-4)^2 + (-9) = ", "-15-(-7) = ","7+(-17)+4 = "};
    static final String[] quesE = {"2w+w = ", "3w + 4(2+w) = ", "5(w+1) +2 = ", "6w+18w = ", "2w+3(5w+2) = "};
    static final String[] quesF = {"3(v+w) = ", "2(2v-3w) = ", "(8v+8w)/(2v+2w) = ","2(5v+3w) = ","2(6v)+w = "};
    static final String[] quesG = {"50 * 0.2 = ", "4 * 80 = ", "9 * 9 = ", "4 * 4 * 4 = ", "3 * 8 * 2 = "};
    
    static final String[] ansA = {"19", "49","20470","0","0"};
    static final String[] ansB = {"1", "1", "341", "7210","60480"};
    static final String[] ansC = {"B","8","21","0","27"};
    static final String[] ansD = {"27","-18","-6","-8","-6"};
    static final String[] ansE = {"3w","7w+8","5w+7","24w","17w+6"};
    static final String[] ansF = {"3v+3w","4v-6w","4","10v+6w","12v+w"};
    static final String[] ansG = {"10","320","81","64","48"};
    
    static final Station[] station ={new Station("Station A", quesA, ansA),
                                      new Station("Station B",quesB, ansB),
                                      new Station("Station C", quesC, ansC),
                                      new Station("Station D",quesD, ansD),
                                      new Station("Station E", quesE, ansE),
                                      new Station("Station F",quesF, ansF),
                                      new Station("Station G", quesG, ansG)} ;
   
  static RacePath<Station> path;
    
    public static void main(String[] args) {
         path = new RacePath<Station>();
        mainMenu(); 
    }
    
    public static void mainMenu(){
      
       path.addStation(station[0]);
        path.addStation(station[1]);
        path.addStation(station[2]);
        
        welcomeBanner();
        int choice = menu();
        while(choice!=-1)
            
            if (choice==1){
                startGame();  
                choice = menu();
               }
            else if(choice==2){
                managePath();
                //managePath();
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
        System.out.println("===================================");
        System.out.println("Welcome to Cooper Games Inc.!!");
    }
    
    public static int menu(){
         System.out.println("===================================");
        System.out.println("1. Start Game (1)");
        System.out.println("2. Race Path Manage (2)");
        System.out.println("3. Top 10 Player (3)");
        System.out.println("4. Exit (-1)");
        System.out.println("Please select your choice:");
        int choice = sc.nextInt();
        sc.nextLine();
                
        return choice;
        }
     
    public static void startGame(){
        
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine(); 
        
       // topPlayerList<Player> player = new topPlayerList<Player>();
        
        Player player1 = new Player(playerName);
        System.out.println("You now have "+ life + "life");
          
        boolean proceed = true;
        boolean matched = true;
         while(proceed && !path.checkWin()){
             if(life<1){
             gameOver();
             break;
            }
             else{
                String reply; 
               
                
                String currentStation = path.getCurrentStationName();
              
                for(int i = 0; i< 7; i++){
                    if(station[i].getStationName().compareToIgnoreCase(currentStation)==0){
                        do{ 
                            if(!matched)
                                life--;
                            if(life<=0){
                                gameOver();
                                proceed=false;
                                break;
                            }
                            int randomNum = randomQues();
                            System.out.println(life);
                            station[i].printQues(randomNum);
                            reply = sc.nextLine();
                            matched = station[i].checkAns(randomNum, reply);
                        }while(!matched);
                        break;
                    }
                }
             System.out.println("You now have "+ life +" life.");  
             proceed = getPermission();
             if(proceed!=false){
                int diceValue = rollDice();
                path.movePosition(diceValue);
             }}};
            
         if(!proceed){
             System.out.println("Exiting game..");
             path = new RacePath<Station>();
             mainMenu();
         }
         if(path.checkWin()){
             System.out.println("Congratulations!! You Won!!! ");
             mainMenu();
             
         }}
         
         
         
      
    
    public static boolean getPermission(){
        System.out.println("Type any positive number to roll a dice (-1 to exit game):");
        int val = sc.nextInt();
        sc.nextLine();
        if (val>0)
            return true;
        else 
            return false;
    }
    
    public static int rollDice(){
        Random random = new Random();
         int dice = random.nextInt(5) -2 ;
         if (dice==0){
             dice = rollDice();
         }
         else
         System.out.println("Your dice number is :" + dice);
         return dice;
    }
    
    public static int randomQues(){
        Random random = new Random();
        int randomValue = random.nextInt(4);
        return randomValue;
    }
    
    public static void managePath(){
        currentStation(path);
        System.out.println("=================================");
        System.out.println("1. Add Station (1)");
        System.out.println("2. Delete Station (2)");
        System.out.println("3. Back (3)");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice ==1 ){
            System.out.println("Enter Station (A-G : enter one) :");
            String stationName = "Station "+ sc.next().toUpperCase();
            switch(stationName){
                case "A":
                   path.addStation(station[0]);
                    break;
                case "B":
                   path.addStation(station[0]);
                    break;
                case "C":
                   path.addStation(station[0]);
                    break;
                case "D":
                   path.addStation(station[0]);
                    break;
                case "E":
                   path.addStation(station[0]);
                    break;
                case "F":
                   path.addStation(station[0]);
                    break;
                case "G":
                   path.addStation(station[0]);
                    break;
                default:
                    System.out.println("Station adding failed..\n");
            }
            currentStation(path);
            managePath();        
        }
        else if(choice ==2 ){
            System.out.println("Enter Station to delete(A-G: enter one) : ");
            String stationDelete = "Station "+sc.next().toUpperCase();
            boolean found= false;
            for(int i=0;i<path.getNumOfStations();i++){
                if(stationDelete.compareToIgnoreCase(station[i].getStationName())==0){
                    found=true;
                    path.removeStation(station[i]);
                    System.out.println( stationDelete + " is succeccfully deleted from Race Path");
            }}
            if(!found){
                System.out.println("Station not exits..");
                System.out.println("Station deleting failed..\n");
            }
            currentStation(path);
            managePath();
        }
        else if(choice==3)
            menu();
        else
            
    }
    
     public static void currentStation(RacePath path){
           System.out.println("Current Stations in Race Path :");
            System.out.println("=================================");
            System.out.println(path.toString()); 
        }
    
    public static void topPlayer(){
    
    }
    
    public static void endGame(){
        System.out.println("============================");
        System.out.println("Thanks for playing!!");
        System.out.println("See you again!!");
    }
    
    public static void gameOver(){
        System.out.println("You have 0 life left..");
        System.out.println("Game Over!!");
        System.out.println("Try again next time. ^o^");
        path = new RacePath<Station>();
       
    }
}



