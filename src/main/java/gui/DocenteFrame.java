package gui;

import controller.Controller;
import model.Docente;
import javax.swing.*;
import java.awt.*;

public class DocentePanel extends JPanel {
    public DocentePanel(Docente docente, Controller controller) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Dashboard Docente: " + docente.getNome() + " " + docente.getCognome() + " [" + docente.getRuolo() + "]", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton btnVisualizza = new JButton("Visualizza il mio Orario");
        JButton btnRichiediSpostamento = new JButton("Invia Richiesta Spostamento Lezione");

        centerPanel.add(btnVisualizza);
        centerPanel.add(btnRichiediSpostamento);
        add(centerPanel, BorderLayout.CENTER);

        btnVisualizza.addActionListener(e -> {
            docente.visualizzaOrario();
            JOptionPane.showMessageDialog(this, "Orario docente caricato nella console.");
        });
    }
}
