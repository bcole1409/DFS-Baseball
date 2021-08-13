package DataStructures;

import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lineups{
    static int i = 0; //increment

    private static final List<Player> DailyLineups = new ArrayList<>(); //list of all lineups

    public static void add(Player p) {
        //DailyLineups.add(arrayIndex(p), p);
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
        //DailyLineups.add(arrayIndex(p), p);
        int size = DailyLineups.size();
        for(int i = 0; i < size; i++){
            System.out.print(i + ": ");
            System.out.println(DailyLineups.get(i).Name);
        }
    }
}


