package Problem2;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private MusicPlayer musicPlayer;
    private Graph graph;
    private MapPanel mapPanel;
    private JTextArea outputArea;
    private JComboBox<String> fromBox, toBox;
    private JTextField weightField;

    public GUI(Graph graph) {
        this.graph = graph;
        initUI();
        musicPlayer = new MusicPlayer("bgm.wav"); // 🎵 fire it up
        setTitle("Syrian Train Network - DSA 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // ---- Map Panel ----
        mapPanel = new MapPanel(graph);
        add(mapPanel, BorderLayout.CENTER);

        // ---- Control Panel ----
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        controlPanel.setPreferredSize(new Dimension(300, 550));

        // Station names for combos
        String[] stations = graph.getStationNames();

        // ADD EDGE
        JPanel addEdgePanel = new JPanel(new GridLayout(4,2,5,5));
        addEdgePanel.setBorder(BorderFactory.createTitledBorder("Add Edge"));
        addEdgePanel.add(new JLabel("From:"));
        fromBox = new JComboBox<>(stations);
        addEdgePanel.add(fromBox);
        addEdgePanel.add(new JLabel("To:"));
        toBox = new JComboBox<>(stations);
        addEdgePanel.add(toBox);
        addEdgePanel.add(new JLabel("Weight:"));
        weightField = new JTextField("1");
        addEdgePanel.add(weightField);
        JButton addEdgeBtn = new JButton("Add Edge");
        addEdgeBtn.addActionListener(e -> addEdge());
        addEdgePanel.add(addEdgeBtn);

        // SHORTEST PATH
        JPanel shortestPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        shortestPanel.setBorder(BorderFactory.createTitledBorder("Shortest Path (Dijkstra)"));

        // Combo boxes for shortest path
        JComboBox<String> fromPathBox = new JComboBox<>(stations);
        JComboBox<String> toPathBox = new JComboBox<>(stations);

        shortestPanel.add(new JLabel("From:"));
        shortestPanel.add(fromPathBox);
        shortestPanel.add(new JLabel("To:"));
        shortestPanel.add(toPathBox);

        JButton dijkstraBtn = new JButton("Find Path");
        dijkstraBtn.addActionListener(e -> {
            String start = (String) fromPathBox.getSelectedItem();
            String end = (String) toPathBox.getSelectedItem();
            
            // Highlight the path on the map
            int[] pathNodes = graph.getShortestPath(start, end);
            mapPanel.highlightPath(pathNodes);
        });
        shortestPanel.add(dijkstraBtn);

        // CYCLE DETECTION
        JPanel cyclePanel = new JPanel();
        cyclePanel.setBorder(BorderFactory.createTitledBorder("Cycle Detection"));
        JButton cycleBtn = new JButton("Detect Cycle");
        cycleBtn.addActionListener(e -> detectCycle());
        cyclePanel.add(cycleBtn);

        // SORT BY DEGREE
        JPanel sortPanel = new JPanel();
        sortPanel.setBorder(BorderFactory.createTitledBorder("Sort by Degree"));
        JButton sortBtn = new JButton("Heap Sort by Degree");
        sortBtn.addActionListener(e -> sortByDegree());
        sortPanel.add(sortBtn);

        // IMPORT / EXPORT
        JPanel ioPanel = new JPanel(new GridLayout(1,2,5,5));
        ioPanel.setBorder(BorderFactory.createTitledBorder("Import / Export"));
        JButton exportBtn = new JButton("Export");
        exportBtn.addActionListener(e -> exportGraph());
        JButton importBtn = new JButton("Import");
        importBtn.addActionListener(e -> importGraph());
        ioPanel.add(exportBtn);
        ioPanel.add(importBtn);

        // OUTPUT AREA
        outputArea = new JTextArea(8, 20);
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));

        // MUTE BUTTON
        JPanel mutePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton muteBtn = new JButton("🔊 Mute");
        muteBtn.addActionListener(e -> {
            musicPlayer.toggleMute();
            muteBtn.setText(musicPlayer.isMuted() ? "🔇 Unmute" : "🔊 Mute");
        });
        mutePanel.add(muteBtn);
        controlPanel.add(mutePanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Assemble control panel
        controlPanel.add(addEdgePanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,5)));
        controlPanel.add(shortestPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,5)));
        controlPanel.add(cyclePanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,5)));
        controlPanel.add(sortPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,5)));
        controlPanel.add(ioPanel);
        controlPanel.add(Box.createRigidArea(new Dimension(0,5)));
        controlPanel.add(scroll);

        add(controlPanel, BorderLayout.EAST);
    }

    private void addEdge() {
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();
        int weight;
        try {
            weight = Integer.parseInt(weightField.getText().trim());
            if (weight <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            outputArea.append("Invalid weight.\n");
            return;
        }
        graph.add(from, to, weight);
        mapPanel.repaint();
        outputArea.append("Added edge " + from + " <-> " + to + " (" + weight + ")\n");
    }

    private void detectCycle() {
        boolean hasCycle = graph.isCyclic();
        outputArea.append("Graph contains a cycle: " + hasCycle + "\n");
    }

    private void sortByDegree() {
        String sorted = graph.sort();
        outputArea.append("Stations sorted by degree (Heap Sort):\n" + sorted + "\n");
    }

    private void exportGraph() {
        String data = graph.export(graph.getAdj());
        // Show in a dialog with option to copy
        JTextArea textArea = new JTextArea(data, 15, 40);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scroll, "Exported Graph String", JOptionPane.PLAIN_MESSAGE);
    }

    private void importGraph() {
        // Prompt user to paste string
        JTextArea input = new JTextArea(10, 40);
        int result = JOptionPane.showConfirmDialog(this, new JScrollPane(input),
                "Paste the graph string and click OK", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                graph.importGraph(input.getText());
                mapPanel.repaint();
                outputArea.append("Graph imported successfully.\n");
            } catch (Exception ex) {
                outputArea.append("Import failed: " + ex.getMessage() + "\n");
            }
        }
    }
}