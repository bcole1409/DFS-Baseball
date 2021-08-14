package DataStructures.PlayerTypes;

import DataStructures.Lineups;

import java.io.IOException;

public abstract class Player {
    public int CITY;
    public int BO;
    public String Name;
    public String Team;
    public String Position;
    public int Salary;

    public Player(int C, int B, String N, String T, String P, int S) {
        this.CITY = C;
        this.BO = B;
        this.Name = N;
        this.Team = T;
        this.Position = P;
        this.Salary = S;
    }

    public Player() {
    }

    public void run() throws IOException {
        Lineups.add(this);
    }
}
