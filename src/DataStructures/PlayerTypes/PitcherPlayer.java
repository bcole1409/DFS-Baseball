package DataStructures.PlayerTypes;

import java.io.IOException;

public class PitcherPlayer extends Player{
    int[] stats = new int[25];

    public PitcherPlayer(int C, int BO, String N, String T, String P, int S){
        super(C, BO, N, T, P, S);
    }

    @Override
    public void run() throws IOException {
        super.run();
    }
}
