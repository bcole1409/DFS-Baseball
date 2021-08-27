package DataStructures;

import java.util.ArrayList;
import java.util.List;

public class Bullpens {
    private static final List<Bullpen> Bullpens = new ArrayList<>(1000);

    public static void add(int index, Bullpen b){
        Bullpens.add(index, b);
    }

    public static void printAll(){
        for(Bullpen b : Bullpens){
            if(b == null) continue;
            System.out.println(b.team);
        }
    }
}
