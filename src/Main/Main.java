package Main;

import DataAggregator.DataCollector;
import DataAggregator.GameSimulations;
import DataStructures.Lineups;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //Start Data Aggregator
        new DataCollector();
        Lineups.printAll();
        //run game simulations to produce player projections
        new GameSimulations();
    }
}

//StartingPitchers.printAll();
//Lineups.printAll();
//TeamHashTable.printAll();