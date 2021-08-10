package Reader;

import DataStructures.StringConverter;
import DataStructures.URLHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class DraftKingsDownloader implements URLHandler {
    final String URL = "https://rotogrinders.com/lineups/mlb";
    private Document doc; //stores html
    ArrayList<String> teamLineUps = new ArrayList<>(); //size of schedule * 2
    public StringConverter listOfAllPlayers;

    public DraftKingsDownloader(){
        run();
    }

    public void run(){
        connect();
    }

    public void connect(){
        try {
            doc = Jsoup.connect(URL).get(); //html code
            //System.out.println(doc);
            parse();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void parse() throws InterruptedException {
        System.out.print("PARSER INITIATING");

        for(int i = 0; i < 3; i++){
            TimeUnit.SECONDS.sleep(2);
            System.out.print(".");
        }

        System.out.println("\n");

        //Elements div = doc.select("div.schedules"); //select div class
        Elements ul = doc.select("div.schedules > ul");
        Elements li = ul.select("li"); //select all li under ul
        //Elements Games = li.select("div");
        Elements Players = li.select("ul.players");

        int i = 0;

        for (Element element : Players) {
            teamLineUps.add(i,element.text());
            i++; //increment index of array

        }

        listOfAllPlayers = new StringConverter(teamLineUps);
    }
}

//System.out.println(element.text());
//System.out.println(element.select("span > span").text());