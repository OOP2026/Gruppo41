package controller;

import dao.LezioneDAO;
import dao.UtenteDAO;
import implementazioneDao.LezionePostgresDAO;
import implementazioneDao.UtentePostgresDAO;
import model.Lezione;
import model.Utente;
import model.Vincolo;
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
        System.out.println("Applicazione avviata. In attesa di interazione con la GUI.");
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
