package Main;


import DataAggregator.DataCollector;
import DataAggregator.GameSimulations;
import DataStructures.Lineups;
import DataStructures.TeamHashTable;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //Start Data Aggregator
        new DataCollector();
        //Lineups.printAll();
        TeamHashTable.printAll();
        new GameSimulations();
    }
}

//
//StartingPitchers.printAll();