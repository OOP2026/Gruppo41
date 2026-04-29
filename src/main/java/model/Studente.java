package model;

// Studente estende Utente
class Studente extends Utente {
    private String matricola;

    public void visualizzaOrarioAnnoDiCorso() {
        System.out.println("Orario anno di corso");
    }

    public void visualizzaAula() {
        System.out.println("Visualizza aula");
    }
}
