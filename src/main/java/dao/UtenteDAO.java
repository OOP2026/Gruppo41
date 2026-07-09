package dao;

import model.Utente;
import model.Studente;
import model.Docente;

public interface UtenteDAO {
    Utente login(String login, String password);
    boolean registraStudente(Studente studente);
    boolean registraDocente(Docente docente);
}
