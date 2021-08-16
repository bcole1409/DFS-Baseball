package DataStructures;

import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lineups{
    static int i = 0; //increment
    private static final List<Player> DailyLineups = new ArrayList<>(TeamHashTable.teamIndex.size()*9); //make size of total teams * 9

    public static void add(Player p) {
        DailyLineups.add(p);
    }

    //function returns correct spot to insert player
    private static int arrayIndex(Player p){
        int i = teamIndex(p);
        return 1;
    }

    private static int teamIndex(Player p){
        return 1;
    }

    public static void printAll() {
        int size = DailyLineups.size();

        System.out.print(size);

        for (Player dailyLineup : DailyLineups) {
            System.out.print(dailyLineup.BO + ": " + dailyLineup.CITY + " ");
            System.out.println(dailyLineup.Name);
        }
    }
}


