package gui;

import controller.Controller;
import model.Coordinatore;
import model.Docente;
import model.ResponsabileOrario;
import model.Studente;
import model.Utente;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;

        setTitle("Sistema Gestione Orari Universita'");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        LoginFrame loginFrame = new LoginFrame(this, controller);
        mainPanel.add(loginFrame, "LOGIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void cambiaSchermata(Utente utente) {
        String cardName;

        if (utente instanceof Coordinatore) {
            cardName = "COORDINATORE";
            mainPanel.add(new CoordinatoreFrame((Coordinatore) utente, controller), cardName);
        } else if (utente instanceof ResponsabileOrario) {
            cardName = "RESPONSABILE";
            mainPanel.add(new ResponsabileOrarioFrame((ResponsabileOrario) utente, controller), cardName);
        } else if (utente instanceof Docente) {
            cardName = "DOCENTE";
            mainPanel.add(new DocenteFrame((Docente) utente, controller), cardName);
        } else if (utente instanceof Studente) {
            cardName = "STUDENTE";
            mainPanel.add(new StudenteFrame((Studente) utente, controller), cardName);
        } else {
            cardName = "LOGIN";
        }

        cardLayout.show(mainPanel, cardName);
    }

    public void mostraLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }
}
