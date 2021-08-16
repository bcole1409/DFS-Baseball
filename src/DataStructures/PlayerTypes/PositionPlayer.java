package DataStructures.PlayerTypes;

import java.io.IOException;

public class PositionPlayer extends Player{
    public int[] stats = new int[50];

    public PositionPlayer(String C, int BO, String N, String T, String P, int S){
        super(C, BO, N, T, P, S);
    }
    @Override
    public void run() throws IOException {
        super.run();
    }
}
