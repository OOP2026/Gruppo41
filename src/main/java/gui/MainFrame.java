package gui;

import controller.Controller;
import model.Lezione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    protected Controller controller;
    protected JTable table;
    protected DefaultTableModel tableModel;

    public MainFrame(Controller controller, String titolo) {
        this.controller = controller;
        setTitle(titolo);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"Giorno", "Ora Inizio", "Ora Fine", "Insegnamento", "Aula", "Docente"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    protected void aggiornaTabellaOrari(List<Lezione> lezioni) {
        tableModel.setRowCount(0);
        for (Lezione l : lezioni) {
            tableModel.addRow(new Object[]{
                    l.getGiornoSettimana(),
                    l.getOraInizio().toString(),
                    l.getOraFine().toString(),
                    l.getInsegnamento().getNome(),
                    l.getAula().getNome(),
                    l.getInsegnamento().getDocente().getNome() + " " + l.getInsegnamento().getDocente().getCognome()
            });
        }
    }
}
