package Problem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {
    Station[] stations;
    int[][] adj;
    boolean[] visited;
    String[] names;

    public Graph() {
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
                i++;
            }
            scanner.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void add(int u, int v, int weight) {
        adj[u][v] = weight;
        stations[u].degree++;
    }

    int shortestPathBetween(Station source, Station target) {
        int[] dist = new int[14];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source.id] = 0;
        
        Heap pq = new Heap(false);
        pq.push(source.id, 0);
        
        Arrays.fill(visited, false);
        
        while (!pq.isEmpty()) {
            Pair curr = pq.pop();
            int u = curr.a;
            
            if (visited[u]) continue;
            visited[u] = true;
            
            for (int v=0;v<14;v++) {
                int weight = adj[u][v];
                if(weight == -1 || u == v) continue;
                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.push(v, dist[v]);
                }
            }
        }
        
        return dist[target.id];
    }

    private boolean cycleDetection(int i, int p) {
        visited[i] = true;
        boolean res = false;
        for(int node=0;node<14;node++) {
            if(node == i) continue;
            if(visited[node] && node != p) {
                return true;
            }
            if(visited[node]) continue;
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

    void sort() {
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
        adj = newAdj;
        stations = newStations;
    }

    String[] export() {
        String[] result = new String[14];
        
        for(int i=0;i<14;i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(names[i]).append(" -> ");
            
            if (adj[i].length == 0) {
                sb.append("(no edges)");
            } else {
                int cnt = 0;
                int total = 0;
                for(int j=0;j<14;j++) {
                    if(adj[i][j] > -1) {
                        total++;
                    }
                }
                for(int j=0;j<14;j++) {
                    int e = adj[i][j];
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
            
            result[i] = sb.toString();
        }
        
        return result;
    }

    int[][] importGraph(String[] lines) {
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

}
