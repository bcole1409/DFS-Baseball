package DataStructures;

import java.util.ArrayList;
import java.util.Hashtable;

public class StringConverter {
    Hashtable<String, Player> table = new Hashtable<String, Player>();
    ArrayList<Player> C = new ArrayList<Player>();
    ArrayList<Player> B1 = new ArrayList<Player>();
    ArrayList<Player> B2 = new ArrayList<Player>();
    ArrayList<Player> B3 = new ArrayList<Player>();
    ArrayList<Player> BSS = new ArrayList<Player>();
    ArrayList<Player> OF = new ArrayList<Player>();
    ArrayList<Player> SP = new ArrayList<Player>();
    ArrayList<Player> M = new ArrayList<Player>();

    static class Player{
        int lineup;
        String Name;
        int Salary;
        String plateSide;

        public Player(int tempL, String tempN, int tempS, String tempP){
            this.lineup = tempL;
            this.Name = tempN;
            this.Salary = tempS;
            this.plateSide = tempP;
        }
    }

    public StringConverter(ArrayList players){
        System.out.println("Converting HTML to Arrays of Players");
        for (Object team : players) {
            //System.out.println(team);
            convertHelper((String)team);
        }

        printList();
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
                Player newPlayer = new Player(battingOrder, NameTEMP, SalaryTEMP, plateSideTEMP); //make empty player, will update fields through iteration of String
                //reset booleans and batting order for newTeam
                cSpaces = 0;
                batting = false;
                nameFinder = false;
                pos = false;
                stName = false;
                side = false;
                batOrder +=1;

                //PUT PLAYER IN APPROPRIATE ARRAY
                playerArranger(position, newPlayer);

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

    private void playerArranger(String position, Player player){
        switch (position) {
            case "C" -> C.add(player);
            case "1B" -> table.put(position, player);
            case "2B" -> B2.add(player);
            case "SS" -> BSS.add(player);
            case "3B" -> B3.add(player);
            case "OF" -> OF.add(player);
            case "SP", "RP" -> SP.add(player);
            default -> M.add(player);
        }
    }

    private void printList(){
        System.out.println();
        System.out.println("List of Catchers: ");
        for(Player p : C){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of 1B: ");
        for(Player p : B1){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of 2B: ");
        for(Player p : B2){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of SHORT STOPS: ");
        for(Player p : BSS){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of 3B: ");
        for(Player p : B3){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of OUT FIELDERS: ");
        for(Player p : OF){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of Starting Pitchers: ");
        for(Player p : SP){
            System.out.print(p.Name + " ");
        }

        System.out.println();
        System.out.println("List of Multiple Positions: ");
        for(Player p : M){
            System.out.print(p.Name + " ");
        }
    }
}
