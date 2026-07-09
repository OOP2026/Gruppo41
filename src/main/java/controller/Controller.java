package controller;

import dao.LezioneDAO;
import dao.UtenteDAO;
import implementazioneDao.LezionePostgresDAO;
import implementazioneDao.UtentePostgresDAO;
import model.*;
import gui.*;
import java.util.List;

public class Controller {

    private List<Lezione> lezioni;
    private UtenteDAO utenteDao;
    private LezioneDAO lezioneDao;
    private Utente utenteLoggato;

    public Controller() {
        this.utenteDao = new UtentePostgresDAO();
        this.lezioneDao = new LezionePostgresDAO();
        this.lezioni = lezioneDao.getTutteLeLezioni();
    }

    public void avviaApplicazione() {
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
    }

    public void mostraInterfacciaUtente() {
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

    public boolean login(String login, String password) {
        Utente utente = utenteDao.login(login, password);
        if (utente != null) {
            this.utenteLoggato = utente;
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
        this.lezioni = lezioneDao.getTutteLeLezioni();
        return lezioni;
    }

    public boolean aggiungiLezione(Lezione nuova) {
        if (verificaConflitti(nuova) || verificaVincoliDocente(nuova)) {
            return false;
        }
        if (lezioneDao.inserisciLezione(nuova)) {
            lezioni.add(nuova);
            return true;
        }
        return false;
    }

    public boolean aggiungiVincolo(Vincolo v) {
        if (utenteLoggato instanceof Docente) {
            Docente d = (Docente) utenteLoggato;
            if (d.aggiungiVincolo(v)) {
                return lezioneDao.inserisciVincolo(d.getLogin(), v);
            }
        }
        return false;
    }

    public boolean richiediSpostamento(SpostamentoLezione s) {
        return lezioneDao.richiediSpostamento(s);
    }

    public List<SpostamentoLezione> getRichiesteSpostamento() {
        return lezioneDao.getRichiesteSpostamento();
    }

    public boolean aggiornaStatoSpostamento(SpostamentoLezione s, String stato) {
        return lezioneDao.aggiornaStatoSpostamento(s, stato);
    }

    private boolean verificaConflitti(Lezione nuova) {
        for (Lezione l : lezioni) {
            boolean stessaAula = l.getAula().equals(nuova.getAula());
            boolean stessoDocente = l.getInsegnamento().getDocente().equals(nuova.getInsegnamento().getDocente());
            boolean stessoGiorno = l.getGiornoSettimana().equals(nuova.getGiornoSettimana());
            boolean conflittoOrario = nuova.getOraInizio().isBefore(l.getOraFine()) && nuova.getOraFine().isAfter(l.getOraInizio());

            if ((stessaAula || stessoDocente) && stessoGiorno && conflittoOrario) {
                return true;
            }
        }
        return false;
    }

    private boolean verificaVincoliDocente(Lezione nuova) {
        if (nuova.getInsegnamento().getDocente().getVincoli() != null) {
            for (Vincolo v : nuova.getInsegnamento().getDocente().getVincoli()) {
                boolean stessoGiorno = v.getGiorno().equals(nuova.getGiornoSettimana());
                boolean conflittoOrario = nuova.getOraInizio().isBefore(v.getOraFine()) && nuova.getOraFine().isAfter(v.getOraInizio());
                if (stessoGiorno && conflittoOrario) {
                    return true;
                }
            }
        }
        return false;
    }
}
