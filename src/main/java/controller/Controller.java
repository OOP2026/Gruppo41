package controller;

import model.Lezione;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Lezione> lezioni;

    public Controller() {
        lezioni = new ArrayList<>();
    }

    public void avviaApplicazione() {
        System.out.println("Applicazione avviata dal Controller.");
    }

    public boolean aggiungiLezione(Lezione nuova) {
        if (verificaConflitti(nuova)) {
            return false;
        }
        lezioni.add(nuova);
        return true;
    }

    private boolean verificaConflitti(Lezione nuova) {
        for (Lezione l : lezioni) {
            boolean stessaAula = l.getAula().equals(nuova.getAula());
            
            boolean stessoDocente = l.getInsegnamento().getDocente()
                    .equals(nuova.getInsegnamento().getDocente());
            
            boolean stessoGiorno = l.getGiornoSettimana()
                    .equals(nuova.getGiornoSettimana());
            
            boolean conflittoOrario = nuova.getOraInizio().isBefore(l.getOraFine())
                    && nuova.getOraFine().isAfter(l.getOraInizio());

            if ((stessaAula || stessoDocente) && stessoGiorno && conflittoOrario) {
                return true;
            }
        }
        return false;
    }
}
