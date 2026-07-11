package dao;

import model.Aula;
import model.Insegnamento;
import model.Lezione;
import model.SpostamentoLezione;
import model.Vincolo;

import java.util.List;

public interface LezioneDAO {
    List<Lezione> getTutteLeLezioni();

    List<Lezione> getLezioniPerAnno(String annoCorso);

    List<Lezione> getLezioniPerDocente(String loginDocente);

    boolean inserisciLezione(Lezione lezione);

    List<Aula> getAuleDisponibili();

    boolean inserisciAula(Aula aula);

    List<Insegnamento> getInsegnamentiAttivi();

    boolean inserisciInsegnamento(Insegnamento insegnamento);

    boolean inserisciVincolo(String loginDocente, Vincolo vincolo);

    boolean richiediSpostamento(SpostamentoLezione spostamento);

    List<SpostamentoLezione> getRichiesteSpostamento();

    boolean aggiornaStatoSpostamento(SpostamentoLezione spostamento, String stato);
}
