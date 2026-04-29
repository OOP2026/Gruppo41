package model;

import java.time.LocalTime;

public class Main {
	public static void main(String[] args) {

		// Docente
		Docente d = new Docente();
		d.nome = "Mario";

		// Aula
		Aula a = new Aula();
		a.nome = "Aula 1";

		// Insegnamento
		Insegnamento i = new Insegnamento();
		i.nome = "Programmazione";

		// Lezione
		Lezione l = new Lezione();
		l.giornoDellaSettimana = "Lunedì";
		l.oraInizio = LocalTime.of(10, 0);
		l.oraFine = LocalTime.of(12, 0);
		l.docente = d;
		l.aula = a;
		l.insegnamento = i;

		System.out.println("Lezione:");
		System.out.println(l.giornoDellaSettimana + " " + l.oraInizio + " - " + l.oraFine);

		// Spostamento lezione
		SpostamentoLezione s = new SpostamentoLezione();
		s.stato = "IN ATTESA";
		s.lezione = l;

		// Coordinatore
		Coordinatore c = new Coordinatore();
		c.approvaRichiesta(s);

		System.out.println("Stato richiesta: " + s.stato);
	}
}
