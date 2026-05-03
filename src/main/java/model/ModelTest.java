package model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testCreazioneOggetti() {

        // 🔹 Utente
        Utente u = new Utente("Mario", "Rossi", "mail", "1234");
        assertEquals("Mario", u.getNome());

        // 🔹 Studente
        Studente s = new Studente("Luca", "Bianchi", "mail", "pass", "S1");
        assertEquals("S1", s.getMatricola());

        // 🔹 Docente
        Docente d = new Docente("Anna", "Verdi", "mail", "pwd", "Prof");
        assertEquals("Prof", d.getRuolo());

        // 🔹 Aula
        Aula a = new Aula("Aula 1");
        assertEquals("Aula 1", a.getNome());

        // 🔹 Insegnamento
        Insegnamento ins = new Insegnamento("Prog", 12, 1);
        assertEquals(12, ins.getCfu());

        // 🔹 Lezione
        Lezione l = new Lezione("Lunedi", LocalTime.of(9, 0), LocalTime.of(11, 0));
        assertEquals("Lunedi", l.getGiornoSettimana());

        // 🔹 Vincolo
        Vincolo v = new Vincolo("Lunedi", LocalTime.of(8, 0), LocalTime.of(12, 0));
        assertEquals("Lunedi", v.getGiornoSettimana());

        // 🔹 Spostamento Lezione
        SpostamentoLezione sp = new SpostamentoLezione(LocalDate.now(), "IN_ATTESA");
        assertEquals("IN_ATTESA", sp.getStato());
    }
}

