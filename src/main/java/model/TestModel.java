package model;

import java.time.LocalTime;

public class TestModel {
    public static void main(String[] args) {
        Aula aula = new Aula("Aula A1");
        Docente docente = new Docente("Mario", "Rossi", "mario.rossi@uni.it", "mrossi", "password123");
        Insegnamento insegnamento = new Insegnamento("Programmazione", 6, "I", docente);
        Lezione lezione = new Lezione(insegnamento, "Lunedi", LocalTime.of(9, 0), LocalTime.of(11, 0), aula);
        
        System.out.println("Test Model completato con successo per: " + lezione.getInsegnamento().getNome());
    }
}
