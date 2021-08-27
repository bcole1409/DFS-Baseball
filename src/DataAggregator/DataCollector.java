package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import Reader.DraftKingsDownloader;
import Reader.FanGraphsDownloader;
import Reader.Schedule;

import java.io.IOException;

public class DataCollector extends Player {
    //public Schedule schedule; //create schedule
    //public DraftKingsDownloader playerPricing;
    //public StringConverter playerPrices; //temporary

    public DataCollector () throws IOException {
        run();
    }

    public void run() throws IOException {
        new Schedule();
        new DraftKingsDownloader(); //downloads all position and pitcher data - via baseballreference and rotogrinders
        new FanGraphsDownloader(); //downloads all reliever data - via fangraphs
    }
}
