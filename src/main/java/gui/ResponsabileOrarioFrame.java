package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class ResponsabileOrarioFrame extends MainFrame {
    private JButton aggiungiLezioneButton;
    private JButton logoutButton;

    public ResponsabileOrarioFrame(Controller controller) {
        super(controller, "Pannello Responsabile Orario - Configurazione");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Pannello Responsabile: " + 
                controller.getUtenteLoggato().getNome() + " " + 
                controller.getUtenteLoggato().getCognome());
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        logoutButton = new JButton("Disconnetti");
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        aggiungiLezioneButton = new JButton("Nuova Lezione");
        bottomPanel.add(aggiungiLezioneButton);
        add(bottomPanel, BorderLayout.SOUTH);

        logoutButton.addActionListener(e -> {
            controller.logout();
            dispose();
            controller.avviaApplicazione();
        });

        aggiungiLezioneButton.addActionListener(e -> mostraDialogNuovaLezione());

        aggiornaTabellaOrari(controller.getLezioni());
    }

    private void mostraDialogNuovaLezione() {
        JTextField insegnamentoField = new JTextField();
        JTextField giornoField = new JTextField();
        JTextField oraInizioField = new JTextField("09:00");
        JTextField oraFineField = new JTextField("11:00");
        JTextField aulaField = new JTextField();

        Object[] message = {
                "Nome Insegnamento:", insegnamentoField,
                "Giorno:", giornoField,
                "Ora Inizio (HH:mm):", oraInizioField,
                "Ora Fine (HH:mm):", oraFineField,
                "Aula:", aulaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Pianifica Nuova Lezione", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Docente d = (Docente) controller.getUtenteLoggato();
                Insegnamento ins = new Insegnamento(insegnamentoField.getText(), 6, "I", d);
                Aula aula = new Aula(aulaField.getText());
                Lezione nuova = new Lezione(
                        ins,
                        giornoField.getText(),
                        LocalTime.parse(oraInizioField.getText()),
                        LocalTime.parse(oraFineField.getText()),
                        aula
                );
                if (controller.aggiungiLezione(nuova)) {
                    JOptionPane.showMessageDialog(this, "Lezione programmata correttamente!");
                    aggiornaTabellaOrari(controller.getLezioni());
                } else {
                    JOptionPane.showMessageDialog(this, "Impossibile inserire: rilevato conflitto di orario, aula occupata o vincolo docente violato!", "Errore di Conflitto", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato dati errato o non valido!", "Errore compilazione", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
