package DataAggregator;

import DataStructures.Player;
import DataStructures.StringConverter;
import Reader.DraftKingsDownloader;
import Reader.Schedule;

import java.io.IOException;

public class DataCollector extends Player {
    public Schedule schedule; //create schedule
    public DraftKingsDownloader playerPricing;
    public StringConverter playerPrices; //temporary

    public DataCollector () throws IOException {
        run();
    }

    public void run() throws IOException {
        schedule = new Schedule();
        playerPricing = new DraftKingsDownloader();
        playerPrices = playerPricing.listOfAllPlayers;
    }
}
