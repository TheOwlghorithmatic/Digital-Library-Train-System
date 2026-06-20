package Problem2;

public class Station {
    static int counter = 0;
    String name;
    int degree;
    int id;
    public Station(String name) {
        this.name = name;
        this.degree = 0;
        this.id = counter++;
    }
}
