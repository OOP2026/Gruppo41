package model;

class Coordinatore extends Docente {

Coordinatore(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password, ruolo);
    }

    public void approvaRichiesta(SpostamentoLezione s) {
        s.setStato("APPROVATA");
    }

    public void rifiuta(SpostamentoLezione s) {
        s.setStato("RIFIUTATA");
    }

    public void modificaOrario(Lezione l, String giorno,
                               java.time.LocalTime inizio,
                               java.time.LocalTime fine) {

        l.setGiornoSettimana(giorno);
        l.setOraInizio(inizio);
        l.setOraFine(fine);
    }
}
