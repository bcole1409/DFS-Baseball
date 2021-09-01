package DataStructures;

public class Bullpens {
    public static Bullpen[] bullpens = new Bullpen[30];

    public static void add(int index, Bullpen b){
        bullpens[index] = b;
    }

    public static void printAll(){
        for(int i = 0; i < 30 ; i++){
            if(bullpens[i] == null) continue;
            System.out.println(bullpens[i].team + " Bullpens Index: " + bullpens[i].stats[8]);
        }
    }
}
