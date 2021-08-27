package Reader;

import DataStructures.RotoHTMLConverter;
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
    ArrayList<String> teamLineUps = new ArrayList<>(); //list of html players
    ArrayList<String> teams = new ArrayList<>(); //list of html teams
    ArrayList<String> pitchers = new ArrayList<>(); //list of html teams
    RotoHTMLConverter listOfAllPlayers;

    public DraftKingsDownloader(){
        run();
    }

    private void run(){
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

    public void parse() throws InterruptedException, IOException {
        System.out.print("CONVERTING ROTOGRINDERS HTML");

        for(int i = 0; i < 4; i++){
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
        }

        System.out.println("\n");

        //Elements div = doc.select("div.schedules"); //select div class
        Elements ul = doc.select("div.schedules > ul");
        Elements li = ul.select("li"); //select all li under ul
        Elements Players = li.select("ul.players");
        Elements Teams = li.select("div.teams");
        Elements Pitchers = doc.getElementsByClass("pitcher players");

        //index increments initialized
        int i = 0;
        int j = 0;
        int k = 0;

        for (Element element : Players) {
            teamLineUps.add(i,element.text());
            i++; //increment index of array
        }

        for (Element element : Teams) {
            teams.add(j,element.text());
            j++; //increment index of array
        }

        for(Element element : Pitchers){
            pitchers.add(k,element.text());
            k++;
        }

        listOfAllPlayers = new RotoHTMLConverter(teamLineUps, teams, pitchers);
    }
}