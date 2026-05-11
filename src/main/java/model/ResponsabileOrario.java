package model;

    public class ResponsabileOrario extends Docente {

     public ResponsabileOrario(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password, ruolo);
    }

    public void creazioneLezione(Lezione l) {
        // metodo da implementare
    }

    public void inserisciAulaDisponibile(Aula a) {
        // inserimento aula
    }

    public void visualizzaEventualiConflitti() {
        // controllo conflitti
    }
}
