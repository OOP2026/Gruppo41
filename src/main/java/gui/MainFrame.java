package gui;

import controller.Controller;
import model.Lezione;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    protected Controller controller;
    protected JTable tabellaOrari;
    protected DefaultTableModel tableModel;

    public MainFrame(Controller controller) {
        this(controller, "Orario Accademico Ufficiale");
    }

    public MainFrame(Controller controller, String titolo) {
        this.controller = controller;
        setTitle(titolo);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel(titolo);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        topPanel.add(lblTitle);
        add(topPanel, BorderLayout.NORTH);

        String[] colonne = {"Insegnamento", "Docente", "Giorno", "Ora Inizio", "Ora Fine", "Aula"};
        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabellaOrari = new JTable(tableModel);
        add(new JScrollPane(tabellaOrari), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            controller.logout();
            this.dispose();
            controller.avviaApplicazione();
        });
        bottomPanel.add(btnLogout);
        add(bottomPanel, BorderLayout.SOUTH);

        aggiornaTabellaOrari(controller.getLezioni());
    }

    protected void aggiornaTabellaOrari(List<Lezione> lista) {
        tableModel.setRowCount(0);
        if (lista != null) {
            for (Lezione l : lista) {
                Object[] riga = {
                    l.getInsegnamento().getNome(),
                    l.getInsegnamento().getDocente().getCognome() + " " + l.getInsegnamento().getDocente().getNome(),
                    l.getGiornoSettimana(),
                    l.getOraInizio().toString(),
                    l.getOraFine().toString(),
                    l.getAula().getNome()
                };
                tableModel.addRow(riga);
            }
        }
    }
}
