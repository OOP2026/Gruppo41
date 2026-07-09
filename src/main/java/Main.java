import controller.Controller;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Avvio dell'applicazione in corso...");
                    Controller controller = new Controller();
                    controller.avviaApplicazione();
                } catch (Exception e) {
                    System.err.println("Errore fatale durante l'avvio dell'applicazione!");
                    e.printStackTrace();
                }
            }
        });
    }
}
