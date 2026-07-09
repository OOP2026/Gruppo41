package model;

import java.util.ArrayList;
import java.util.List;

public class Docente extends Utente {
    private List<Insegnamento> insegnamenti;
    private List<Vincolo> vincoli;

    public Docente(String nome, String cognome, String email, String login, String password) {
        super(nome, cognome, email, login, password);
        this.insegnamenti = new ArrayList<>();
        this.vincoli = new ArrayList<>();
    }

    public List<Insegnamento> getInsegnamenti() { return insegnamenti; }
    public void setInsegnamenti(List<Insegnamento> insegnamenti) { this.insegnamenti = insegnamenti; }
    public List<Vincolo> getVincoli() { return vincoli; }
    public void setVincoli(List<Vincolo> vincoli) { this.vincoli = vincoli; }

    public boolean aggiungiVincolo(Vincolo v) {
        if (this.vincoli.size() >= 3) {
            return false;
        }
        this.vincoli.add(v);
        return true;
    }
}
