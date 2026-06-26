package Problem2;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        javax.swing.SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(graph);
            gui.setVisible(true);
        });
    }
}
