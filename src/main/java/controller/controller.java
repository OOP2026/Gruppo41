package controller;

import model.Lezione;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Lezione> lezioni;

    public Controller() {

        lezioni = new ArrayList<>();
    }

    public boolean aggiungiLezione(Lezione nuova) {

        if(verificaConflitti(nuova)) {
            return false;
        }

        lezioni.add(nuova);

        return true;
    }

    private boolean verificaConflitti(Lezione nuova) {

        for(Lezione l : lezioni) {

            boolean stessaAula =
                    l.getAula().equals(nuova.getAula());

            boolean stessoGiorno =
                    l.getGiornoSettimana()
                            .equals(nuova.getGiornoSettimana());

            boolean conflittoOrario =
                    nuova.getOraInizio().isBefore(l.getOraFine())
                            &&
                            nuova.getOraFine().isAfter(l.getOraInizio());

            if(stessaAula && stessoGiorno && conflittoOrario) {

                return true;
            }
        }

        return false;
    }
}
