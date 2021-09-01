package DataAggregator;

import DataStructures.Lineups;
import DataStructures.PlayerTypes.Player;
import DataStructures.StartingPitchers;
import DataStructures.TeamHashTable;
import Statistics.Formulas;
import Statistics.OffensivePointSystem;

import java.math.*;

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

    //bases
    int b1;
    int b2;
    int b3;

    public GameSimulations(){
        runGames();
    }

    public void runGames(){
        Game = 0; //set game to 0, increment to 100
        startSeries(); //start series of games between two teams

        //run games 100 times
        while(Game < 100){
            StartGame(); //initialize all variables
            while(Inning >= 9 && HomeRuns != VisitorRuns){
                StartInning(); //play inning by inning until we have a winner
            }

            //TEST FOR SCORING SIMULATIONS
            System.out.println("Game Score: " + TeamHashTable.teamIndex.get(AwayTeam)
                    + VisitorRuns + TeamHashTable.teamIndex.get(HomeTeam) + HomeRuns) ;

            Game++;
        }
    }

    private void startSeries(){
        if(GameNumber > 0){
            AwayTeam+=2;
            HomeTeam+=2;
        }

        else {
            Inning = 1;
            AwayTeam = 0;
            HomeTeam = 1;
        }
    }

    private void StartGame(){ //initialize game variables
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
        Player currentBatter = Lineups.DailyLineups.get(AwayTeam * 9 + VisitorOrder);
        Outs = 0;
        b1 = 0;
        b2 = 0;
        b3 = 0;

        while(Outs!=3){
            if(throwPitch(HomePitcher)==1){ //if pitcher throws strike -> then what outcome

            }

            else{ //if pitcher throw ball -> then what outcome

            }
        }

    }

    private void HomeBatting(){
        Outs = 0;
        b1 = 0;
        b2 = 0;
        b3 = 0;

    }

    private int throwPitch(Player p){ //int 0 = ball ---- 1 = strike]
        if(Math.random() <= Formulas.probStrike(p)) return 1;
        return 0;
    }

    private int batterDecision(Player p){
        return 1;
    }
}
