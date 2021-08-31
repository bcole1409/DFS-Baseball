package DataStructures;

import DataStructures.PlayerTypes.PitcherPlayer;
import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;

public class LineupFactory {
    private LineupFactory(){
    }

    public static Player getPositionPlayer(String C, int BO, String N, String T, String P, int S, String s){
        return new PositionPlayer(C, BO, N, T, P, S, s);
    }

    public static Player getPitcherPlayer(String C, int BO, String N, String T, String P, int S, String s){
        return new PitcherPlayer(C, BO, N, T, P, S, s);
    }
}
