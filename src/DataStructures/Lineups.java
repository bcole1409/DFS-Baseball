package DataStructures;

import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lineups{
    static int i = 0; //increment

    private static final List<Player> DailyLineups = new ArrayList<Player>(); //list of all lineups

    public static void add(Player p) {
        //DailyLineups.add(arrayIndex(p), p);
        DailyLineups.add(i, p);
        i++;
    }

    //function returns correct spot to insert player
    private static int arrayIndex(Player p){
        int i = teamIndex(p);
        return 1;
    }

    private static int teamIndex(Player p){
        return 1;
    }
}


