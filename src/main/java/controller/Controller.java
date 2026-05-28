package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Lezione> lezioni;
    private List<Utente> utentiRegistrati;

    public Controller() {
        this.lezioni = new ArrayList<>();
        this.utentiRegistrati = new ArrayList<>();

        // Ora questi costruttori funzioneranno perfettamente!
        utentiRegistrati.add(new ResponsabileOrario("Mario", "Rossi", "mario@gmail.com", "1234", "ResponsabileOrario"));
        utentiRegistrati.add(new Coordinatore("Luigi", "Verdi", "luigi@gmail.com", "abcd","Coordinatore"));
        utentiRegistrati.add(new Studente("Simone","Guida","simone@gmail.com","1234","studente"));
    }

    public Utente login(String email, String password) {
        for (Utente u : utentiRegistrati) {
            if (u.getEmail().trim().equalsIgnoreCase(email.trim()) && u.getPassword().trim().equals(password.trim())) {
                return u;
            }
        }
        return null;
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
            boolean stessaAula = l.getAula().equals(nuova.getAula());
            boolean stessoGiorno = l.getGiornoSettimana().equals(nuova.getGiornoSettimana());
            boolean conflittoOrario = nuova.getOraInizio().isBefore(l.getOraFine())
                    && nuova.getOraFine().isAfter(l.getOraInizio());

            if(stessaAula && stessoGiorno && conflittoOrario) {
                return true;
            }
        }
        return false;
    }
}
