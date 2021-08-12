package Main;

import DataAggregator.DataCollector;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        //store data of schedule
        DataCollector data = new DataCollector();
        data.schedule.printDateGames();
    }
}
