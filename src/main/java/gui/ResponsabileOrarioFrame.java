package gui;

import controller.Controller;
import model.Aula;
import model.Insegnamento;
import model.Lezione;
import model.ResponsabileOrario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ResponsabileOrarioFrame extends JPanel {

    private final DefaultTableModel tableModel;
    private final Controller controller;

    public ResponsabileOrarioFrame(ResponsabileOrario responsabile, Controller controller) {
        this.controller = controller;


        setLayout(new BorderLayout());

        JLabel header = new JLabel("Area di Lavoro: Responsabile Orari (" + responsabile.getNome() + ")",
                SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        String[] colonne = {"Insegnamento", "Docente", "Giorno", "Ora inizio", "Ora fine", "Aula"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottoni = new JPanel(new FlowLayout());
        JButton btnNuovaLezione = new JButton("Pianifica Nuova Lezione");
        JButton btnNuovaAula = new JButton("Inserisci Nuova Aula");
        JButton btnAggiorna = new JButton("Aggiorna / Verifica Orario Completo");

        bottoni.add(btnNuovaLezione);
        bottoni.add(btnNuovaAula);
        bottoni.add(btnAggiorna);
        add(bottoni, BorderLayout.SOUTH);

        btnNuovaLezione.addActionListener(e -> apriFormNuovaLezione());
        btnNuovaAula.addActionListener(e -> apriFormNuovaAula());
        btnAggiorna.addActionListener(e -> caricaLezioni());

        caricaLezioni();
    }

    private void caricaLezioni() {
        tableModel.setRowCount(0);
        List<Lezione> lezioni = controller.getLezioni();

        if (lezioni == null) {
            return;
        }

        for (Lezione l : lezioni) {
            tableModel.addRow(new Object[]{
                    l.getInsegnamento().getNome(),
                    l.getInsegnamento().getDocente().getNome() + " " + l.getInsegnamento().getDocente().getCognome(),
                    l.getGiornoSettimana(),
                    l.getOraInizio(),
                    l.getOraFine(),
                    l.getAula().getNome()
            });
        }
    }

    private void apriFormNuovaAula() {
        String nome = JOptionPane.showInputDialog(this, "Nome della nuova aula (es. Aula A1):");
        if (nome == null || nome.trim().isEmpty()) {
            return;
        }

        boolean successo = controller.inserisciAula(new Aula(nome.trim()));
        if (successo) {
            JOptionPane.showMessageDialog(this, "Aula inserita correttamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Impossibile inserire l'aula.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void apriFormNuovaLezione() {
        List<Insegnamento> insegnamenti = controller.getInsegnamentiAttivi();
        List<Aula> aule = controller.getAuleDisponibili();

        if (insegnamenti == null || insegnamenti.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessun insegnamento attivo disponibile.");
            return;
        }
        if (aule == null || aule.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna aula disponibile.");
            return;
        }

        JComboBox<Insegnamento> comboInsegnamento = new JComboBox<>(insegnamenti.toArray(new Insegnamento[0]));
        JComboBox<Aula> comboAula = new JComboBox<>(aule.toArray(new Aula[0]));
        JTextField giornoField = new JTextField();
        JTextField oraInizioField = new JTextField("09:00");
        JTextField oraFineField = new JTextField("11:00");

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Insegnamento:"));
        form.add(comboInsegnamento);
        form.add(new JLabel("Aula:"));
        form.add(comboAula);
        form.add(new JLabel("Giorno (es. Lunedi):"));
        form.add(giornoField);
        form.add(new JLabel("Ora inizio (HH:mm):"));
        form.add(oraInizioField);
        form.add(new JLabel("Ora fine (HH:mm):"));
        form.add(oraFineField);

        int result = JOptionPane.showConfirmDialog(this, form, "Nuova Lezione",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            Insegnamento insegnamento = (Insegnamento) comboInsegnamento.getSelectedItem();
            Aula aula = (Aula) comboAula.getSelectedItem();
            LocalTime oraInizio = LocalTime.parse(oraInizioField.getText().trim());
            LocalTime oraFine = LocalTime.parse(oraFineField.getText().trim());

            if (!oraInizio.isBefore(oraFine)) {
                JOptionPane.showMessageDialog(this, "L'ora di inizio deve precedere l'ora di fine.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Lezione nuovaLezione = new Lezione(insegnamento, giornoField.getText().trim(), oraInizio, oraFine, aula);

            boolean successo = controller.aggiungiLezione(nuovaLezione);
            if (successo) {
                JOptionPane.showMessageDialog(this, "Lezione registrata con successo!");
                caricaLezioni();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Conflitto rilevato: aula occupata, docente gia' impegnato o vincolo violato.",
                        "Conflitto orario", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato orario non valido. Usa HH:mm (es. 09:00).",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
