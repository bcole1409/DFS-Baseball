package Main;


import DataAggregator.DataCollector;
import DataStructures.Lineups;
import DataStructures.StartingPitchers;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //store data of schedule
        DataCollector data = new DataCollector();
        Lineups.printAll();
        StartingPitchers.printAll();
    }
}
