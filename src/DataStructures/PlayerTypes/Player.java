package DataStructures.PlayerTypes;

import DataStructures.Lineups;

import java.io.IOException;

public abstract class Player {
    public double[]stats = new double[25];
    public String CITY;
    public int BO;
    public String Name;
    public String Team;
    public String Position;
    public int Salary;
    public String PlateSide;

    public Player(String C, int B, String N, String T, String P, int S, String ps) {
        this.CITY = C;
        this.BO = B;
        this.Name = N;
        this.Team = T;
        this.Position = P;
        this.Salary = S;
        this.PlateSide = ps;
    }

    public Player() {
    }

    public void run() throws IOException {
        Lineups.add(this);
    }

    /*STATS TABLE
    [0] = ATBATS
    [1] = RUNS
    [2] = SINGLES
    [3] = DOUBLES
    [4] = TRIPLES
    [5] = HOMERUNS


     */
}
