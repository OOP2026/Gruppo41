package model;

import java.util.*;

// Docente estende Utente
class Docente extends Utente {
    private String ruolo;
    public List<Vincolo> vincoli = new ArrayList<>();

    public void visualizzaOrario() {
        System.out.println("Orario docente");
    }

    public void richiestaSpostamentoLezione() {
        System.out.println("Richiesta spostamento");
    }
}
