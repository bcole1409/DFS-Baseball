package Statistics;

import DataStructures.StringConverter;
import Reader.DraftKingsDownloader;
import Reader.Schedule;

import java.io.IOException;

public class DataCollector {
    public Schedule schedule; //create schedule
    public DraftKingsDownloader playerPricing;
    public StringConverter playerPrices;


    public DataCollector () throws IOException {
        run();
    }

    public void run() throws IOException {
        schedule = new Schedule();
        playerPricing = new DraftKingsDownloader();
        playerPrices = playerPricing.listOfAllPlayers;
    }
}
