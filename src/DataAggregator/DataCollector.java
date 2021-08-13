package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import Reader.DraftKingsDownloader;
import Reader.Schedule;

import java.io.IOException;

public class DataCollector extends Player {
    public Schedule schedule; //create schedule
    public DraftKingsDownloader playerPricing;
    //public StringConverter playerPrices; //temporary

    public DataCollector () throws IOException {
        super();
        run();
    }

    public void run() throws IOException {
        schedule = new Schedule();
        playerPricing = new DraftKingsDownloader();
        //playerPrices = playerPricing.listOfAllPlayers;
    }
}
