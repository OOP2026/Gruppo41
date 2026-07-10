package controller;

import dao.UtenteDAO;
import dao.LezioneDAO;
import implementazioneDao.UtentePostgresDAO;
import implementazioneDao.LezionePostgresDAO;
import model.*;
import gui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Lezione> lezioni;
    private UtenteDAO utenteDao;
    private LezioneDAO lezioneDao;
    private Utente utenteLoggato;

    public Controller() {
        this.utenteDao = new UtentePostgresDAO();
        this.lezioneDao = new LezionePostgresDAO();
        this.lezioni = new ArrayList<>();
    }

    public void avviaApplicazione() {
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    public boolean login(String username, String password) {
        Utente u = utenteDao.login(username, password);
        if (u != null) {
            this.utenteLoggato = u;
            return true;
        }
        return false;
    }

    public void mostraInterfacciaUtente() {
        if (utenteLoggato == null) return;

        if (utenteLoggato instanceof Coordinatore) {
            new CoordinatoreFrame(this).setVisible(true);
        } else if (utenteLoggato instanceof ResponsabileOrario) {
            new ResponsabileOrarioFrame(this).setVisible(true);
        } else if (utenteLoggato instanceof Docente) {
            new DocenteFrame(this).setVisible(true);
        } else if (utenteLoggato instanceof Studente) {
            new StudenteFrame(this).setVisible(true);
        }
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

    public List<Insegnamento> getInsegnamentiAttivi() {
        return lezioneDao.getInsegnamentiAttivi();
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
                boolean sovrapposizioneOraria = nuova.getOraInizio().isBefore(l.getOraFine()) && nuova.getOraFine().isAfter(l.getOraInizio());
                if (sovrapposizioneOraria) {
                    if (l.getAula().getNome().equalsIgnoreCase(nuova.getAula().getNome())) {
                        return false; 
                    }
                    if (l.getInsegnamento().getDocente().getLogin().equalsIgnoreCase(nuova.getInsegnamento().getDocente().getLogin())) {
                        return false; 
                    }
                }
            }
        }
        return true;
    }

    private boolean verificaVincoliDocente(Lezione nuova) {
        return true; 
    }

    public boolean inserisciVincolo(Vincolo v) {
        if (utenteLoggato instanceof Docente) {
            return lezioneDao.inserisciVincolo(utenteLoggato.getLogin(), v);
        }
        return false;
    }

    public boolean aggiungiVincolo(Vincolo v) {
        return inserisciVincolo(v);
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
