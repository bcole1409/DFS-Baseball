package DataStructures;

import DataStructures.PlayerTypes.Player;
import Statistics.Formulas;

import java.util.ArrayList;
import java.util.List;

public class Lineups{
    static int i = 0; //increment
    public static final List<Player> DailyLineups = new ArrayList<>(TeamHashTable.teamIndex.size()*9); //make size of total teams * 9

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
            System.out.println();
            System.out.print(dailyLineup.Salary + "::" + dailyLineup.Name + " player for the " + dailyLineup.CITY + " ");
            System.out.print("Batting " + dailyLineup.BO + " from the " + dailyLineup.PlateSide + " of the plate" + ". Playing " + dailyLineup.Position);
            //System.out.print(Formulas.RunsCreatedPerGame(dailyLineup));

        }


    }
}


