package model;

import java.time.LocalTime;

    public class Coordinatore extends Docente {

    public Coordinatore(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password, ruolo);
    }

    public void approvaRichiesta(SpostamentoLezione s) {
        s.setStato("APPROVATA");
    }

    public void rifiuta(SpostamentoLezione s) {
        s.setStato("RIFIUTATA");
    }

    public void modificaOrario(Lezione l, String giorno, LocalTime inizio, LocalTime fine) {

        l.setGiornoSettimana(giorno);
        l.setOraInizio(inizio);
        l.setOraFine(fine);
    }
}
