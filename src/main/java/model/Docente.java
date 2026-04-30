package model;

import java.util.List;

class Docente extends Utente {
    private String ruolo;
    private List<Vincolo> vincoli;
    private List<Lezione> lezioni;

    Docente(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password);
        this.ruolo = ruolo;
    }

    public void visualizzaOrario() {}
    public void richiestaSpostamentoLezione(SpostamentoLezione s) {}

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public List<Vincolo> getVincoli() {
        return vincoli;
    }
    public void setVincoli(List<Vincolo> vincoli) {
        this.vincoli = vincoli;
    }

    public List<Lezione> getLezioni() {
        return lezioni;
    }
    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }
}
