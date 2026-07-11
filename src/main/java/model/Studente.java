package model;

public class Studente extends Utente {

    private String matricola;
    private String annoCorso;

    public Studente(String nome, String cognome, String email, String login, String password,
                     String matricola, String annoCorso) {
        super(nome, cognome, email, login, password);
        this.matricola = matricola;
        this.annoCorso = annoCorso;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    public String getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(String annoCorso) {
        this.annoCorso = annoCorso;
    }
}
