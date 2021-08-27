package Reader;

import DataStructures.FanGraphsHTMLConverter;
import DataStructures.URLHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FanGraphsDownloader implements URLHandler {
    final String URL = "https://www.fangraphs.com/leaders.aspx?pos=all&stats=rel&lg=all&qual=0&type=8&season=2021&month=0&season1=2021&ind=0&team=0,ts&rost=0&age=0&filter=&players=0&startdate=2021-01-01&enddate=2021-12-31";
    private Document doc; //stores html

    public FanGraphsDownloader(){
        run();
    }

    private void run(){
        connect();
    }

    public void connect() {
        try{
            doc = Jsoup.connect(URL).get();
            parse();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void parse() throws InterruptedException, IOException{
        System.out.print("PARSING CONVERTING FANGRAPHS HTML");

        for(int i = 0; i < 4; i++){
            TimeUnit.SECONDS.sleep(1);
            System.out.print(".");
        }
        System.out.println();

        Elements Bullpens = doc.getElementsByClass("rgMasterTable"); //collect data from class: rgMasterTable

        new FanGraphsHTMLConverter(Bullpens.text()); //pass doc to converter
    }
}
