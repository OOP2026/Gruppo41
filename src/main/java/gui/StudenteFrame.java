package gui;

import controller.Controller;
import model.Lezione;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class StudenteFrame extends MainFrame {
    private JComboBox<String> annoComboBox;
    private JButton logoutButton;

    public StudenteFrame(Controller controller) {
        super(controller, "Orario Lezioni - Portale Studenti");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Profilo Studente: " + 
                controller.getUtenteLoggato().getNome() + " " + 
                controller.getUtenteLoggato().getCognome());
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        logoutButton = new JButton("Disconnetti");
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Filtra per Anno di Corso:"));
        annoComboBox = new JComboBox<>(new String[]{"Tutti", "I", "II", "III"});
        filterPanel.add(annoComboBox);
        add(filterPanel, BorderLayout.SOUTH);

        annoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caricaDatiLezioni();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                dispose();
                controller.avviaApplicazione();
            }
        });

        caricaDatiLezioni();
    }

    private void caricaDatiLezioni() {
        String filtro = (String) annoComboBox.getSelectedItem();
        List<Lezione> lezioni;
        if ("Tutti".equals(filtro)) {
            lezioni = controller.getLezioni();
        } else {
            lezioni = controller.getLezioni().stream()
                    .filter(l -> l.getInsegnamento().getAnnoCorso().equals(filtro))
                    .collect(Collectors.toList());
        }
        aggiornaTabellaOrari(lezioni);
    }
}
