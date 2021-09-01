package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import Reader.DraftKingsDownloader;
import Reader.FanGraphsDownloader;
import Reader.Schedule;

import java.io.IOException;

public class DataCollector extends Player {
    public DataCollector () throws IOException {
        run();
    }

    public void run() throws IOException {
        new Schedule(); //downloads CSV scheduled games for today
        new DraftKingsDownloader(); //downloads all position and pitcher data - via baseballreference and rotogrinders
        new FanGraphsDownloader(); //downloads all reliever data - via fangraphs
    }
}
