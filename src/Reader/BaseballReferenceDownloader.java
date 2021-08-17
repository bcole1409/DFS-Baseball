package Reader;

import DataStructures.PlayerTypes.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import DataStructures.URLHandler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BaseballReferenceDownloader implements URLHandler {
    //PARSER VARIABLES
    Player player;

    //URL VARIABLES
    StringBuilder URL = new StringBuilder();
    final String URLbase = "https://www.baseball-reference.com/players/";
    final List<String> URLend = Arrays.asList("01-bat.shtml#all_batting_advanced", "02-bat.shtml#all_batting_advanced"
            ,"03-bat.shtml#all_batting_advanced","04-bat.shtml#all_batting_advanced"
            ,"05-bat.shtml#all_batting_advanced","06-bat.shtml#all_batting_advanced"
            ,"07-bat.shtml#all_batting_advanced");
    final List<String> forbidden = Arrays.asList("Giancarlo Stanton", "Travis d'Arnaud");
    final List<String> real = Arrays.asList("Mike Stanton", "Travis Darnaud");

    //used to sort data
    private Document doc; //stores html

    public BaseballReferenceDownloader(Player p) throws IOException {
        this.player = p;
        run();
    }

    private void run() throws IOException {
        connect();
    }

    public void connect() throws IOException {
        try{
            //test for forbidden names -> change name to correct format
            if(forbidden.contains(player.Name)){
                System.out.println("TRUE");
                System.out.println(real.get(forbidden.indexOf(player.Name)));

                player.Name = real.get(forbidden.indexOf(player.Name));
            }

            String fName = firstName(player.Name).toLowerCase();
            String lName = lastName(player.Name).toLowerCase();


            int index = 0;

            //help find correct player
            String d = "Died:";
            String t = "Team:";
            int i = 0;
            boolean isFound = false;

            String currentWord;

            while(!isFound){
                URL.append(URLbase).append(lName.charAt(0) + "/").append(lName).append(fName).append(URLend.get(index));
                System.out.println(URL.toString());

                doc = Jsoup.connect(URL.toString()).get();
                Element battingStandard = doc.select("tbody").first();

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
                        System.out.println("Found Team");
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

            parse();

        }
        catch (Exception e)
        {
            System.out.println("Unable to find player");

        }
    }

    public void parse() throws InterruptedException {
        System.out.print("");
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
                return s.substring(i + 1 ,s.length());
            }
        }

        return null; //name not found
    }
}
