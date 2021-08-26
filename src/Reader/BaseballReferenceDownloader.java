package Reader;

import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import DataStructures.URLHandler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BaseballReferenceDownloader implements URLHandler {
    //PARSER VARIABLES
    Player player;

    //URL VARIABLES
    StringBuilder URL = new StringBuilder();
    final String URLbase = "https://www.baseball-reference.com/players/";
    final List<String> URLend = Arrays.asList("01","02","03","04","05","06");
    final String URLBattingStandardEnd = "-bat.shtml#all_batting_advanced";
    final String URLPitchingStandardEnd = ".shtml";
    final List<String> forbidden = Arrays.asList("Giancarlo Stanton", "Travis d'Arnaud", "Jose Barrero", "Chi Chi", "Tommy La", "Bryan De", "Tyler O'Neill", "Yulieski Gurriel", "Ryan O'Hearn", "Hoy Jun");
    final List<String> real = Arrays.asList("Mike Stanton", "Travis Darnaud", "Jose Garcia", "Chi Gonzalez", "Tommy LaStella", "Bryan DeLaCruz", "Tyler Oneill", "Yuli Gourriel", "Ryan Ohearn", "Hoy Park");

    //used to sort data
    private Document doc; //stores html

    public BaseballReferenceDownloader(Player p) {
        this.player = p;
        run();
    }

    private void run() {
        connect();
    }

    public void connect() {
        try{
            //test for forbidden names -> change name to correct format
            if(forbidden.contains(player.Name)){
                //Testing Purposes
                //System.out.println("TRUE");
                //System.out.println(real.get(forbidden.indexOf(player.Name)));

                player.Name = real.get(forbidden.indexOf(player.Name));
            }

            String fName = firstName(player.Name).toLowerCase();
            String lName = Objects.requireNonNull(lastName(player.Name)).toLowerCase();
            //String lName = lastName(player.Name).toLowerCase();

            int index = 0;

            //help find correct player
            String d = "Died:";
            String t = "Team:";
            int i = 0;
            boolean isFound = false;

            String currentWord;

            while(!isFound){
                if(player instanceof PositionPlayer){
                    URL.append(URLbase).append(lName.charAt(0)).append("/").append(lName).append(fName).append(URLend.get(index)).append(URLBattingStandardEnd);
                    doc = Jsoup.connect(URL.toString()).get();

                    //Elements team = doc.select("a[href]");
                    Elements team = doc.select("p");
                    for(Element e : team){
                        currentWord = e.text().substring(0,5);

                        if(currentWord.equals(d)){
                            URL.delete(0,URL.length());
                            i = 0; //reset
                            index++; //find new URL
                            break; //try new URL
                        }

                        if(currentWord.equals(t)){
                            isFound = true;
                            break;
                        }

                        i++;

                        if(i == 7){
                            URL.delete(0,URL.length());
                            i = 0; //reset
                            index++; //find new URL
                            break; //try new URL
                        }
                    }
                }

                else{
                    URL.append(URLbase).append(lName.charAt(0)).append("/").append(lName).append(fName).append(URLend.get(index)).append(URLPitchingStandardEnd);
                    doc = Jsoup.connect(URL.toString()).get();
                    Elements team = doc.select("p");
                    for(Element e : team){
                        currentWord = e.text().substring(0,5);

                        if(currentWord.equals(d)){
                            URL.delete(0,URL.length());
                            i = 0; //reset
                            index++; //find new URL
                            break; //try new URL
                        }

                        if(currentWord.equals(t)){
                            isFound = true;
                            break;
                        }

                        i++;

                        if(i == 7){
                            URL.delete(0,URL.length());
                            i = 0; //reset
                            index++; //find new URL
                            break; //try new URL
                        }
                    }
                }
            }

            parse();
        }
        catch (Exception e)
        {
            //System.out.println("Unable to find player");
        }
    }

    public void parse(){
        if(player instanceof PositionPlayer){
            Elements battingStandard = doc.select("tfoot");
            playerStandardBatting(battingStandard.text());
        }

        else {
            Elements pitchingStandard = doc.select("tfoot");
            playerStandardPitching(pitchingStandard.text());
        }
    }

    private String firstName(String s){
        if(s.charAt(1) == 46){
            return s.substring(0,1).toLowerCase() + s.charAt(2);
        }

        return s.substring(0,1).toLowerCase() + s.charAt(1);
    }

    private String lastName(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 32){
                if(s.length() - i > 5){
                    return (char)(s.charAt(i+1) + 32) + s.substring(i+2,i + 6);
                }
                return s.substring(i + 1);
            }
        }

        return null; //name not found
    }

    private void playerStandardBatting(String e){
        int spaceCounter = 0;
        int spaceCounterPrev;
        int spaceCounterCurrent = 0;

        //data variable
        final double careerWeight = 0.40;
        final double yearWeight = 0.60;
        double parser;

        for(int i = 0; i < e.length(); i++) {
            if(e.charAt(i) == 32) {
                spaceCounterPrev = spaceCounterCurrent + 1; //set previous space index
                spaceCounterCurrent = i; //set currentIndex
                spaceCounter++;

                try {
                    parser = convertToDouble(e.substring(spaceCounterPrev,spaceCounterCurrent));
                }

                catch (NumberFormatException ignored){
                    parser = 1;
                }


                if(spaceCounter == 5 || spaceCounter == 30){ //AT BATS
                    if(spaceCounter == 30) player.stats[0] += yearWeight * parser;
                    else player.stats[0] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 6 || spaceCounter == 31){ //RUNS
                    if(spaceCounter == 31) player.stats[1] += yearWeight * parser;
                    else player.stats[1] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 7 || spaceCounter == 32){ //SINGLES
                    if(spaceCounter == 32) player.stats[2] += yearWeight * parser;
                    else player.stats[2] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 8 || spaceCounter == 33){ //DOUBLES
                    if(spaceCounter == 33) {
                        player.stats[3] += yearWeight * parser;
                        player.stats[3] -= yearWeight * player.stats[3]; //TO GET ACCURATE SINGLES
                    }

                    else{
                        player.stats[4] = careerWeight * parser;
                        player.stats[4] -= careerWeight * player.stats[3]; //TO GET ACCURATE SINGLES
                    }
                    continue;
                }

                if(spaceCounter == 9 || spaceCounter == 34){ //TRIPLES
                    if(spaceCounter == 34) {
                        player.stats[5] += yearWeight * parser;
                        player.stats[5] -= yearWeight * player.stats[4]; //TO GET ACCURATE SINGLES
                    }
                    else{
                        player.stats[6] = careerWeight * parser;
                        player.stats[6] -= careerWeight * player.stats[4]; //TO GET ACCURATE SINGLES
                    }
                    continue;
                }

                if(spaceCounter == 10 || spaceCounter == 35){ //HOMERUNS
                    if(spaceCounter == 35) {
                        player.stats[7] = yearWeight * parser;
                        player.stats[7] -= yearWeight * player.stats[5]; //TO GET ACCURATE SINGLES
                    }
                    else{
                        player.stats[8] = careerWeight * parser;
                        player.stats[8] -= careerWeight * player.stats[5]; //TO GET ACCURATE SINGLES
                    }

                    continue;
                }

                if(spaceCounter == 11 || spaceCounter == 36){ //RBI
                    if(spaceCounter == 36) player.stats[9] = yearWeight * parser;
                    else player.stats[9] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 12 || spaceCounter == 37){ //STOLENBASES
                    if(spaceCounter == 37) player.stats[10] += yearWeight * parser;
                    else player.stats[10] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 13 || spaceCounter == 38){ //STEALING
                    if(spaceCounter == 38) player.stats[11] += yearWeight * parser;
                    else player.stats[11] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 14 || spaceCounter == 39){ //WALKS
                    if(spaceCounter == 39) player.stats[12] += yearWeight * parser;
                    else player.stats[12] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 15 || spaceCounter == 40){ //K's
                    if(spaceCounter == 40) player.stats[13] += yearWeight * parser;
                    else player.stats[13] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 17 || spaceCounter == 42){ //OBP
                    if(spaceCounter == 42) player.stats[14] += yearWeight * parser;
                    else player.stats[14] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 18 || spaceCounter == 43){ //SLG
                    if(spaceCounter == 43) player.stats[15] += yearWeight * parser;
                    else player.stats[15] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 19 || spaceCounter == 44){ //OPS
                    if(spaceCounter == 44) player.stats[16] += yearWeight * parser;
                    else player.stats[16] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 20 || spaceCounter == 45){ //OPS+ -> PLAYER ADJUSTED BALLPARKS
                    if(spaceCounter == 45) player.stats[17] += yearWeight * parser;
                    else player.stats[17] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 21 || spaceCounter == 46){ //TOTAL BASES
                    if(spaceCounter == 46) player.stats[18] += yearWeight * parser;
                    else player.stats[18] = careerWeight * parser;
                    continue;
                }

                if(spaceCounter == 23 || spaceCounter == 48){ //HIT BY PITCH
                    if(spaceCounter == 48) {
                        player.stats[19] += yearWeight * parser;
                        break;
                    }
                    else player.stats[19] = careerWeight * parser;
                }
            }
        }
    }

    public void playerStandardPitching(String e){
        int spaceCounter = 0;
        int spaceCounterPrev;
        int spaceCounterCurrent = 0;

        //data variable
        final double careerWeight = 0.40;
        final double yearWeight = 0.60;
        double parser = 0;

        for(int i = 0; i < e.length(); i++){
            if(e.charAt(i) == 32){
                spaceCounter++;
                spaceCounterPrev = spaceCounterCurrent + 1; //set previous space index
                spaceCounterCurrent = i; //set currentIndex

                try {
                    parser = convertToDouble(e.substring(spaceCounterPrev,spaceCounterCurrent));
                }

                catch (NumberFormatException ignored){
                }

                if(spaceCounter == 3 || spaceCounter == 36){ //WINS
                    if(spaceCounter == 3) player.stats[0] = parser * careerWeight;
                    else player.stats[0] += parser * yearWeight;
                }

                if(spaceCounter == 4 || spaceCounter == 37){ //LOSES
                    if(spaceCounter == 4) player.stats[1] = parser * careerWeight;
                    else player.stats[1] += parser * yearWeight;
                }

                if(spaceCounter == 5 || spaceCounter == 38){ //W/L%
                    if(spaceCounter == 5) player.stats[2] = parser * careerWeight;
                    else player.stats[2] += parser * yearWeight;
                }

                //TEST
                if(spaceCounter == 6 || spaceCounter == 39){ //ERA
                    if(spaceCounter == 6) player.stats[3] = parser * careerWeight;
                    else player.stats[3] += parser * yearWeight;
                }

                if(spaceCounter == 9 || spaceCounter == 42){ //GAMES STARTED
                    if(spaceCounter == 9) player.stats[4] = parser * careerWeight;
                    else player.stats[4] += parser * yearWeight;
                }

                if(spaceCounter == 13 || spaceCounter == 46){ //INNINGS PITCHED
                    if(spaceCounter == 13) player.stats[5] = parser * careerWeight;
                    else player.stats[5] += parser * yearWeight;
                }

                if(spaceCounter == 14 || spaceCounter == 47){ //HITS ALLOWED
                    if(spaceCounter == 14) player.stats[6] = parser * careerWeight;
                    else player.stats[6] += parser * yearWeight;
                }

                if(spaceCounter == 16 || spaceCounter == 49){ //EARNED RUNS ALLOWED
                    if(spaceCounter == 16) player.stats[7] = parser * careerWeight;
                    else player.stats[7] += parser * yearWeight;
                }

                if(spaceCounter == 17 || spaceCounter == 50){ //HOMERUNS
                    if(spaceCounter == 17) player.stats[8] = parser * careerWeight;
                    else player.stats[8] += parser * yearWeight;
                }

                if(spaceCounter == 18 || spaceCounter == 51){ //WALKS
                    if(spaceCounter == 18) player.stats[9] = parser * careerWeight;
                    else player.stats[9] += parser * yearWeight;
                }

                if(spaceCounter == 20 || spaceCounter == 52){ //STRIKEOUTS
                    if(spaceCounter == 20) player.stats[10] = parser * careerWeight;
                    else player.stats[10] += parser * yearWeight;
                }

                if(spaceCounter == 24 || spaceCounter == 56){ //BATTERSFACED
                    if(spaceCounter == 24) player.stats[11] = parser * careerWeight;
                    else player.stats[11] += parser * yearWeight;
                }

                if(spaceCounter == 25 || spaceCounter == 57){ //ERA+
                    if(spaceCounter == 25) player.stats[12] = parser * careerWeight;
                    else player.stats[12] += parser * yearWeight;
                }

                if(spaceCounter == 26 || spaceCounter == 58){ //FIP
                    if(spaceCounter == 26) player.stats[13] = parser * careerWeight;
                    else player.stats[13] += parser * yearWeight;
                }

                if(spaceCounter == 27 || spaceCounter == 59){ //WHIP
                    if(spaceCounter == 27) player.stats[14] = parser * careerWeight;
                    else {
                        player.stats[14] += parser * yearWeight;
                        break;
                    }
                }
            }
        }
    }

    private Double convertToDouble(String s){
        return Double.parseDouble(s);
    }
}
