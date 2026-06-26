package Problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    private Station[] stations;
    private int[][] adj;
    private boolean[] visited;
    private String[] names;
    private Map <String, Integer> index;

    public Graph() {
        this.index = new HashMap<>();
        this.visited = new boolean[14];
        this.names = new String[14];
        this.adj = new int[14][14];
        for(int i=0;i<14;i++) {
            adj[i] = new int[14];
            for(int j=0;j<14;j++) {
                adj[i][j] = -1;
            }
        }
        this.stations = new Station[14];
        try {
            File file = new File("Governorates.txt");
            Scanner scanner = new Scanner(file);
            int i=0;
            while(scanner.hasNextLine()) {
                String city = scanner.nextLine();
                stations[i] = new Station(city);
                names[i] = city;
                index.put(city, i);
                i++;
            }
            scanner.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void add(String from, String to, int weight) {
        int u = index.get(from), v = index.get(to);
        adj[u][v] = weight;
        adj[v][u] = weight;
        stations[u].degree++;
        stations[v].degree++;
    }

    int[] getShortestPath(String beg, String end) {
        int source = index.get(beg);
        int target = index.get(end);
        int[] parent = new int[14];
        int[] dist = new int[14];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[source] = 0;
        
        Heap pq = new Heap(false);
        pq.push(source, 0);
        
        Arrays.fill(visited, false);
        
        while(!pq.isEmpty()) {
            Pair curr = pq.pop();
            int u = curr.a;
            
            if(visited[u]) continue;
            visited[u] = true;
            
            for(int v=0;v<14;v++) {
                int weight = adj[u][v];
                if(weight == -1 || u == v) continue;
                if(!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.push(v, dist[v]);
                }
            }
        }
        if(dist[target] == Integer.MAX_VALUE) {
            return new int[0];
        }
        
        int count = 0;
        int curr = target;
        while(curr != -1) {
            count++;
            curr = parent[curr];
        }
        
        int[] path = new int[count];
        curr = target;
        for(int i = count - 1; i >= 0; i--) {
            path[i] = curr;
            curr = parent[curr];
        }
        
        return path;
    }

    private boolean cycleDetection(int i, int p) {
        visited[i] = true;
        boolean res = false;
        for(int node=0;node<14;node++) {
            if(adj[i][node] == -1) continue;
            if(node == i) continue;
            if(visited[node] && node != p) {
                return true;
            }
            if(!visited[node])
                res |= cycleDetection(node, i);
        }
        return res;
    }

    boolean isCyclic() {
        Arrays.fill(visited, false);
        boolean res = false;
        for(int i=0;i<14;i++) {
            if(!visited[i])
                res |= cycleDetection(stations[i].id, -1);
        }
        return res;
    }

    String sort() {
        Heap pq = new Heap(true);
        for(int i=0;i<14;i++) {
            pq.push(i, stations[i].degree);
        }
        int[][] newAdj = new int[14][14];
        Station[] newStations = new Station[14];
        for(int i=0;i<14;i++) {
            int x = pq.pop().a;
            newStations[i] = stations[x];
            newAdj[i] = adj[x];
        }
        return export(newAdj);
    }

    String export(int[][] mat) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<14;i++) {
            sb.append(names[i]).append(" -> ");
            
            if (mat[i].length == 0) {
                sb.append("(no edges)");
            } else {
                int cnt = 0;
                int total = 0;
                for(int j=0;j<14;j++) {
                    if(mat[i][j] > -1) {
                        total++;
                    }
                }
                for(int j=0;j<14;j++) {
                    int e = mat[i][j];
                    if(e == -1 || i == j) continue;
                    cnt++;
                    sb.append(names[j])
                      .append("(")
                      .append(e)
                      .append(")");
                    
                    if (cnt < total) {
                        sb.append(", ");
                    }
                }
            }
            sb.append("\n");            
        }
        
        return sb.toString();
    }

    int[][] importGraph(String input) {
        String[] lines = input.split("\n");
        Map<String, Integer> nameToIndex = new HashMap<>();
        for(int i=0;i<14;i++) {
            nameToIndex.put(names[i], i);
        }
        
        int[][] newAdj = new int[14][14];
        for(int i=0;i<14;i++) {
            newAdj[i] = new int[14];
            for(int j=0;j<14;j++) {
                newAdj[i][j] = -1;
            }
        }
        
        for(String line : lines) {
            String[] parts = line.split(" -> ");
            if(parts.length != 2) continue;
            
            String source = parts[0].trim();
            Integer sourceIndex = nameToIndex.get(source);
            
            String destinationsPart = parts[1].trim();
            if(destinationsPart.equals("(no edges)")) continue;
            
            String[] destinations = destinationsPart.split(", ");
            for(String dest : destinations) {
                String[] destParts = dest.split("\\(");
                
                String destName = destParts[0].trim();
                String weightStr = destParts[1].replace(")", "").trim();
                
                Integer destIndex = nameToIndex.get(destName);
                if (destIndex == null) continue;
                
                int weight = Integer.parseInt(weightStr);
                newAdj[sourceIndex][destIndex] = weight;
            }
        }
        
        return newAdj;
    }

    public String[] getStationNames() {
        return this.names;
    }

    public int[][] getAdj() {
        return this.adj;
    }

}
