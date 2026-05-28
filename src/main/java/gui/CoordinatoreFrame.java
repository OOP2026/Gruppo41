package gui;

import controller.Controller;
import model.Coordinatore;
import javax.swing.*;
import java.awt.*;

    class CoordinatorePanel extends JPanel {
    public CoordinatorePanel(Coordinatore coordinatore, Controller controller) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Pannello di Controllo Coordinatore: " + coordinatore.getNome(), SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        JPanel actionsPanel = new JPanel(new FlowLayout());
        JButton btnApprova = new JButton("Approva Richiesta");
        JButton btnRifiuta = new JButton("Rifiuta Richiesta");
        JButton btnModifica = new JButton("Modifica Orario Lezione");

        actionsPanel.add(btnApprova);
        actionsPanel.add(btnRifiuta);
        actionsPanel.add(btnModifica);
        add(actionsPanel, BorderLayout.CENTER);

        // Esempio logica di approvazione
        btnApprova.addActionListener(e -> {
            // Qui passeresti la richiesta selezionata dalla GUI
            JOptionPane.showMessageDialog(this, "Richiesta Approvata con successo dal Coordinatore.");
        });
    }
}
