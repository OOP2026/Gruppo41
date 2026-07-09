package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class DocenteFrame extends MainFrame {
    private JButton vincoloButton;
    private JButton spostaLezioneButton;
    private JButton logoutButton;

    public DocenteFrame(Controller controller) {
        super(controller, "Area Personale Docente - Gestione Orari");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Profilo Docente: Prof. " + 
                controller.getUtenteLoggato().getNome() + " " + 
                controller.getUtenteLoggato().getCognome());
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        logoutButton = new JButton("Disconnetti");
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        vincoloButton = new JButton("Imposta Vincolo");
        spostaLezioneButton = new JButton("Richiedi Spostamento");
        bottomPanel.add(vincoloButton);
        bottomPanel.add(spostaLezioneButton);
        add(bottomPanel, BorderLayout.SOUTH);

        logoutButton.addActionListener(e -> {
            controller.logout();
            dispose();
            controller.avviaApplicazione();
        });

        vincoloButton.addActionListener(e -> mostraDialogNuovoVincolo());
        spostaLezioneButton.addActionListener(e -> mostraDialogSpostamento());

        caricaLezioniDocente();
    }

    private void caricaLezioniDocente() {
        String loginDocente = controller.getUtenteLoggato().getLogin();
        List<Lezione> lezioniFiltrate = controller.getLezioni().stream()
                .filter(l -> l.getInsegnamento().getDocente().getLogin().equals(loginDocente))
                .collect(Collectors.toList());
        aggiornaTabellaOrari(lezioniFiltrate);
    }

    private void mostraDialogNuovoVincolo() {
        JTextField giornoField = new JTextField();
        JTextField oraInizioField = new JTextField("09:00");
        JTextField oraFineField = new JTextField("11:00");

        Object[] message = {
                "Giorno:", giornoField,
                "Ora Inizio (HH:mm):", oraInizioField,
                "Ora Fine (HH:mm):", oraFineField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Imposta Vincolo Indisponibilità", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Vincolo v = new Vincolo(
                        giornoField.getText(),
                        LocalTime.parse(oraInizioField.getText()),
                        LocalTime.parse(oraFineField.getText())
                );
                if (controller.aggiungiVincolo(v)) {
                    JOptionPane.showMessageDialog(this, "Vincolo inserito correttamente!");
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: limite vincoli superato (Max 3)!", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato dati non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostraDialogSpostamento() {
        JTextField insegnamentoField = new JTextField();
        JTextField giornoCorrenteField = new JTextField();
        JTextField nuovoGiornoField = new JTextField();
        JTextField oraInizioField = new JTextField("09:00");
        JTextField oraFineField = new JTextField("11:00");

        Object[] message = {
                "Nome Insegnamento da spostare:", insegnamentoField,
                "Giorno Corrente:", giornoCorrenteField,
                "Nuovo Giorno Proposto:", nuovoGiornoField,
                "Nuova Ora Inizio (HH:mm):", oraInizioField,
                "Nuova Ora Fine (HH:mm):", oraFineField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Invia Richiesta di Spostamento", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Docente d = (Docente) controller.getUtenteLoggato();
                Insegnamento ins = new Insegnamento(insegnamentoField.getText(), 6, "I", d);
                Lezione l = new Lezione(ins, giornoCorrenteField.getText(), LocalTime.of(0,0), LocalTime.of(0,0), new Aula(""));
                SpostamentoLezione sl = new SpostamentoLezione(
                        l,
                        nuovoGiornoField.getText(),
                        LocalTime.parse(oraInizioField.getText()),
                        LocalTime.parse(oraFineField.getText())
                );
                if (controller.richiediSpostamento(sl)) {
                    JOptionPane.showMessageDialog(this, "Richiesta inviata correttamente!");
                } else {
                    JOptionPane.showMessageDialog(this, "Errore durante l'invio della richiesta.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato dati errato!", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
