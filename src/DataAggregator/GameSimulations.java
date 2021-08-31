package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import DataStructures.StartingPitchers;
import DataStructures.TeamHashTable;

public class GameSimulations {
    //Team Index -> used to pick correct teams
    int AwayTeam;
    int HomeTeam;

    //AwayTeam Variables
    int VisitorRuns;
    int VisitorOrder;
    Player VisitorPitcher = StartingPitchers.PitchingLineup.get(AwayTeam);
    //Player CurrentVisitorBatter = Lineups.DailyLineups.get(VisitorOrder);

    //HomeTeam Variables
    int HomeRuns;
    int HomeOrder;
    Player HomePitcher = StartingPitchers.PitchingLineup.get(AwayTeam);
    //Player CurrentHomeBatter = Lineups.DailyLineups.get(VisitorOrder);

    //Game/InningVariables
    int Game; //used for total simulations
    int GameNumber = 0; //used for total game occurring today
    int Outs;
    int Inning;

    public GameSimulations(){
        runGames();
    }

    public void runGames(){
        StartGame(); //initialize all variables
        Game = 0; //set game to 0, increment to 100

        //run games 100 times
        while(Game < 100){
            while(Inning >= 9 && HomeRuns != VisitorRuns){
                StartInning(); //play inning by inning until we have a winner
            }

            //TEST FOR SCORING SIMULATIONS
            System.out.println("Game Score: " + TeamHashTable.teamIndex.get(AwayTeam)
                    + VisitorRuns + TeamHashTable.teamIndex.get(HomeTeam) + HomeRuns) ;

            Game++;
        }
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

        //initialize at the start of each new game
        GameNumber++;
        Inning = 1;
        HomeRuns = 0;
        VisitorRuns = 0;

        //keep track of batting order
        VisitorOrder = 1;
        HomeOrder = 1;

        //GRAB PITCHER DATA
        VisitorPitcher = StartingPitchers.PitchingLineup.get(AwayTeam);
        HomePitcher = StartingPitchers.PitchingLineup.get(HomeTeam);
    }

    private void StartInning(){
        Inning++;
        AwayBatting();
        HomeBatting();
    }

    private void AwayBatting(){
        Outs = 0;

    }

    private void HomeBatting(){
        Outs = 0;

    }
}
