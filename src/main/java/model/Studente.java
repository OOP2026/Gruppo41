package model;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

    public class Studente extends Utente {
    private String matricola;
    private List<Insegnamento> insegnamenti;

    public Studente(String nome, String cognome, String email, String password, String matricola) {
        super(nome, cognome, email, password);
        this.matricola = matricola;
    }

    public void visualizzaOrarioAnnoDiCorso() {
        //metodo da implementare
    }
    public void visualizzaAula() {
        //metodo da implementare
    }

    public String getMatricola() {
        return matricola;
    }
    public void setMatricola(String matricola) {

        this.matricola = matricola;
    }

    public List<Insegnamento> getInsegnamenti() {

        return insegnamenti;
    }
    public void setInsegnamenti(List<Insegnamento> insegnamenti) {
        this.insegnamenti = insegnamenti;
    }
}
