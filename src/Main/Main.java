package Main;

import DataAggregator.DataCollector;
import DataStructures.Bullpens;
import DataStructures.Lineups;
import DataStructures.StartingPitchers;
import DataStructures.TeamHashTable;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //Start Data Aggregator
        new DataCollector();

        TeamHashTable.printAll();
        Lineups.printAll();

        StartingPitchers.printAll();
        Bullpens.printAll();
        //Testing: Work in Progress
        //new GameSimulations();
    }
}