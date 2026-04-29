package model;

// ResponsabileOrario estende Docente
class ResponsabileOrario extends Docente {

    public void creazioneLezione() {
        System.out.println("Creazione lezione");
    }

    public void inserisciAulaDisponibile() {
        System.out.println("Inserisci aula");
    }

    public void visualizzaEventualiConflitti() {
        System.out.println("Controllo conflitti");
    }
}
