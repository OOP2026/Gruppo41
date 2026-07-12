package controller;

import dao.LezioneDAO;
import dao.UtenteDAO;
import implementazioneDao.LezionePostgresDAO;
import implementazioneDao.UtentePostgresDAO;
import model.Aula;
import model.Docente;
import model.Insegnamento;
import model.Lezione;
import model.SpostamentoLezione;
import model.Utente;
import model.Vincolo;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<Lezione> lezioni;[cite: 7]
    private final UtenteDAO utenteDao;[cite: 7]
    private final LezioneDAO lezioneDao;[cite: 7]
    private Utente utenteLoggato;[cite: 7]
    private AppLauncher launcher;[cite: 7]

    public Controller() {
        this.utenteDao = new UtentePostgresDAO();[cite: 7]
        this.lezioneDao = new LezionePostgresDAO();[cite: 7]
        this.lezioni = new ArrayList<>();[cite: 7]
    }

    public void setLauncher(AppLauncher launcher) {
        this.launcher = launcher;[cite: 7]
    }

    public void avviaApplicazione() {
        if (launcher != null) {[cite: 7]
            launcher.launch();[cite: 7]
        }
    }

    public boolean login(String username, String password) {
        Utente u = utenteDao.login(username, password);[cite: 7]
        if (u != null) {[cite: 7]
            this.utenteLoggato = u;[cite: 7]
            return true;[cite: 7]
        }
        return false;[cite: 7]
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;[cite: 7]
    }

    public void logout() {
        this.utenteLoggato = null;[cite: 7]
    }

    public List<Lezione> getLezioni() {
        try {
            this.lezioni = lezioneDao.getTutteLeLezioni();[cite: 7]
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il recupero delle lezioni dal database", e);
        }
        return lezioni;[cite: 7]
    }

    public List<Lezione> getLezioniPerAnno(String anno) {
        return lezioneDao.getLezioniPerAnno(anno);[cite: 7]
    }

    public List<Lezione> getLezioniPerDocente(String login) {
        return lezioneDao.getLezioniPerDocente(login);[cite: 7]
    }

    public List<Aula> getAuleDisponibili() {
        return lezioneDao.getAuleDisponibili();[cite: 7]
    }

    public boolean inserisciAula(Aula aula) {
        return lezioneDao.inserisciAula(aula);[cite: 7]
    }

    public List<Insegnamento> getInsegnamentiAttivi() {
        return lezioneDao.getInsegnamentiAttivi();[cite: 7]
    }

    public boolean inserisciInsegnamento(Insegnamento insegnamento) {
        return lezioneDao.inserisciInsegnamento(insegnamento);[cite: 7]
    }

    public boolean aggiungiLezione(Lezione lezione) {
        if (verificaConflitti(lezione) && verificaVincoliDocente(lezione)) {[cite: 7]
            return lezioneDao.inserisciLezione(lezione);[cite: 7]
        }
        return false;[cite: 7]
    }

    private boolean verificaConflitti(Lezione nuova) {
        List<Lezione> esistenti = getLezioni();[cite: 7]
        for (Lezione l : esistenti) {[cite: 7]
            if (l.getGiornoSettimana().equalsIgnoreCase(nuova.getGiornoSettimana())) {[cite: 7]
                boolean sovrapposizioneOraria = nuova.getOraInizio().isBefore(l.getOraFine())[cite: 7]
                        && nuova.getOraFine().isAfter(l.getOraInizio());[cite: 7]
                if (sovrapposizioneOraria) {[cite: 7]
                    if (l.getAula().getNome().equalsIgnoreCase(nuova.getAula().getNome())) {[cite: 7]
                        return false;[cite: 7]
                    }
                    if (l.getInsegnamento().getDocente().getLogin()[cite: 7]
                            .equalsIgnoreCase(nuova.getInsegnamento().getDocente().getLogin())) {[cite: 7]
                        return false;[cite: 7]
                    }
                }
            }
        }
        return true;[cite: 7]
    }

    private boolean verificaVincoliDocente(Lezione nuova) {
        Docente docente = nuova.getInsegnamento().getDocente();[cite: 7]
        if (docente == null) {[cite: 7]
            return true;[cite: 7]
        }
        for (Vincolo v : docente.getVincoli()) {[cite: 7]
            if (v.getGiorno().equalsIgnoreCase(nuova.getGiornoSettimana())) {[cite: 7]
                boolean sovrapposizione = nuova.getOraInizio().isBefore(v.getOraFine())[cite: 7]
                        && nuova.getOraFine().isAfter(v.getOraInizio());[cite: 7]
                if (sovrapposizione) {[cite: 7]
                    return false;[cite: 7]
                }
            }
        }
        return true;[cite: 7]
    }

    public boolean inserisciVincolo(Vincolo v) {
        if (utenteLoggato instanceof Docente) {[cite: 7]
            return lezioneDao.inserisciVincolo(utenteLoggato.getLogin(), v);[cite: 7]
        }
        return false;[cite: 7]
    }

    public boolean richiediSpostamento(SpostamentoLezione spostamento) {
        return lezioneDao.richiediSpostamento(spostamento);[cite: 7]
    }

    public List<SpostamentoLezione> getRichiesteSpostamento() {
        return lezioneDao.getRichiesteSpostamento();[cite: 7]
    }

    public boolean aggiornaStatoSpostamento(SpostamentoLezione s, String stato) {
        return lezioneDao.aggiornaStatoSpostamento(s, stato);[cite: 7]
    }
}
