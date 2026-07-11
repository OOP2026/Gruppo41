package gui;

import controller.Controller;
import model.Docente;
import model.Lezione;
import model.SpostamentoLezione;
import model.Vincolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class DocenteFrame extends JFrame {

    private final DefaultTableModel tableModel;
    private final Docente docente;
    private final Controller controller;

    public DocenteFrame(Docente docente, Controller controller) {
        this.docente = docente;
        this.controller = controller;

        setTitle("Dashboard Docente");
        setSize(800, 550);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel header = new JLabel(
                "Dashboard Docente: " + docente.getNome() + " " + docente.getCognome(),
                SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(header, BorderLayout.NORTH);

        String[] colonne = {"Insegnamento", "Giorno", "Ora inizio", "Ora fine", "Aula"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottoni = new JPanel(new FlowLayout());
        JButton btnAggiorna = new JButton("Aggiorna Orario");
        JButton btnVincolo = new JButton("Aggiungi Vincolo (max 3)");
        JButton btnSpostamento = new JButton("Richiedi Spostamento Lezione");

        bottoni.add(btnAggiorna);
        bottoni.add(btnVincolo);
        bottoni.add(btnSpostamento);
        add(bottoni, BorderLayout.SOUTH);

        btnAggiorna.addActionListener(e -> caricaOrario());
        btnVincolo.addActionListener(e -> apriFormVincolo());
        btnSpostamento.addActionListener(e -> apriFormSpostamento());

        caricaOrario();
    }

    private void caricaOrario() {
        tableModel.setRowCount(0);
        List<Lezione> lezioni = controller.getLezioniPerDocente(docente.getLogin());

        if (lezioni == null || lezioni.isEmpty()) {
            return;
        }

        for (Lezione l : lezioni) {
            tableModel.addRow(new Object[]{
                    l.getInsegnamento().getNome(),
                    l.getGiornoSettimana(),
                    l.getOraInizio(),
                    l.getOraFine(),
                    l.getAula().getNome()
            });
        }
    }

    private void apriFormVincolo() {
        if (docente.getVincoli().size() >= 3) {
            JOptionPane.showMessageDialog(this, "Hai gia' raggiunto il numero massimo di 3 vincoli.",
                    "Limite raggiunto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField giornoField = new JTextField();
        JTextField inizioField = new JTextField("09:00");
        JTextField fineField = new JTextField("11:00");

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Giorno (es. Lunedi):"));
        form.add(giornoField);
        form.add(new JLabel("Ora inizio (HH:mm):"));
        form.add(inizioField);
        form.add(new JLabel("Ora fine (HH:mm):"));
        form.add(fineField);

        int result = JOptionPane.showConfirmDialog(this, form, "Nuovo Vincolo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Vincolo vincolo = new Vincolo(
                    giornoField.getText().trim(),
                    LocalTime.parse(inizioField.getText().trim()),
                    LocalTime.parse(fineField.getText().trim())
            );

            boolean successo = controller.aggiungiVincolo(vincolo);
            if (successo) {
                JOptionPane.showMessageDialog(this, "Vincolo aggiunto correttamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Impossibile aggiungere il vincolo (limite raggiunto).",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato orario non valido. Usa HH:mm (es. 09:00).",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apriFormSpostamento() {
        List<Lezione> lezioni = controller.getLezioniPerDocente(docente.getLogin());
        if (lezioni == null || lezioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non hai lezioni da spostare.");
            return;
        }

        JComboBox<Lezione> comboLezioni = new JComboBox<>(lezioni.toArray(new Lezione[0]));
        JTextField nuovoGiornoField = new JTextField();
        JTextField nuovaOraInizioField = new JTextField("09:00");
        JTextField nuovaOraFineField = new JTextField("11:00");

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Lezione da spostare:"));
        form.add(comboLezioni);
        form.add(new JLabel("Nuovo giorno:"));
        form.add(nuovoGiornoField);
        form.add(new JLabel("Nuova ora inizio (HH:mm):"));
        form.add(nuovaOraInizioField);
        form.add(new JLabel("Nuova ora fine (HH:mm):"));
        form.add(nuovaOraFineField);

        int result = JOptionPane.showConfirmDialog(this, form, "Richiesta Spostamento Lezione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Lezione lezioneSelezionata = (Lezione) comboLezioni.getSelectedItem();
            SpostamentoLezione spostamento = new SpostamentoLezione(
                    lezioneSelezionata,
                    nuovoGiornoField.getText().trim(),
                    LocalTime.parse(nuovaOraInizioField.getText().trim()),
                    LocalTime.parse(nuovaOraFineField.getText().trim())
            );

            boolean successo = controller.richiediSpostamento(spostamento);
            if (successo) {
                JOptionPane.showMessageDialog(this, "Richiesta di spostamento inviata al responsabile.");
            } else {
                JOptionPane.showMessageDialog(this, "Impossibile inviare la richiesta.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato orario non valido. Usa HH:mm (es. 09:00).",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
