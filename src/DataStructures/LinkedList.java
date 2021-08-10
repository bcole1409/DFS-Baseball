package DataStructures;

public class LinkedList {
    Node head;
    int size = 0;

    public static class Node{
        Node next;
        String team1;
        String team2;

        Node(String t1, String t2){
            this.team1 = t1;
            this.team2 = t2;
            this.next = null;
        }
    }

    public void add(String t1, String t2){
        //generate newNode
        Node newNode = new Node(t1, t2);
        if(head == null){
            head = newNode;
        }

        else{
            Node temp = head;
            while(temp.next != null){
                temp = temp.next;
            }

            temp.next = newNode;
        }

        //increase size
        size ++;
    }

    public void printList(){
        //generate newNode
        if(head == null){
            System.out.println("No Games");
        }

        else{
            int i = 1;
            Node temp = head;
            while(temp != null){
                System.out.printf("Game %d: ", i);
                System.out.println(temp.team1 + " vs. " + temp.team2);
                temp = temp.next;
                i++;
            }
        }

        //increase size
        size ++;
    }
}
