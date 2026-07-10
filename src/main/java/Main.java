import controller.Controller;
import gui.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        
        controller.setLauncher(() -> {
            SwingUtilities.invokeLater(() -> {
                new LoginFrame(controller).setVisible(true);
            });
        });
        
        controller.avviaApplicazione();
    }
}
