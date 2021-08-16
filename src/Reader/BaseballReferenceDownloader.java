package Reader;

import DataStructures.PlayerTypes.Player;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import DataStructures.URLHandler;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BaseballReferenceDownloader implements URLHandler {
    //PARSER VARIABLES
    Player player;

    //URL VARIABLES
    StringBuilder URL = new StringBuilder();
    final String URLbase = "https://www.baseball-reference.com/players/";
    final List<String> URLend = Arrays.asList("01-bat.shtml#all_batting_advanced", "02-bat.shtml#all_batting_advanced"
            ,"03-bat.shtml#all_batting_advanced");

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
            String fName = firstName(player.Name);
            String lName = lastName(player.Name);
            int index = 0;
            URL.append(URLbase).append(lName.charAt(0) + "/").append(lName).append(fName).append(URLend.get(index));

            /*
            while(true){
                URL.append(URLbase).append(lName.charAt(0) + "/").append(lName).append(fName).append(URLend.get(index));
                doc = Jsoup.connect(URL.toString()).get();

                Elements div = doc.select("a href");
                System.out.println(URL.toString());

                System.out.println("yrdy");
                System.out.println(div.toString());
                System.out.println("dfg");

                if(true){
                    break;
                }

                index++;
            }
            */

            parse();

        }
        catch (InterruptedException e)
        {
            System.out.println("Unable to find player");
            e.printStackTrace();
        }
    }

    public void parse() throws InterruptedException {
        System.out.print("PARSING CONVERTING BASEBALLREFERENCES TO HTML");
    }

    private String firstName(String s){
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
