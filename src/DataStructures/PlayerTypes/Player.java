package DataStructures.PlayerTypes;

import DataStructures.Lineups;

import java.io.IOException;

public abstract class Player {
    public String Name;
    public String Team;
    public String Position;
    public int Salary;

    public Player(String N, String T, String P, int S) {
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
