package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Docente extends Utente {

    private static final int MAX_VINCOLI = 3;

    private List<Insegnamento> insegnamenti;
    private List<Vincolo> vincoli;

    public Docente(String nome, String cognome, String email, String login, String password) {
        super(nome, cognome, email, login, password);
        this.insegnamenti = new ArrayList<>();
        this.vincoli = new ArrayList<>();
    }

    // Restituisce una copia non modificabile: evita che l'esterno alteri
    // la lista interna senza passare dai metodi della classe.
    public List<Insegnamento> getInsegnamenti() {
        return Collections.unmodifiableList(insegnamenti);
    }

    public void setInsegnamenti(List<Insegnamento> insegnamenti) {
        this.insegnamenti = new ArrayList<>(insegnamenti);
    }

    public void aggiungiInsegnamento(Insegnamento insegnamento) {
        insegnamenti.add(insegnamento);
    }

    public List<Vincolo> getVincoli() {
        return Collections.unmodifiableList(vincoli);
    }

    public void setVincoli(List<Vincolo> vincoli) {
        this.vincoli = new ArrayList<>(vincoli);
    }

    // La traccia impone: "Ogni docente può indicare al massimo tre vincoli".
    // Ritorna false se il limite è già stato raggiunto, cosi la GUI/Controller
    // puo' avvisare l'utente invece di aggiungere il vincolo silenziosamente.
    public boolean aggiungiVincolo(Vincolo vincolo) {
        if (vincoli.size() >= MAX_VINCOLI) {
            return false;
        }
        vincoli.add(vincolo);
        return true;
    }
}
