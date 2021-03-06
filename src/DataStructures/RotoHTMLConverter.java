package DataStructures;

import DataStructures.PlayerTypes.PitcherPlayer;
import DataStructures.PlayerTypes.Player;
import Reader.BaseballReferenceDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RotoHTMLConverter{
    private int tIndex = 0; //used to help store players in correct index
    private int pIndex = 0;

    public RotoHTMLConverter(ArrayList<String> players, ArrayList<String> teams, ArrayList<String> pitchers) throws IOException, InterruptedException {
        System.out.print("\nStoring Lineup Data in Lineups");

        for(int i = 0; i < 4; i++){
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
        }

        System.out.println(); //Print for Formatting

        for(String city : teams){
            convertTeams(city); //function reads html and insert index in hash table
        }

        for (String team : players) {
            convertHelper(team); //function reads html and converts String to PositionPlayer
        }

        for(String pitcher : pitchers){
            convertPitcher(pitcher); //function reads html and converts String to PitcherPlayer
        }
    }

    /*
    Goal of this function is to parse String Data into raw data:
        PlayerName  Salary  PlateSide   Position    BattingOrder
     */
    private void convertHelper(String team) throws IOException {
        String NameTEMP = "";
        int SalaryTEMP = 0;
        String plateSideTEMP = "";
        String position = "";
        int battingOrder = 0; //used for lBool algo

        //BOOLEANS USED TO EFFICIENTLY PARSE STRING
        boolean newTeam = false;
        boolean batting = false;
        boolean nameFinder = false;
        boolean pos = false;
        boolean stName = false;
        boolean side = false;

        //USED FOR NAMEFINDER/BATTING ORDER
        int sIndex = 0; //start name
        int eIndex = 0; //end name
        int cSpaces = 0; //tells name finder when to stop
        int batOrder = 1;

        for(int i = 0; i < team.length(); i++) {
            //LOOK FOP BATTING ORDER NUMBER PER PLAYER
            //remove "lineup not released Projected Lineup string

            if(!newTeam){
                newTeam = true;
                if(team.charAt(i) == 'L'){
                    i += 36;
                    continue;
                }
            }

            if(!batting){
                battingOrder = team.charAt(i) - 48; //ascii value convert by subtracting 48
                batOrder++;
                batting = true;
                i++; //skip next space
                continue;
            }

            //find first name
            if(!nameFinder){ //IF NAME NOT ESTABLISHED
                if(team.charAt(i) >= 65 && team.charAt(i) <= 90){
                    if(!stName){ //FOUND FIRST NAME
                        sIndex = i; //SET STARTING INDEX
                        stName = true; //FOUND FIRST NAME FIRST LETTER
                        continue;
                    }
                }

                else if(team.charAt(i) == ' ') { //32 ASCII
                    cSpaces += 1;
                    if(cSpaces == 2){
                        eIndex = i;
                        NameTEMP = team.substring(sIndex,eIndex);
                        nameFinder = true;
                        continue;
                    }
                    continue;
                }
                continue;
            }

            //FIND POSITION OF PLAYER
            if(!pos){
                sIndex = eIndex + 1;

                if(team.charAt(i) == ' '){
                    eIndex = i;
                    position = team.substring(sIndex, eIndex); //NOTE: USED LATER
                    pos = true;
                    continue;
                }
                continue;
            }


            if(!side){
                plateSideTEMP = team.substring(i,i+1);
                i++;
                side = true;
                continue;
            }

            //FIND SALARY OF PLAYER
            if(team.charAt(i) == 36){ //ASCII VALUE 36 = $
                SalaryTEMP = playerSalary(team, i);
                continue;
            }

            //algo done for each player will create new player object -> needs to pass boolean test

            if(team.charAt(i) == '%' || (team.charAt(i) == '0' && team.charAt(i-2) == 'K')){
                //adds new player to lineup generator
                Player newPlayer;

                if(position.equals("SP") || position.equals("RP")){
                    newPlayer = LineupFactory.getPitcherPlayer(TeamHashTable.teamIndex.get(tIndex), battingOrder, NameTEMP, team, position, SalaryTEMP, plateSideTEMP);
                }

                else{
                    if(SalaryTEMP > 10000) SalaryTEMP -= 10000;
                    newPlayer = LineupFactory.getPositionPlayer(TeamHashTable.teamIndex.get(tIndex), battingOrder, NameTEMP, team, position, SalaryTEMP, plateSideTEMP);
                }

                new BaseballReferenceDownloader(newPlayer); //add statistics to array of stats
                newPlayer.run(); //adds player to lineup list

                if(batOrder == 10){
                    batOrder = 1;
                    tIndex++; //Means we are onto the next team
                }

                //reset booleans
                cSpaces = 0;
                batting = false;
                nameFinder = false;
                pos = false;
                stName = false;
                side = false;


                i++; //INCREMENT TO NEXT PLAYER
            }
        }
    }

    private int playerSalary(String team, int startingIndex){
        int endingIndex = 0; //used to find K -> end of salary string
        float temp;
        int result; //store result from algo

        for(int i = startingIndex + 1; i < team.length(); i++){
            //System.out.println(team.charAt(i));
            if(team.charAt(i) == 75){
                endingIndex = i;
                break;
            }
            else{
                endingIndex = startingIndex + i;
            }
        }

        temp = Float.parseFloat(team.substring(startingIndex + 1,endingIndex))*1000; //need temp float or lose of precision
        result = (int)temp; //cast back to int -> then store as Salary in Player
        return result;
    }

    private void convertTeams(String line){
        int teamCount = 0;
        String currentTeam;

        for(int i = 0; i < line.length(); i++) {
            if (line.charAt(i) >= 65 && line.charAt(i) <= 90) {
                if (line.charAt(i+1) >= 65 && line.charAt(i+1) <= 90) {
                    teamCount++;
                    currentTeam = line.substring(i, i + 3); //grab "AAA" city-format
                    TeamHashTable.teamIndex.put(TeamHashTable.Index, currentTeam); //insert dict key,value
                    TeamHashTable.Index++;

                    i = i+10; //speed up city grabbing process
                }
            }

            if (teamCount == 2) {
                return; //found all cities in Line, no need to iterate further
            }
        }
    }


    private void convertPitcher(String line) {
        int counter = 0;
        int salary = 0;
        String arm = "";
        String Name = "";

        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == 32){
                counter++;
                if(counter == 2){
                    Name = line.substring(0,i);
                    continue;
                }

                if(counter == 3){
                    arm = line.substring(i-1,i);
                    if(line.charAt(i+1) == '$') salary = playerSalary(line,i+1);
                    else salary = (int)Float.parseFloat(line.substring(i+1,i+4))*1000;
                    break;
                }
            }
        }

        Player newPlayer = new PitcherPlayer(TeamHashTable.teamIndex.get(pIndex),0,Name,
                TeamHashTable.teamIndex.get(pIndex),arm,salary, "None");

        new BaseballReferenceDownloader(newPlayer); //pass new pitcher to BaseReferenceDownloader Object

        StartingPitchers.add(newPlayer);
        pIndex++;   //insert in new index position
    }
}
