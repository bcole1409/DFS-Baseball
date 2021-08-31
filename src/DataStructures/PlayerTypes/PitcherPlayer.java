package DataStructures.PlayerTypes;

import java.io.IOException;

public class PitcherPlayer extends Player{
    public int[] stats = new int[25];

    public PitcherPlayer(String C, int BO, String N, String T, String P, int S, String s){
        super(C, BO, N, T, P, S, s);
    }

    @Override
    public void run() throws IOException {
        super.run();
    }
}
