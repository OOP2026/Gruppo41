package gui;

import controller.Controller;
import model.Studente;
import javax.swing.*;
import java.awt.*;

public class StudentePanel extends JPanel {
    public StudentePanel(Studente studente, Controller controller) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Dashboard Studente: " + studente.getNome() + " " + studente.getCognome() + " (Matricola: " + studente.getMatricola() + ")", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout());
        JButton btnOrario = new JButton("Visualizza Orario Anno di Corso");
        JButton btnAula = new JButton("Visualizza Aula");

        centerPanel.add(btnOrario);
        centerPanel.add(btnAula);
        add(centerPanel, BorderLayout.CENTER);

        // Azioni
        btnOrario.addActionListener(e -> {
            studente.visualizzaOrarioAnnoDiCorso();
            JOptionPane.showMessageDialog(this, "Funzionalità 'Visualizza Orario' invocata sul modello.");
        });

        btnAula.addActionListener(e -> {
            studente.visualizzaAula();
            JOptionPane.showMessageDialog(this, "Funzionalità 'Visualizza Aula' invocata sul modello.");
        });
    }
}
