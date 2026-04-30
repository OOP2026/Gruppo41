package model;

class ResponsabileOrario extends Docente {

     ResponsabileOrario(String nome, String cognome, String email, String password, String ruolo) {
        super(nome, cognome, email, password, ruolo);
    }

    public void creazioneLezione(Lezione l) {
        // creazione lezione
    }

    public void inserisciAulaDisponibile(Aula a) {
        // inserimento aula
    }

    public void visualizzaEventualiConflitti() {
        // controllo conflitti
    }
}
