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
    
    static final String[] quesA = {"20 - 1 =", "7 * 7 =","890 * 23 =","72 + 88 =","22 % 2 ="};
    static final String[] quesB={"80 % 6 =", "902 - 872 =",
                                 "1242 - 901 =","7232 - 22=","672 * 90 ="};
    static final String[] quesC = {"log10 / log2 = (round to 2 dp)", "2 ^ 3= ", "3 * 7 = ","Any number can divide by 0? [Can(1)/Cannot(0)]","3 ^ 3 = "};
    static final String[] quesD = {"5 + |-11| + 11 = ", "-8 + 5(2-4) = ", "48 / (-4)^2 + (-9) = ", "-15 -(-7) = ","7+(-17)+4 = "};
    static final String[] quesE = {"2w + w = ", "3w + 4(2+w) = ", "5(w+1) +2 = ", "6w + 18w = ", "2w + 3(5w+2) = "};
    static final String[] quesF = {"3(v+w) = ", "2(2v-3w) = ", "(8v + 8w)/(2v + 2w) = ","2(5v + 3w) = ","2(6v) + w = "};
    static final String[] quesG = {"50 * 0.2 = ", "4 * 80 = ", "9 * 9 = ", "4 * 4 * 4 = ", "3 * 8 * 2 = "};
    
    static final String[] ansA = {"19", "49","20470","160","0"};
    static final String[] ansB = {"2", "30", "341", "7210","60480"};
    static final String[] ansC = {"3.32","8","21","0","27"};
    static final String[] ansD = {"27","-18","-6","-8","-6"};
    static final String[] ansE = {"3w","7w+8","5w + 7","24w","17w+6"};
    static final String[] ansF = {"3v+3w","4v-6w","4","10v+6w","12v+w"};
    static final String[] ansG = {"10","320","81","64","48"};
    
    static final Station[] station ={new Station("Station A", quesA, ansA),
                                     new Station("Station B", quesB, ansB),
                                     new Station("Station C", quesC, ansC),
                                     new Station("Station D", quesD, ansD),
                                     new Station("Station E", quesE, ansE),
                                     new Station("Station F", quesF, ansF),
                                     new Station("Station G", quesG, ansG),};
   
    static RacePath<Station> path;
    static topPlayerListInterface<Player> topList = new topPlayerList<Player>();
    static boolean firstTimePrintPlayer = false;
    
    public static void main(String[] args) {
        printQuestionTextFile();
        printAnswerTextFile();
        //mainMenu();
    }
    
    public static void mainMenu(){
        resetPath();
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
            else{
                System.out.println("Invalid Input!!");//reenter input
            }
        }while(choice!=-1);
        endGame(); //if the choice is -1
    }
     // to reset the race path after every game. 
    public static void resetPath(){ 
        path = new RacePath<Station>();
        initializePath();
    }
    
     //Initial the station path
    public static void initializePath(){
        path.addStation(station[0]);
        path.addStation(station[1]);
        path.addStation(station[2]);
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
        System.out.print("Please select your choice:");
        int choice = sc.nextInt();
        sc.nextLine();
        
        return choice;
        }
     
    public static void startGame(){
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine(); 
        Player player = new Player(playerName);
        System.out.println("===================================");
        
        int life =5;  
        boolean proceed = true;
        boolean matched = true;
     
        while(proceed){
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
                        System.out.println("You now have "+ life + " life");
                        station[i].printQues(randomNum);
                        reply = sc.nextLine();
                        matched = station[i].checkAns(randomNum, reply);
                    }while(!matched);
                    break;
                }
            }
             
            if(life >=1 && !path.checkWin()){ // if life >0 only ask permission
                System.out.println("You now have "+ life +" life.");
                proceed = getPermission();
            }
            if(proceed==true && !path.checkWin()){
                int diceValue = rollDice();
                path.movePosition(diceValue);
            }
            else{
                proceed = false;
            }
        }
            
        if(!proceed && !path.checkWin() && life >=1){ //if the player no die only print
            System.out.println("Exiting game..");
            resetPath();
        }
         
        if(path.checkWin()&&life>=1){
            System.out.println("Congratulations!! You Won!!! ");
            player.calculateResult();
            System.out.println("You used "+ player.getResult() + " seconds to finish the game!!.");
            topList.addPlayerToList(player);
            topPlayer();
            printPlayerTextFile();
            printPlayerPathTextFile(playerPathTextFormat(playerName));
            path = new RacePath<Station>();
            resetPath();
        }
    }    
    
    public static boolean getPermission(){
        System.out.print("Enter positive number to roll a dice (-1 to exit game):");
        int val =  sc.nextInt();
        sc.nextLine();
        if (val>0)
            return true;
        else 
            return false;
    }
    
    public static int rollDice(){
        Random random = new Random();
        int dice =0;
        while(dice == 0){
            dice = random.nextInt(5) -2;
        }
        System.out.println("Your dice number is >>> " + dice);
        return dice;
    }
    
    public static int randomQues(){
        Random random = new Random();
        int randomValue = random.nextInt(5);
        return randomValue;
    }
    
    public static void managePath(){
        int choice =0;
        while(choice != 3){ // put this while ... no more managePath(); and choice == 3
            currentStation(path);
            System.out.println("=================================");
            System.out.println("1. Add Station (1)");
            System.out.println("2. Delete Station (2)");
            System.out.println("3. Back (3)");
            System.out.print("Please select your choice:");
            choice = sc.nextInt();
            sc.nextLine();
            if (choice ==1 ){
                System.out.print("Enter Station (A-G : enter one) :");
                String stationName = "Station "+ sc.nextLine().toUpperCase();
               
                if(!path.isExits(stationName)){ // check whether station already exist in path
                    switch(stationName){
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
            }
            else if(choice ==2 ){ // delete station from path
                System.out.print("Enter Station to delete(A-G, enter one) >>  ");
                String stationDelete = "Station "+sc.next().toUpperCase();
                boolean found= false;
                Station temp= station[0];
                for(int i=0;i<7;i++){
                    if(path.findStation(i).compareToIgnoreCase("") == 0){
                        break;
                    }
                    if(stationDelete.compareToIgnoreCase(path.findStation(i))==0){ // match the stationDelete in path
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
                if(!found){
                    System.out.println("Station not exits..");
                    System.out.println("Station deleting failed..\n");
                }

            }
            else if(choice ==3){
                break;
            }
            else{
                System.out.println("Invalid Input..");
            }
        }   //end if-else statement
    }//end managePath()
    
    public static void currentStation(RacePath path){
        System.out.println("Current Stations in Race Path :");
        System.out.println("=================================");
        System.out.println("******STARTING POINT******");
        System.out.println(path.toString()); 
        System.out.println("******FINISHING LINE******");
    }
    
    public static void topPlayer(){
        System.out.println("\n=====================================");
        System.out.println("Top 10 Player");
        System.out.println("=====================================");
        System.out.println("No. \tPlayer Name\tTime Used(s)");
        System.out.println("--------------------------------------");
        System.out.println(topList.displayRanking());
        System.out.println();
    }
    
    public static void endGame(){
        System.out.println("============================");
        System.out.println("Thanks for playing!!");
        System.out.println("See you again!!");
        System.out.println("This game is brought to you by Cooper Games INC.");
        System.out.println("Developed by FoongLY, LuZY and HoSY");
    }
    
    public static void gameOver(){
        System.out.println("You have 0 life left..");
        System.out.println("Game Over!!");
        System.out.println("Try again next time. ^o^");
        resetPath();
    }
    
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
             for(int i = 0;i<=4;i++){
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



