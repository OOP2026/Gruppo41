package gui;

import controller.Controller;
import model.Coordinatore;
import model.SpostamentoLezione;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CoordinatoreFrame extends JFrame {

    private final DefaultTableModel tableModel;
    private final Controller controller;
    private List<SpostamentoLezione> richieste;

    public CoordinatoreFrame(Coordinatore coordinatore, Controller controller) {
        this.controller = controller;

        setTitle("Pannello di Controllo Coordinatore");
        setSize(850, 550);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JLabel header = new JLabel("Pannello di Controllo Coordinatore: " + coordinatore.getNome(),
                SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        String[] colonne = {"Insegnamento", "Giorno attuale", "Orario attuale", "Nuovo giorno", "Nuovo orario", "Stato"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel(new FlowLayout());
        JButton btnAggiorna = new JButton("Aggiorna Richieste");
        JButton btnApprova = new JButton("Approva Richiesta Selezionata");
        JButton btnRifiuta = new JButton("Rifiuta Richiesta Selezionata");

        actionsPanel.add(btnAggiorna);
        actionsPanel.add(btnApprova);
        actionsPanel.add(btnRifiuta);
        add(actionsPanel, BorderLayout.SOUTH);

        btnAggiorna.addActionListener(e -> caricaRichieste());
        btnApprova.addActionListener(e -> gestisciRichiesta(table.getSelectedRow(), "APPROVATA"));
        btnRifiuta.addActionListener(e -> gestisciRichiesta(table.getSelectedRow(), "RIFIUTATA"));

        caricaRichieste();
    }

    private void caricaRichieste() {
        tableModel.setRowCount(0);
        richieste = controller.getRichiesteSpostamento();

        if (richieste == null) {
            return;
        }

        for (SpostamentoLezione s : richieste) {
            tableModel.addRow(new Object[]{
                    s.getLezione().getInsegnamento().getNome(),
                    s.getLezione().getGiornoSettimana(),
                    s.getLezione().getOraInizio() + " - " + s.getLezione().getOraFine(),
                    s.getNuovoGiorno(),
                    s.getNuovaOraInizio() + " - " + s.getNuovaOraFine(),
                    s.getStato()
            });
        }
    }

    private void gestisciRichiesta(int rigaSelezionata, String nuovoStato) {
        if (rigaSelezionata < 0 || richieste == null || rigaSelezionata >= richieste.size()) {
            JOptionPane.showMessageDialog(this, "Seleziona prima una richiesta dalla tabella.",
                    "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SpostamentoLezione selezionata = richieste.get(rigaSelezionata);
        boolean successo = controller.aggiornaStatoSpostamento(selezionata, nuovoStato);

        if (successo) {
            JOptionPane.showMessageDialog(this, "Richiesta " + nuovoStato.toLowerCase() + " con successo.");
            caricaRichieste();
        } else {
            JOptionPane.showMessageDialog(this, "Impossibile aggiornare la richiesta.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}
