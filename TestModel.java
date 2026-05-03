package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestModel {

	public static void main(String[] args) {

		// 🔹 Creazione utenti
		Studente studente = new Studente("Mario", "Rossi", "mario@email.com", "1234", "S001");

		Docente docente = new Docente("Luigi", "Verdi", "luigi@email.com", "abcd", "Professore");

		Coordinatore coordinatore = new Coordinatore("Anna", "Bianchi", "anna@email.com", "pass", "Coordinatore");

		ResponsabileOrario responsabile = new ResponsabileOrario("Paolo", "Neri", "paolo@email.com", "pwd", "Responsabile");

		// 🔹 Aula
		Aula aula = new Aula("Aula 101");
		aula.setLezioni(new ArrayList<>());

		// 🔹 Insegnamento
		Insegnamento prog = new Insegnamento("Programmazione", 12, 1);
		prog.setLezioni(new ArrayList<>());

		// 🔹 Lezione
		Lezione lezione = new Lezione("Lunedi", LocalTime.of(9, 0), LocalTime.of(11, 0));

		lezione.setAula(aula);
		lezione.setInsegnamento(prog);

		// collegamenti
		aula.getLezioni().add(lezione);
		prog.getLezioni().add(lezione);

		docente.setLezioni(new ArrayList<>());
		docente.getLezioni().add(lezione);

		// 🔹 Vincolo docente
		Vincolo vincolo = new Vincolo("Lunedi", LocalTime.of(8, 0), LocalTime.of(13, 0));

		docente.setVincoli(new ArrayList<>());
		docente.getVincoli().add(vincolo);

		// 🔹 Spostamento lezione
		SpostamentoLezione spostamento = new SpostamentoLezione(LocalDate.now(), "IN_ATTESA");

		spostamento.setLezione(lezione);
		spostamento.setNuovaData(LocalDate.now().plusDays(1));
		spostamento.setNuovoOrarioInizio(LocalTime.of(10, 0));
		spostamento.setNuovoOrarioFine(LocalTime.of(12, 0));

		// 🔹 Azioni
		docente.richiestaSpostamentoLezione(spostamento);

		coordinatore.approvaRichiesta(spostamento);

		responsabile.creazioneLezione(lezione);

		// 🔹 Output di test
		System.out.println("Studente: " + studente.getNome());
		System.out.println("Docente: " + docente.getNome());
		System.out.println("Insegnamento: " + prog.getNome());
		System.out.println("Aula: " + aula.getNome());
		System.out.println("Stato richiesta: " + spostamento.getStato());
		System.out.println("Nuovo orario: " + spostamento.getNuovoOrarioInizio() + " - " + spostamento.getNuovoOrarioFine());
	}
}

