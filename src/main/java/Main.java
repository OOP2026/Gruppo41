import controller.Controller;
import gui.MainFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller(); //
        
        controller.setLauncher(() -> { 
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(controller);
                mainFrame.setVisible(true);
            });
        });
        
        controller.avviaApplicazione(); 
    }
}
