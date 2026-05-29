import DataStructures.*;

public class Main {
    public static void main(String[] args) {
        Heap h = new Heap(false);
        h.insert(10);
        h.insert(-10);
        h.insert(3);
        h.insert(100);
        while(!h.isEmpty()) {
            System.out.println(h.pop());
        }
    }
}
