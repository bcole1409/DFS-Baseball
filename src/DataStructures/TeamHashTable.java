package DataStructures;

import java.util.Hashtable;

public class TeamHashTable { //stores sequence of teams in correct order
    //change pub
    public static Hashtable<Integer, String> teamIndex = new Hashtable<>(30); //store team index, which will universally help other data
    //structures to store data in the correct index pertaining to the correct team
    static int Index = 0; //used for hash table insertion

    public static void printAll(){
        for(int i = 0; i < 30; i++) System.out.println(teamIndex.get(i) + " teamIndex index: " + i);
    }
}
