package gui;

import controller.Controller;
import model.SpostamentoLezione;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CoordinatoreFrame extends ResponsabileOrarioFrame {
    private JButton gestisciRichiesteButton;

    public CoordinatoreFrame(Controller controller) {
        super(controller);
        setTitle("Pannello Coordinatore Corso - Controllo Totale");

        JPanel bottomPanel = (JPanel) getContentPane().getComponent(2);
        gestisciRichiesteButton = new JButton("Valuta Richieste Spostamento");
        bottomPanel.add(gestisciRichiesteButton, 0);

        gestisciRichiesteButton.addActionListener(e -> mostraDialogRichieste());
    }

    private void mostraDialogRichieste() {
        List<SpostamentoLezione> richieste = controller.getRichiesteSpostamento();
        if (richieste == null || richieste.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nessuna richiesta di spostamento in attesa.", "Stato Richieste", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] opzioni = {"Approva", "Rifiuta", "Annulla"};
        for (SpostamentoLezione s : richieste) {
            if ("IN ATTESA".equals(s.getStato())) {
                String msg = "Richiesta di spostamento per: " + s.getLezione().getInsegnamento().getNome() + "\n" +
                        "Da: " + s.getLezione().getGiornoSettimana() + "\n" +
                        "A: " + s.getNuovoGiorno() + " dalle " + s.getNuovaOraInizio() + " alle " + s.getNuovaOraFine();

                int scelta = JOptionPane.showOptionDialog(this, msg, "Valutazione Spostamento",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[2]);

                if (scelta == 0) {
                    controller.aggiornaStatoSpostamento(s, "APPROVATA");
                    JOptionPane.showMessageDialog(this, "Richiesta Approvata!");
                } else if (scelta == 1) {
                    controller.aggiornaStatoSpostamento(s, "RIFIUTATA");
                    JOptionPane.showMessageDialog(this, "Richiesta Rifiutata!");
                }
            }
        }
        aggiornaTabellaOrari(controller.getLezioni());
    }
}
