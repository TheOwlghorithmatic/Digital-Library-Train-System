package Problem2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();
        int m = sc.nextInt();
        for(int i=0;i<m;i++) {
            int u = sc.nextInt(), v = sc.nextInt(), w = sc.nextInt();
            graph.add(u, v, w);
        }
        sc.close();
        System.out.println(graph.shortestPathBetween(0,4));
    }
}
