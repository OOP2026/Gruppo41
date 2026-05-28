package main;

import controller.Controller;
import gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Imposta la grafica nativa del sistema operativo
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.out.println("Errore Look and Feel: " + e.getMessage());
            }

            Controller controller = new Controller();

            // AVVIAMO IL MAINFRAME CENTRALIZZATO
            MainFrame app = new MainFrame(controller);
            app.setVisible(true);
        });
    }
}
