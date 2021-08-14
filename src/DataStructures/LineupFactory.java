package DataStructures;

import DataStructures.PlayerTypes.PitcherPlayer;
import DataStructures.PlayerTypes.Player;
import DataStructures.PlayerTypes.PositionPlayer;

public class LineupFactory {
    private LineupFactory(){
    }

    public static Player getPositionPlayer(int C, int BO, String N, String T, String P, int S){
        return new PositionPlayer(C, BO, N, T, P, S);
    }

    public static Player getPitcherPlayer(int C, int BO, String N, String T, String P, int S){

        return new PitcherPlayer(C, BO, N, T, P, S);
    }
}
