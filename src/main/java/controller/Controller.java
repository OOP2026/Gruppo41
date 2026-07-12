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

    private List<Lezione> lezioni;
    private final UtenteDAO utenteDao;
    private final LezioneDAO lezioneDao;
    private Utente utenteLoggato;
    private AppLauncher launcher;

    public Controller() {
        this.utenteDao = new UtentePostgresDAO();
        this.lezioneDao = new LezionePostgresDAO();
        this.lezioni = new ArrayList<>();
    }

    public void setLauncher(AppLauncher launcher) {
        this.launcher = launcher;
    }

    public void avviaApplicazione() {
        if (launcher != null) {
            launcher.launch();
        }
    }

    public boolean login(String username, String password) {
        Utente u = utenteDao.login(username, password);
        if (u != null) {
            this.utenteLoggato = u;
            return true;
        }
        return false;
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public void logout() {
        this.utenteLoggato = null;
    }

    public List<Lezione> getLezioni() {
        try {
            this.lezioni = lezioneDao.getTutteLeLezioni();
        } catch (Exception e) {
            System.err.println("Errore durante il recupero delle lezioni dal database: " + e.getMessage());
            e.printStackTrace();
        }
        return lezioni;
    }

    public List<Lezione> getLezioniPerAnno(String anno) {
        return lezioneDao.getLezioniPerAnno(anno);
    }

    public List<Lezione> getLezioniPerDocente(String login) {
        return lezioneDao.getLezioniPerDocente(login);
    }

    public List<Aula> getAuleDisponibili() {
        return lezioneDao.getAuleDisponibili();
    }

    public boolean inserisciAula(Aula aula) {
        return lezioneDao.inserisciAula(aula);
    }

    public List<Insegnamento> getInsegnamentiAttivi() {
        return lezioneDao.getInsegnamentiAttivi();
    }

    public boolean inserisciInsegnamento(Insegnamento insegnamento) {
        return lezioneDao.inserisciInsegnamento(insegnamento);
    }

    public boolean aggiungiLezione(Lezione lezione) {
        if (verificaConflitti(lezione) && verificaVincoliDocente(lezione)) {
            return lezioneDao.inserisciLezione(lezione);
        }
        return false;
    }

    private boolean verificaConflitti(Lezione nuova) {
        List<Lezione> esistenti = getLezioni();
        for (Lezione l : esistenti) {
            if (l.getGiornoSettimana().equalsIgnoreCase(nuova.getGiornoSettimana())) {
                boolean sovrapposizioneOraria = nuova.getOraInizio().isBefore(l.getOraFine())
                        && nuova.getOraFine().isAfter(l.getOraInizio());
                if (sovrapposizioneOraria) {
                    if (l.getAula().getNome().equalsIgnoreCase(nuova.getAula().getNome())) {
                        return false;
                    }
                    if (l.getInsegnamento().getDocente().getLogin()
                            .equalsIgnoreCase(nuova.getInsegnamento().getDocente().getLogin())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean verificaVincoliDocente(Lezione nuova) {
        Docente docente = nuova.getInsegnamento().getDocente();
        if (docente == null) {
            return true;
        }
        for (Vincolo v : docente.getVincoli()) {
            if (v.getGiorno().equalsIgnoreCase(nuova.getGiornoSettimana())) {
                boolean sovrapposizione = nuova.getOraInizio().isBefore(v.getOraFine())
                        && nuova.getOraFine().isAfter(v.getOraInizio());
                if (sovrapposizione) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean inserisciVincolo(Vincolo v) {
        if (utenteLoggato instanceof Docente) {
            return lezioneDao.inserisciVincolo(utenteLoggato.getLogin(), v);
        }
        return false;
    }

    public boolean richiediSpostamento(SpostamentoLezione spostamento) {
        return lezioneDao.richiediSpostamento(spostamento);
    }

    public List<SpostamentoLezione> getRichiesteSpostamento() {
        return lezioneDao.getRichiesteSpostamento();
    }

    public boolean aggiornaStatoSpostamento(SpostamentoLezione s, String stato) {
        return lezioneDao.aggiornaStatoSpostamento(s, stato);
    }
}
