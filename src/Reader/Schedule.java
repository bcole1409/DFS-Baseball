package Reader;

import DataStructures.Games;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Schedule{
    public Games[] games = new Games[183]; //store LinkedList of games - 183
    File file;
    BufferedReader buffer;

    public Schedule() throws IOException {
         System.out.println("Generating 2021 MLB Schedule");

         try{
             this.file = new File("C:\\Users\\Brandon\\Desktop\\DFS\\MLB\\Schedule\\2021SKED.txt");
             this.buffer = new BufferedReader(new FileReader(file));
             read();
         }
         catch(IOException e){
             System.out.println("File doesn't exist");
         }
    }

    private void read() throws IOException { //read csv file stored in folder
        String line;
        int i = -1; //iterator
        String currentDate;
        Games currentGameDay = new Games("00000000");

        while((line = buffer.readLine()) != null){
            //get current date
            currentDate = line.substring(1,9);

            //store new dates in array
            if(!currentDate.equals(currentGameDay.Date)){ //search for current date
                //if one first element populate first array element
                i++; //increment to new array space
                currentGameDay.Date = currentDate;
                games[i] = new Games(currentDate); //create new games object
            }

            //format weird string errors
            if((int)line.charAt(38) == 34){
                games[i].list.add(line.substring(22,25),(line.substring(35,38))); //add game to linked list
            }
            else if((int)line.charAt(39) == 34){
                games[i].list.add(line.substring(22,25),(line.substring(36,39))); //add game to linked list
            }

            else{
                games[i].list.add(line.substring(22,25),(line.substring(37,40))); //add game to linked list
            }
        }
    }

    public void printDateGames(){ //function created to test linked list functionality
        DateFormatter date = new DateFormatter();
        System.out.printf("Today's games: %s \n", date.today2);

        for(int i = 0; i < 183; i++){
            if(date.today1.equals(games[i].Date)){
                games[i].list.printList();
                return;
            }
        }
    }
}
