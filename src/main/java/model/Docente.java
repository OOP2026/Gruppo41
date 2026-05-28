package model;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Utente {
    private String ruolo;
    private List<Lezione> lezioni;
    private List<Vincolo> vincoli;

    public Docente(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password);
        this.ruolo = ruolo;
        this.lezioni = new ArrayList<>();
        this.vincoli = new ArrayList<>();
    }

    public String getRuolo() {
        return ruolo;
    }

    public List<Lezione> getLezioni() {
        return lezioni;
    }

    public void visualizzaOrario() {
        System.out.println("Visualizzazione orario docente...");
    }

    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }

    public List<Vincolo> getVincoli() {
        return vincoli;
    }

    public void setVincoli(List<Vincolo> vincoli) {
        this.vincoli = vincoli;
    }

    public void richiestaSpostamentoLezione(SpostamentoLezione spostamento) {
        // Metodo stub per i test
    }
}
