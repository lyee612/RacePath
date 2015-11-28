package Java;
/**
 *
 * @author LiYee,ZhenYee,SweeYong
 */
import java.util.*;
import ADT.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestRace {

    static Scanner sc = new Scanner(System.in);
   
    //initialized questions for specific station
    static final String[] quesA = {"20 - 1 =", "7 * 7 =","890 * 23 =","72 + 88 =","22 % 2 ="};
    static final String[] quesB={"80 % 6 =", "902 - 872 =",
                                 "1242 - 901 =","7232 - 22=","672 * 90 ="};
    static final String[] quesC = {"log10 / log2 = (round to 2 dp)", "2 ^ 3= ", "3 * 7 = ","Any number can divide by 0? [Can(1)/Cannot(0)]","3 ^ 3 = "};
    static final String[] quesD = {"5 + |-11| + 11 = ", "-8 + 5(2-4) = ", "48 / (-4)^2 + (-9) = ", "-15 -(-7) = ","7+(-17)+4 = "};
    static final String[] quesE = {"2w + w = ", "3w + 4(2+w) = ", "5(w+1) +2 = ", "6w + 18w = ", "2w + 3(5w+2) = "};
    static final String[] quesF = {"3(v+w) = ", "2(2v-3w) = ", "(8v + 8w)/(2v + 2w) = ","2(5v + 3w) = ","2(6v) + w = "};
    static final String[] quesG = {"50 * 0.2 = ", "4 * 80 = ", "9 * 9 = ", "4 * 4 * 4 = ", "3 * 8 * 2 = "};
   
    //initialise answer for each questions 
    static final String[] ansA = {"19", "49","20470","160","0"};
    static final String[] ansB = {"2", "30", "341", "7210","60480"};
    static final String[] ansC = {"3.32","8","21","0","27"};
    static final String[] ansD = {"27","-18","-6","-8","-6"};
    static final String[] ansE = {"3w","7w+8","5w + 7","24w","17w+6"};
    static final String[] ansF = {"3v+3w","4v-6w","4","10v+6w","12v+w"};
    static final String[] ansG = {"10","320","81","64","48"};
    
    //initialise station with relevent questions and answer
    static final Station[] station ={new Station("Station A", quesA, ansA),
                                     new Station("Station B", quesB, ansB),
                                     new Station("Station C", quesC, ansC),
                                     new Station("Station D", quesD, ansD),
                                     new Station("Station E", quesE, ansE),
                                     new Station("Station F", quesF, ansF),
                                     new Station("Station G", quesG, ansG),};
   
    static RacePath<Station> path;
    static topPlayerListInterface<Player> topList = new topPlayerList<Player>(); //create new topPlayerList
    static boolean firstTimePrintPlayer = false; //indicate first time printing to text file
    
    public static void main(String[] args) {
        printQuestionTextFile(); //print question to text file
        printAnswerTextFile(); //print answer to text file
        mainMenu();
    }
    
    // where user can choose what to do in the game
    public static void mainMenu(){
        resetPath(); //reset to new path
        welcomeBanner();
        int choice = 0;
        do{ // auto come back !!
            choice = menu();
            if (choice==1){ // start game
                startGame();
            }
            else if(choice==2){ // manage path
                managePath();
            }   
             else if(choice==3){ //show top 10 player
                topPlayer();
            }
             else if(choice==4){
                 break;
             }
            else{
                System.out.println("Invalid Input!!");//reenter input
            }
        }while(choice!=-1);
        endGame(); //if the choice is -1
    }
     // to reset the race path before and after every game. 
    public static void resetPath(){ 
        path = new RacePath<Station>();
        initializePath(); 
    }
    
     //Initialise default station to path
    public static void initializePath(){
        path.addStation(station[0]);
        path.addStation(station[1]);
        path.addStation(station[2]);
    }
    
    public static void welcomeBanner(){
        System.out.println("===================================");
        System.out.println("Welcome to Cooper Games Inc.!!");
    }
    
    //player choose what to do with the game
    public static int menu(){
        System.out.println("===================================");
        System.out.println("1. Start Game (1)");
        System.out.println("2. Race Path Manage (2)");
        System.out.println("3. Top 10 Player (3)");
        System.out.println("4. Exit (-1)");
        System.out.print("Please select your choice:");
        while(!sc.hasNextInt()){ //if player did not enter the correct format of input
            System.out.println("Invalid Input");
            System.out.print("Please enter 1/2/3/-1 only >>");
            sc.next();
        }
        int choice = sc.nextInt();
        sc.nextLine();
        
        return choice;
        }
     
    public static void startGame(){
        System.out.println("Games Rule");
        System.out.println("-------------");
        System.out.println("1. You have 5 life");
        System.out.println("2. You must answer one question correct to proceed to roll dice.");
        System.out.println("3. Every wrong answer will cost you 1 life.");
        System.out.println("Start Game!!");
        System.out.println("*****************************");
        System.out.println();
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine();  //get player name
        Player player = new Player(playerName); //create  new player with player name
        System.out.println("===================================");
        
        int life =5;  //players initially got 5 life
        boolean proceed = true;  
        boolean matched = true;
     
        while(proceed){
            String reply; 
            String currentStation = path.getCurrentStationName(); //get player's current location in path
              
            for(int i = 0; i< 7; i++){
                if(station[i].getStationName().compareToIgnoreCase(currentStation)==0){ //compare current station name with station name to get question
                    do{ 
                        if(!matched) //if player's answer is not correct
                            life--;
                        if(life<=0){ //if player's life <= 0
                            gameOver();
                            proceed=false;
                            break;
                        }
                        int randomNum = randomQues(); //get random number to print question
                        System.out.println("You now have "+ life + " life");
                        station[i].printQues(randomNum); //get question based on random num
                        reply = sc.nextLine();
                        matched = station[i].checkAns(randomNum, reply); //check whether player got the correct answer
                    }while(!matched);
                    break;
                }
            }
             
            if(life >=1 && !path.checkWin()){ // if life >0 && player haven't win only continue
                System.out.println("You now have "+ life +" life.");
                proceed = getPermission(); //ask whether player want to continue game or not
            }
            if(proceed==true && !path.checkWin()){ //if player want to proceed and haven't reach finishing line
                int diceValue = rollDice();
                path.movePosition(diceValue); //move current position in path based on diceValue
            }
            else{
                proceed = false;
            }
        }
            
        if(!proceed && !path.checkWin() && life >=1){ //if the player no die only print
            System.out.println("Exiting game..");
            resetPath(); //reset the path and initialise default station to path
        }
         
        if(path.checkWin()&&life>=1){
            System.out.println("Congratulations!! You Won!!! ");
            player.calculateResult(); //get time duration 
            System.out.println("You used "+ player.getResult() + " seconds to finish the game!!.");
            topList.addPlayerToList(player); //add player to topPlayerList
            topPlayer(); //show current top 10 player list
            printPlayerTextFile();  
            printPlayerPathTextFile(playerPathTextFormat(playerName));
            path = new RacePath<Station>();
            resetPath();
        }
    }    
    
    //get player permission to continue game
    public static boolean getPermission(){
        System.out.print("Enter positive number to roll a dice (-1 to exit game) >>");
        while(!sc.hasNextInt()){ //if player did not enter the correct format of input
            System.out.println("Invalid Input");
            System.out.print("Enter positive number to roll a dice (-1 to exit game) >>");
            sc.next();
        }
        int val =  sc.nextInt();
        sc.nextLine();
        if (val>=0)
            return true;
        else 
            return false;
    }
   
    //get random dice value
    public static int rollDice(){
        Random random = new Random();
        int dice =0;
        while(dice == 0){ //if dice value is 0, then throw dice again
            dice = random.nextInt(5) -2; //get value [0,5] and then subtract by 2 to get [-2,3]
        }
        System.out.println("Your dice number is >>> " + dice);
        return dice;
    }
    
    public static int randomQues(){
        Random random = new Random();
        int randomValue = random.nextInt(4); //get random value of [0-4]
        return randomValue;
    }
    
    public static void managePath(){
        int choice =0;
        while(choice != 3){ // show menu as long as choice != 3 (back to main menu)
            currentStation(path);
            System.out.println("=================================");
            System.out.println("1. Add Station (1)");
            System.out.println("2. Delete Station (2)");
            System.out.println("3. Back (3)");
            System.out.print("Please select your choice (1/2/3) :");
            while(!sc.hasNextInt()){ //if player did not enter the correct format of input
            System.out.println("Invalid Input");
            System.out.print("Please select your choice (1/2/3) :");
            sc.next();
            System.out.println();
        }
            choice = sc.nextInt();
            sc.nextLine();
            if (choice ==1 ){ //add station to path at tail of node
                System.out.println("Add Station");
                System.out.println("---------------");
                System.out.print("Enter Station (A-G : enter one) :");
                while(!sc.hasNext("[abcdefg]")&&!sc.hasNext("[ABCDEFG]")){ //check format of user input
                    System.out.println("Invalid station name");
                    System.out.print("Enter Station (A-G : enter one) :");
                    sc.next();
                }
                String stationName = "Station "+ sc.nextLine().toUpperCase();
                if(!path.isExist(stationName)){ // check whether station already exist in path
                    switch(stationName){ //find station to added into path by switching stationName
                        case "Station A":
                            path.addStation(station[0]);
                            break;
                        case "Station B":
                            path.addStation(station[1]);
                            break;
                        case "Station C":
                            path.addStation(station[2]);
                            break;
                        case "Station D":
                            path.addStation(station[3]);
                            break;
                        case "Station E":
                            path.addStation(station[4]);
                            break;
                        case "Station F":
                            path.addStation(station[5]);
                            break;
                        case "Station G":
                            path.addStation(station[6]);
                            break;
                        default:
                            System.out.println("Station adding failed..\n");
                    }
                }
                else{ // if station already exist in the path.
                    System.out.println("Station is exits..");
                    System.out.println("Station adding failed..\n");
                }
                System.out.println("Total Stations in path :" + path.getNumOfStations());
            }
            else if(choice ==2 ){ // delete station from path
                System.out.println("Delete Station");
                System.out.println("---------------");
                System.out.print("Enter Station to delete(A-G, enter one) >>  ");
                while(!sc.hasNext("[ABCDEFG]")&&!sc.hasNext("[abcdefg]")){ //check format of user input
                    System.out.println("Invalid station name");
                    System.out.print("Enter Station (A-G : enter one) :");
                    sc.next();
                }
                String stationDelete = "Station "+sc.next().toUpperCase();
                boolean found= false;
                Station temp= station[0];
                for(int i=0;i<7;i++){
                    if(path.findStation(i).compareToIgnoreCase("") == 0){ //if station is null in path 
                        break;
                    }
                    if(stationDelete.compareToIgnoreCase(path.findStation(i))==0){ // match the stationDelete in path by path.findStation()
                        // find the station to be deleted from race path
                        for(int k=0;k<7;k++){
                            if(station[k].getStationName().compareTo(path.findStation(i))==0){ 
                                temp = station[k];
                                break;
                            }
                        }
                        found=true;
                        path.removeStation(temp); //pass in the found station to be deleted into removeStation.
                        System.out.println( stationDelete + " is succeccfully deleted from Race Path");
                        break;
                    }
                }
                if(!found){ //if station to be deleted is not found in current path
                    System.out.println("Station not exits..");
                    System.out.println("Station deleting failed..\n");
                }
                System.out.println("Total Stations in path :" + path.getNumOfStations());
                System.out.println();
            }
            else if(choice ==3){ //back to main menu
                break;
            }
            else{ //invalid input
                System.out.println("Invalid Input..");
            }
        }   //end if-else statement
    }//end managePath()
    
    //show current station exist in path
    public static void currentStation(RacePath path){
        System.out.println("Current Stations in Race Path :");
        System.out.println("=================================");
        System.out.println("******STARTING POINT******");
        System.out.println(path.toString()); 
        System.out.println("******FINISHING LINE******");
    }
    
    //show current top player list
    public static void topPlayer(){
        System.out.println("\n=====================================");
        System.out.println("Top 10 Player");
        System.out.println("=====================================");
        System.out.println("No. \tPlayer Name\tTime Used(s)");
        System.out.println("--------------------------------------");
        System.out.println(topList.displayRanking());
        System.out.println();
    }
    
    //end game/exit program
    public static void endGame(){
        System.out.println("============================");
        System.out.println("Thanks for playing!!");
        System.out.println("See you again!!");
        System.out.println("This game is brought to you by Cooper Games INC.");
        System.out.println("Developed by FoongLY, LuZY and HoSY");
    }
    
    // when player's life ==0
    public static void gameOver(){
        System.out.println("You have 0 life left..");
        System.out.println("Game Over!!");
        System.out.println("Try again next time. ^o^");
        resetPath();
    }
    
    // formatting string to print information into "Player Path.txt"
    public static String playerPathTextFormat(String playerName){
        String str = "";
        str += "\nPlayer Name: " + playerName + "\n" + "Path: \n" + path.toString()+"\n";
        return str;
    }
    
     public static void printPlayerTextFile(){
         String formatText = topList.displayRanking().replaceAll("\n", System.lineSeparator());
         try{
             FileWriter writer = new FileWriter(new File("Top Player.txt"),false);
             BufferedWriter bw = new BufferedWriter(writer);
             bw.write("Top 10 Player");
             bw.newLine();
             bw.write("=====================================");
             bw.newLine();
             bw.write("No. \tPlayer Name\tTime Used(s)");
             bw.newLine();
             bw.write("--------------------------------------");
             bw.newLine();
             bw.write(formatText);
             bw.close();
             writer.close();
         }
         catch(IOException e){
                System.out.println("Error in printing to text file..");
         }
     }
    
    public static void printPlayerPathTextFile(String text){
        String formatText = text.replaceAll("\n", System.lineSeparator());
        try{
            if(!firstTimePrintPlayer){ //if is first time printing to text file, create a file with header 
                FileWriter writer = new FileWriter(new File("Player Path.txt"),false);
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write("Player's Path");
                bw.newLine();
                bw.write("======================================");
                bw.close();
                writer.close();
            }
            //append player's path data to "Player Path.txt" 
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Player Path.txt", true)));
             out.println(formatText); 
             out.close();
             firstTimePrintPlayer=true;
        }
        catch (IOException e){
            System.out.println("Error in printing to text file..");
        }  
    }
    
    public static void printQuestionTextFile(){
         try{
             FileWriter writer = new FileWriter(new File("Questions.txt"),false);
             BufferedWriter bw = new BufferedWriter(writer);
             bw.write("Questions in Stations ");
             bw.newLine();
             bw.write("=======================");
             bw.newLine();
             bw.write("Station A Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){ //there are all 5 questions, so loop 5 times
                bw.write((i+1)+".  "+quesA[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station B Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesB[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station C Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesC[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station D Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesD[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station E Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesE[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station F Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesF[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station G Questions");
             bw.newLine();
             bw.write("---------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+quesG[i].toString());
                bw.newLine();
             }
             bw.close();
             writer.close();
         }
         catch(IOException e){
                System.out.println("Error in printing to text file..");
         }
     }
    public static void printAnswerTextFile(){
        try{
             FileWriter writer = new FileWriter(new File("Answers.txt"),false);
             BufferedWriter bw = new BufferedWriter(writer);
             bw.write("Answers for Questions in Stations ");
             bw.newLine();
             bw.write("====================================");
             bw.newLine();
             bw.write("Station A Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansA[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station B Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansB[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station C Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansC[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station D Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansD[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station E Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansE[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station F Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansF[i].toString());
                bw.newLine();
             }
             bw.newLine();
             bw.write("Station F Question's Answers:");
             bw.newLine();
             bw.write("------------------------------");
             bw.newLine();
             for(int i = 0;i<=4;i++){
                bw.write((i+1)+".  "+ansG[i].toString());
                bw.newLine();
             }
             bw.close();
             writer.close();
         }
         catch(IOException e){
                System.out.println("Error in printing to text file..");
         }
    }
}



