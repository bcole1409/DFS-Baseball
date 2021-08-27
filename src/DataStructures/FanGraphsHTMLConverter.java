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
        float IP = 0; float k = 0; float BB = 0; float HR = 0; float BABIP = 0; float LOB = 0; float GB = 0; float vFA = 0;
        float ERA = 0; float FIP = 0; float xFIP = 0; float WAR = 0;

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
                    if(stringHolder.equals("WSN")) stringHolder = "WAS";

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
                }

                if(spaceCounter == 8){ //IP
                    IP = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 9){ //K
                    k = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 10){ //BB
                    BB = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 11){ //HR
                    HR = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 12){ //BABIP
                    BABIP = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 13){  //LOB
                    LOB = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 14){ //GB
                    GB = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 15){ //vFA
                    vFA = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 16){ //ERA
                    ERA = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 17){ //FIP
                    FIP = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 18){ //xFIP
                    xFIP = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                    continue;
                }

                if(spaceCounter == 19){ //WAR
                    WAR = parser(s.substring(spaceCounterPrev,spaceCounterCurrent));
                }


                if(spaceCounter == 20){ //start of newTeam
                    spaceCounter = 0;
                    if(!teamFound){
                        System.out.println(stringHolder);
                    }

                    else{
                        System.out.println("Attempting to add: " + stringHolder);
                        Bullpen newBullpen = addToBullpen(stringHolder,IP,k,BB,HR,BABIP,LOB,GB,vFA,ERA,FIP,xFIP,WAR);
                        Bullpens.add(index, newBullpen);
                        teamFound = false;
                    }
                }

                //SPECIAL CASE - Final Team
                if(i == s.length()-5){ //end of sequence -> space logic wont work
                    System.out.println("Attempting to add: " + stringHolder);
                    Bullpen newBullpen = addToBullpen(stringHolder,IP,k,BB,HR,BABIP,LOB,GB,vFA,ERA,FIP,xFIP,WAR);
                    Bullpens.add(index, newBullpen);
                    break;
                }
            }
        }
    }

    //IP, stikeouts, walks, HRs, BABIP, LOB%, GB%, HR/FB, vFA, ERA, xERA, FIP, xFIP, WAR
    private float parser(String s){
        if(s.charAt(s.length()-1) == '%'){ //convert % to float number
            return Float.parseFloat(s.substring(0,s.length()-1))/100;
        }

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
