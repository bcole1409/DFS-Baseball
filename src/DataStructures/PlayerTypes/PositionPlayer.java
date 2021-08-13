package DataStructures.PlayerTypes;

import DataStructures.Lineups;

import java.io.IOException;

public class PositionPlayer extends Player{
    int[] stats = new int[50];

    public PositionPlayer(String N, String T, String P, int S){
        super(N, T, P, S);
    }
    @Override
    public void run() throws IOException {
        super.run();
    }
}
