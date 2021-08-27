package Main;


import DataAggregator.DataCollector;
import DataStructures.Bullpens;
import DataStructures.Lineups;
import DataStructures.StartingPitchers;
import DataStructures.TeamHashTable;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //store data of schedule
        DataCollector data = new DataCollector();
        //Lineups.printAll();
        StartingPitchers.printAll();
        TeamHashTable.printAll();
        Bullpens.printAll();
    }
}
