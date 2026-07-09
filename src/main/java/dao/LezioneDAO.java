package dao;

import model.Lezione;
import model.Aula;
import model.Insegnamento;
import model.SpostamentoLezione;
import model.Vincolo;
import java.util.List;

public interface LezioneDAO {
    List<Lezione> getTutteLeLezioni();
    List<Lezione> getLezioniPerAnno(String annoCorso);
    List<Lezione> getLezioniPerDocente(String loginDocente);
    boolean inserisciLezione(Lezione lezione);
    List<Aula> getAuleDisponibili();
    List<Insegnamento> getInsegnamentiAttivi();
    boolean inserisciVincolo(String loginDocente, Vincolo vincolo);
    boolean richiediSpostamento(SpostamentoLezione spostamento);
    List<SpostamentoLezione> getRichiesteSpostamento();
    boolean aggiornaStatoSpostamento(SpostamentoLezione spostamento, String stato);
}
