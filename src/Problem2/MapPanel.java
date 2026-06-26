package Problem2;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapPanel extends JPanel {
    private Graph graph;
    private Set<String> highlightedEdges = new HashSet<>(); // <-- ADD THIS
    
    // ... POSITIONS map stays the same ...
    
    // Hardcoded positions for a Syria-like layout
    private static final Map<String, Point> POSITIONS = new HashMap<>();
    static {
        POSITIONS.put("Damascus",        new Point(310, 390));
        POSITIONS.put("Aleppo",          new Point(360, 140));
        POSITIONS.put("Homs",            new Point(290, 300));
        POSITIONS.put("Hama",            new Point(280, 240));
        POSITIONS.put("Latakia",         new Point(180, 210));
        POSITIONS.put("Tartus",          new Point(200, 270));
        POSITIONS.put("Deir_ez-Zor",     new Point(550, 260));
        POSITIONS.put("Al-Hasakah",      new Point(580, 70));
        POSITIONS.put("Ar-Raqqah",       new Point(480, 170));
        POSITIONS.put("Idlib",           new Point(270, 150));
        POSITIONS.put("As-Suwayda",      new Point(350, 450));
        POSITIONS.put("Daraa",           new Point(290, 480));
        POSITIONS.put("Quneitra",        new Point(230, 430));
        POSITIONS.put("Rif_Dimashq",     new Point(350, 340));
    }

    public MapPanel(Graph graph) {
        this.graph = graph;
        setPreferredSize(new Dimension(750, 550));
        setBackground(new Color(240, 248, 255));
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }
    
    // ADD THIS METHOD
    public void highlightPath(int[] pathNodes) {
        highlightedEdges.clear();
        if (pathNodes != null && pathNodes.length > 1) {
            String[] names = graph.getStationNames();
            for (int i = 0; i < pathNodes.length - 1; i++) {
                String edge = names[pathNodes[i]] + "->" + names[pathNodes[i + 1]];
                String edgeReverse = names[pathNodes[i + 1]] + "->" + names[pathNodes[i]];
                highlightedEdges.add(edge);
                highlightedEdges.add(edgeReverse);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (graph == null) return;

        int[][] adj = graph.getAdj();
        String[] names = graph.getStationNames();
        int n = names.length;

        // Draw edges
        for (int i = 0; i < n; i++) {
            Point p1 = POSITIONS.get(names[i]);
            if (p1 == null) continue;
            for (int j = i + 1; j < n; j++) {
                if (adj[i][j] > 0) {
                    Point p2 = POSITIONS.get(names[j]);
                    if (p2 == null) continue;
                    
                    // Check if this edge is on the highlighted path
                    String edgeKey = names[i] + "->" + names[j];
                    boolean isHighlighted = highlightedEdges.contains(edgeKey);
                    
                    // Thick glowing line for highlighted path
                    if (isHighlighted) {
                        g2.setStroke(new BasicStroke(5));
                        g2.setColor(new Color(255, 69, 0, 220)); // Red-orange glow
                    } else {
                        g2.setStroke(new BasicStroke(2));
                        g2.setColor(new Color(100, 100, 100, 180));
                    }
                    
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                    
                    // Weight label
                    int mx = (p1.x + p2.x) / 2;
                    int my = (p1.y + p2.y) / 2;
                    if (isHighlighted) {
                        g2.setColor(new Color(180, 0, 0));
                        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                    } else {
                        g2.setColor(Color.BLACK);
                        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                    }
                    g2.drawString(String.valueOf(adj[i][j]), mx, my);
                }
            }
        }

        // Draw stations
        for (String name : names) {
            Point p = POSITIONS.get(name);
            if (p == null) continue;
            int r = 15;
            g2.setColor(new Color(70, 130, 180));
            g2.fillOval(p.x - r, p.y - r, 2 * r, 2 * r);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 10));
            String letter = name.substring(0, 1);
            FontMetrics fm = g2.getFontMetrics();
            int lw = fm.stringWidth(letter);
            g2.drawString(letter, p.x - lw / 2, p.y + 4);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            fm = g2.getFontMetrics();
            int tw = fm.stringWidth(name);
            g2.drawString(name, p.x - tw / 2, p.y + r + 14);
        }
    }
}