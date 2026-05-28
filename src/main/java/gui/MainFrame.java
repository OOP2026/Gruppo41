package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Sistema Gestione Orari Università");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inizializza e aggiunge il pannello di Login
        LoginPanel loginPanel = new LoginPanel(this, controller);
        mainPanel.add(loginPanel, "LOGIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    // Metodo cruciale: viene chiamato dal LoginPanel per cambiare schermata in base al ruolo
    public void cambiaSchermata(Utente utente) {
        String cardName = "";

        if (utente instanceof ResponsabileOrario) {
            cardName = "RESPONSABILE";
            mainPanel.add(new ResponsabileOrarioPanel((ResponsabileOrario) utente, controller), cardName);
        } else if (utente instanceof Coordinatore) {
            cardName = "COORDINATORE";
            mainPanel.add(new CoordinatorePanel((Coordinatore) utente, controller), cardName);
        } else if (utente instanceof Docente) {
            cardName = "DOCENTE";
            mainPanel.add(new DocentePanel((Docente) utente, controller), cardName);
        } else if (utente instanceof Studente) {
            cardName = "STUDENTE";
            mainPanel.add(new StudentePanel((Studente) utente, controller), cardName);
        }

        cardLayout.show(mainPanel, cardName);
    }
    
    public void mostraLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }
}
