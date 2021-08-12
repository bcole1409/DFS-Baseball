package Reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import DataStructures.URLHandler;


import java.io.IOException;

public class BaseballReferenceDownloader implements URLHandler {
    StringBuilder URL;
    final String URLbase = "https://www.baseball-reference.com/players/";
    final String URLend = "01-bat.shtml#all_batting_advanced";
    private Document doc; //stores html

    public BaseballReferenceDownloader() throws IOException {
        run();
    }

    private void run() throws IOException {
        connect();
    }

    public void connect() throws IOException {
        try{
            URL.append(URLbase).append(URLend);
            doc = Jsoup.connect(URL.toString()).get();
            parse();
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    public void parse() throws InterruptedException {

    }
}
