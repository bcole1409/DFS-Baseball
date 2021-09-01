package Statistics;

import DataStructures.PlayerTypes.Player;

public class Formulas {
    //variables
    private final double OutsPerGame = 26.72; //statistically accurate - not 27

    public static double RunsCreatedPerGame(Player p){ //(RC/OUTS)
        return RunsCreated(p)/BasicOuts(p);
    }
    private static double RunsCreated(Player p){
        return ((p.stats[2] + p.stats[3] + p.stats[4] + p.stats[5]) *
                (p.stats[2] + 2 * p.stats[3] + 3 * p.stats[4] + 4 * p.stats[5])) /
                (p.stats[1] + p.stats[12]);
    }

    private static double BasicOuts(Player p){
        return p.stats[0] - (0.18 * p.stats[0] -
                (p.stats[2] + p.stats[3] + p.stats[4] + p.stats[5]));
    }

    public static double probStrike(Player p){
        double strikeRate = 0.68; //league average
        strikeRate -= p.stats[3]*0.1;
        return strikeRate;
    }
}
