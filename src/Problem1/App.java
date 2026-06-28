import javax.swing.SwingUtilities;
import service.LibraryManegmentSystem;
import ui.MainFrame;

public class App {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {

            LibraryManegmentSystem manager = new LibraryManegmentSystem();
            
            MainFrame frame = new MainFrame(manager);

            frame.setVisible(true);

        });

    }
}
