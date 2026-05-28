package model;

public class Coordinatore extends Docente {

    public Coordinatore(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password, ruolo);
    }

    public void approvaRichiesta(SpostamentoLezione spostamento) {
        // Metodo stub per i test
    }
}
