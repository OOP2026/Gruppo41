package gui;

import controller.Controller;
import model.Lezione;
import model.Studente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudenteFrame extends JPanel {

    private final DefaultTableModel tableModel;

    public StudenteFrame(Studente studente, Controller controller) {
        setTitle("Dashboard Studente");
        setSize(700, 500);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel header = new JLabel(
                "Orario - " + studente.getNome() + " " + studente.getCognome()
                        + " (Matricola: " + studente.getMatricola() + ", Anno: " + studente.getAnnoCorso() + ")",
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

        JButton btnAggiorna = new JButton("Aggiorna Orario");
        add(btnAggiorna, BorderLayout.SOUTH);

        btnAggiorna.addActionListener(e -> caricaOrario(studente, controller));

        caricaOrario(studente, controller);
    }

    private void caricaOrario(Studente studente, Controller controller) {
        tableModel.setRowCount(0);
        List<Lezione> lezioni = controller.getLezioniPerAnno(studente.getAnnoCorso());

        if (lezioni == null || lezioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna lezione trovata per il tuo anno di corso.");
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
}
