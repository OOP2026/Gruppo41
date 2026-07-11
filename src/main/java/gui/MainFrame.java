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

        LoginPanel loginPanel = new LoginPanel(this, controller);
        mainPanel.add(loginPanel, "LOGIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void cambiaSchermata(Utente utente) {
        String cardName;

        if (utente instanceof Coordinatore) {
            cardName = "COORDINATORE";
            mainPanel.add(new CoordinatorePanel((Coordinatore) utente, controller), cardName);
        } else if (utente instanceof ResponsabileOrario) {
            cardName = "RESPONSABILE";
            mainPanel.add(new ResponsabileOrarioPanel((ResponsabileOrario) utente, controller), cardName);
        } else if (utente instanceof Docente) {
            cardName = "DOCENTE";
            mainPanel.add(new DocentePanel((Docente) utente, controller), cardName);
        } else if (utente instanceof Studente) {
            cardName = "STUDENTE";
            mainPanel.add(new StudentePanel((Studente) utente, controller), cardName);
        } else {
            cardName = "LOGIN";
        }

        cardLayout.show(mainPanel, cardName);
    }

    public void mostraLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }
}
