package DataStructures;

import DataStructures.PlayerTypes.Player;

import java.util.ArrayList;
import java.util.List;

public class StartingPitchers {
    public static final List<Player> PitchingLineup = new ArrayList<>(30);

    public static void add(Player p){
        PitchingLineup.add(p);
    }

    public static void printAll(){
        for (Player player : PitchingLineup) {
            System.out.println(player.Name + " " + player.stats[3]);
        }
    }

}
