package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import DataStructures.StartingPitchers;

public class GameSimulations {
    //Team Index -> used to pick correct teams
    int AwayTeam;
    int HomeTeam;

    //AwayTeam Variables
    int VisitorRuns;
    int VisitorOrder;
    Player VisitorPitcher = StartingPitchers.PitchingLineup.get(AwayTeam);
    Player CurrentVisitorBatter = Lineups.DailyLineups.get(VisitorOrder);

    //HomeTeam Variables
    int HomeRuns;
    int HomeOrder;
    Player HomePitcher = StartingPitchers.PitchingLineup.get(AwayTeam);
    Player CurrentHomeBatter = Lineups.DailyLineups.get(VisitorOrder);

    //Game/InningVariables
    int GameNumber = 0;
    int Outs;
    int Inning;

    public GameSimulations(){
        runGames();
    }

    public void runGames(){
        StartGame();
        StartInning();
    }

    private void StartGame(){ //initialize game variables
        if(GameNumber > 0){
            AwayTeam+=2;
            HomeTeam+=2;
        }

        else {
            Inning = 1;
            AwayTeam = 0;
            HomeTeam = 1;
        }

        GameNumber++;
        Inning = 1;
        HomeRuns = 0;
        VisitorRuns = 0;
        VisitorOrder = 1;
        HomeOrder = 1;
    }

    private void StartInning(){
        Outs = 1;
    }


}
