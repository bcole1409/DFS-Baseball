package DataStructures;

import DataStructures.PlayerTypes.Player;

import java.util.ArrayList;

public class RotoHTMLConverter{
    static class PlayerData{ //struct that will be passed to LineupConstructor
        int lineup; //batting order spot
        String Name; //playerName
        int Salary; //$k
        String plateSide; //L/R/S

        public PlayerData(int tempL, String tempN, int tempS, String tempP){
            this.lineup = tempL;
            this.Name = tempN;
            this.Salary = tempS;
            this.plateSide = tempP;
        }
    }

    public RotoHTMLConverter(ArrayList<String> players){
        System.out.println("Converting HTML to Arrays of Players");
        for (Object team : players) {
            //System.out.println(team);
            convertHelper((String)team);
        }
    }

    private void convertHelper(String team){
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
                battingOrder = team.charAt(i); //ascii value
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

            if(team.charAt(i) == '%'){
                //adds new player to lineup generator
                Player newPlayer;

                if(position.equals("SP") || position.equals("RP")){
                    newPlayer = LineupFactory.getPitcherPlayer(NameTEMP, team, position, SalaryTEMP);
                    System.out.println(newPlayer.Name);
                }
                else{
                    newPlayer = LineupFactory.getPositionPlayer(NameTEMP, team, position, SalaryTEMP);
                    System.out.println(newPlayer.Name);
                }

                //reset booleans and batting order for newTeam
                cSpaces = 0;
                batting = false;
                nameFinder = false;
                pos = false;
                stName = false;
                side = false;
                batOrder +=1;



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
}
