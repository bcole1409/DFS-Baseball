package DataStructures;

public class FanGraphsHTMLConverter {
    String bullpen;

    public FanGraphsHTMLConverter(String BullpenData){
        this.bullpen = BullpenData;
        bullpen = stringRemover(bullpen);
        convertHelper(bullpen);
    }

    private String stringRemover(String s){ //removes unecessary characters
        int spaceCounter = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 32){ //spaceCharacter
                spaceCounter++;
                if(spaceCounter == 48){
                    return s.substring(i + 1);
                }
            }
        }
        return s; //fails - should never reach
    }

    //20 spaces == new team
    private void convertHelper(String s){
        int spaceCounter = 0;
        int spaceCounterPrev;
        int spaceCounterCurrent = 0;
        int index = 0; //used to store team at correct index
        String stringHolder = ""; //used to store data that will be used

        //used to store in stats
        float IP = 0; float k; float BB; float HR; float BABIP; float LOB; float GB; float vFA;
        float ERA; float FIP; float xFIP; float WAR;

        //booleans used for logic testing
        boolean teamFound = false;

        System.out.println(s);

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 32){ //check for space character
                spaceCounter++;
                spaceCounterPrev = spaceCounterCurrent; //set previous space index
                spaceCounterCurrent = i; //set currentIndex

                if(spaceCounter == 2){ //team abbreviation
                    stringHolder = s.substring(spaceCounterPrev + 1,spaceCounterCurrent);

                    for(int j = 0; j < 30; j++){
                        if(TeamHashTable.teamIndex.get(j).equals(stringHolder)){
                            teamFound = true;
                            index = j;
                            break;
                        }
                    }

                    if(!teamFound){ //team not playing today
                        System.out.println("Team not found");
                    }

                    /*
                    for(String t : TeamHashTable.teamIndex.values()){ //check to see if team exists
                        if(t.equals(stringHolder)){
                            teamFound = true;
                            break;
                        }

                        index++; //helps find correct index
                    }
                     */
                }

                if(spaceCounter == 8){ //IP
                    IP = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                }

                //SPECIAL CASE
                if(i == s.length()-5){ //end of sequence -> space logic wont work
                    System.out.println("Attempting to add: " + stringHolder);
                    Bullpen newBullpen = addToBullpen(stringHolder,IP,1,1,1,1,1,1,1,1,1,1,1);
                    Bullpens.add(index, newBullpen);
                    break;
                }

                if(spaceCounter == 20){ //start of newTeam
                    spaceCounter = 0;
                    if(!teamFound){
                        System.out.println(stringHolder);
                    }

                    else{
                        System.out.println("Attempting to add: " + stringHolder);
                        Bullpen newBullpen = addToBullpen(stringHolder,IP,1,1,1,1,1,1,1,1,1,1,1);
                        Bullpens.add(index, newBullpen);
                        teamFound = false;
                    }
                }
            }
        }
    }

    //IP, stikeouts, walks, HRs, BABIP, LOB%, GB%, HR/FB, vFA, ERA, xERA, FIP, xFIP, WAR
    private float parser(String s){
        return Float.parseFloat(s);
    }

    private Bullpen addToBullpen(String team, float IP, float k, float BB,
                                 float HR, float BABIP, float LOB, float GB, float vFA,
                                    float ERA, float FIP, float xFIP, float WAR){
        Bullpen bullpen = new Bullpen();

        //add data to fields
        bullpen.team = team; bullpen.stats[0] = IP; bullpen.stats[1] = k; bullpen.stats[2] = BB;
        bullpen.stats[3] = HR; bullpen.stats[4] = BABIP; bullpen.stats[5] = LOB; bullpen.stats[6] = GB;
        bullpen.stats[7] = vFA; bullpen.stats[8] = ERA; bullpen.stats[9] = FIP; bullpen.stats[10] = xFIP;
        bullpen.stats[11] = WAR;

        return bullpen;
    }
}
