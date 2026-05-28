package gui;

import controller.Controller;
import model.ResponsabileOrario;
import javax.swing.*;
import java.awt.*;

    class ResponsabileOrarioPanel extends JPanel {
    public ResponsabileOrarioPanel(ResponsabileOrario responsabile, Controller controller) {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Area di Lavoro: Responsabile Orari (" + responsabile.getNome() + ")", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 1, 10, 10));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton btnNuovaLezione = new JButton("Pianifica Nuova Lezione");
        JButton btnNuovaAula = new JButton("Inserisci Nuova Aula Disponibile");
        JButton btnConflitti = new JButton("Verifica Conflitti e Sovrapposizioni");

        grid.add(btnNuovaLezione);
        grid.add(btnNuovaAula);
        grid.add(btnConflitti);
        add(grid, BorderLayout.CENTER);

        // Collegamento azioni sul modello
        btnConflitti.addActionListener(e -> {
            responsabile.visualizzaEventualiConflitti();
            JOptionPane.showMessageDialog(this, "Controllo conflitti eseguito. Verifica i log.");
        });
    }
}
