package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudenteFrame extends JPanel {
    private final DefaultTableModel tableModel;

    public StudenteFrame(Studente studente, Controller controller) {
        setLayout(new BorderLayout());
        String[] colonne = {"Insegnamento", "Giorno", "Ora inizio", "Ora fine", "Aula"};
        tableModel = new DefaultTableModel(colonne, 0);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        
        JButton btn = new JButton("Aggiorna");
        add(btn, BorderLayout.SOUTH);
        btn.addActionListener(e -> caricaDati(studente, controller));
        caricaDati(studente, controller);
    }
    
    private void caricaDati(Studente s, Controller c) {
        tableModel.setRowCount(0);
        for(Lezione l : c.getLezioniPerAnno(s.getAnnoCorso())) {
            tableModel.addRow(new Object[]{l.getInsegnamento().getNome(), l.getGiornoSettimana(), l.getOraInizio(), l.getOraFine(), l.getAula().getNome()});
        }
    }
}
