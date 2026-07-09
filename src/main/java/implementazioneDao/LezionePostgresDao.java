package implementazioneDao;

import dao.LezioneDAO;
import database_connection.ConnessioneDB;
import model.*;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class LezionePostgresDAO implements LezioneDAO {
    private Connection connection;

    public LezionePostgresDAO() {
        try {
            this.connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Lezione> getTutteLeLezioni() {
        List<Lezione> lista = new ArrayList<>();
        String query = "SELECT * FROM lezione l JOIN insegnamento i ON l.insegnamento_id = i.nome JOIN utente u ON i.docente_login = u.login";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Docente d = new Docente(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("email"), resultSet.getString("login"), "");
                Insegnamento ins = new Insegnamento(resultSet.getString("insegnamento_id"), resultSet.getInt("cfu"), resultSet.getString("anno_corso"), d);
                Aula aula = new Aula(resultSet.getString("aula_nome"));
                Lezione lez = new Lezione(ins, resultSet.getString("giorno"), resultSet.getTime("ora_inizio").toLocalTime(), resultSet.getTime("ora_fine").toLocalTime(), aula);
                lista.add(lez);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Lezione> getLezioniPerAnno(String annoCorso) {
        List<Lezione> lista = new ArrayList<>();
        String query = "SELECT * FROM lezione l JOIN insegnamento i ON l.insegnamento_id = i.nome JOIN utente u ON i.docente_login = u.login WHERE i.anno_corso = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, annoCorso);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Docente d = new Docente(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("email"), resultSet.getString("login"), "");
                    Insegnamento ins = new Insegnamento(resultSet.getString("insegnamento_id"), resultSet.getInt("cfu"), resultSet.getString("anno_corso"), d);
                    Aula aula = new Aula(resultSet.getString("aula_nome"));
                    Lezione lez = new Lezione(ins, resultSet.getString("giorno"), resultSet.getTime("ora_inizio").toLocalTime(), resultSet.getTime("ora_fine").toLocalTime(), aula);
                    lista.add(lez);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Lezione> getLezioniPerDocente(String loginDocente) {
        List<Lezione> lista = new ArrayList<>();
        String query = "SELECT * FROM lezione l JOIN insegnamento i ON l.insegnamento_id = i.nome JOIN utente u ON i.docente_login = u.login WHERE i.docente_login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, loginDocente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Docente d = new Docente(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("email"), resultSet.getString("login"), "");
                    Insegnamento ins = new Insegnamento(resultSet.getString("insegnamento_id"), resultSet.getInt("cfu"), resultSet.getString("anno_corso"), d);
                    Aula aula = new Aula(resultSet.getString("aula_nome"));
                    Lezione lez = new Lezione(ins, resultSet.getString("giorno"), resultSet.getTime("ora_inizio").toLocalTime(), resultSet.getTime("ora_fine").toLocalTime(), aula);
                    lista.add(lez);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean inserisciLezione(Lezione lezione) {
        String query = "INSERT INTO lezione (insegnamento_id, giorno, ora_inizio, ora_fine, aula_nome) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lezione.getInsegnamento().getNome());
            statement.setString(2, lezione.getGiornoSettimana());
            statement.setTime(3, Time.valueOf(lezione.getOraInizio()));
            statement.setTime(4, Time.valueOf(lezione.getOraFine()));
            statement.setString(5, lezione.getAula().getNome());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Aula> getAuleDisponibili() {
        List<Aula> lista = new ArrayList<>();
        String query = "SELECT * FROM aula";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                lista.add(new Aula(resultSet.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Insegnamento> getInsegnamentiAttivi() {
        List<Insegnamento> lista = new ArrayList<>();
        String query = "SELECT * FROM insegnamento i JOIN utente u ON i.docente_login = u.login";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Docente d = new Docente(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("email"), resultSet.getString("login"), "");
                lista.add(new Insegnamento(resultSet.getString("nome"), resultSet.getInt("cfu"), resultSet.getString("anno_corso"), d));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean inserisciVincolo(String loginDocente, Vincolo vincolo) {
        String query = "INSERT INTO vincolo (docente_login, giorno, ora_inizio, ora_fine) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, loginDocente);
            statement.setString(2, vincolo.getGiorno());
            statement.setTime(3, Time.valueOf(vincolo.getOraInizio()));
            statement.setTime(4, Time.valueOf(vincolo.getOraFine()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean richiediSpostamento(SpostamentoLezione spostamento) {
        String query = "INSERT INTO spostamento (insegnamento_id, giorno_corrente, nuovo_giorno, nuova_ora_inizio, nuova_ora_fine, stato) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, spostamento.getLezione().getInsegnamento().getNome());
            statement.setString(2, spostamento.getLezione().getGiornoSettimana());
            statement.setString(3, spostamento.getNuovoGiorno());
            statement.setTime(4, Time.valueOf(spostamento.getNuovaOraInizio()));
            statement.setTime(5, Time.valueOf(spostamento.getNuovaOraFine()));
            statement.setString(6, spostamento.getStato());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 @Override
    public boolean richiediSpostamento(SpostamentoLezione spostamento) {
        String query = "INSERT INTO spostamento (insegnamento_id, giorno_corrente, nuovo_giorno, nuova_ora_inizio, nuova_ora_fine, stato) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, spostamento.getLezione().getInsegnamento().getNome());
            statement.setString(2, spostamento.getLezione().getGiornoSettimana());
            statement.setString(3, spostamento.getNuovoGiorno());
            statement.setTime(4, Time.valueOf(spostamento.getNuovaOraInizio()));
            statement.setTime(5, Time.valueOf(spostamento.getNuovaOraFine()));
            statement.setString(6, spostamento.getStato());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SpostamentoLezione> getRichiesteSpostamento() {
        List<SpostamentoLezione> lista = new ArrayList<>();
        String query = "SELECT * FROM spostamento s " +
                       "JOIN insegnamento i ON s.insegnamento_id = i.nome " +
                       "JOIN utente u ON i.docente_login = u.login";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Docente d = new Docente(
                    resultSet.getString("nome"), 
                    resultSet.getString("cognome"), 
                    resultSet.getString("email"), 
                    resultSet.getString("login"), 
                    ""
                );
                Insegnamento ins = new Insegnamento(
                    resultSet.getString("insegnamento_id"), 
                    resultSet.getInt("cfu"), 
                    resultSet.getString("anno_corso"), 
                    d
                );
                Lezione l = new Lezione(
                    ins, 
                    resultSet.getString("giorno_corrente"), 
                    LocalTime.of(0,0), 
                    LocalTime.of(0,0), 
                    new Aula("")
                );
                SpostamentoLezione sl = new SpostamentoLezione(
                    l,
                    resultSet.getString("nuovo_giorno"),
                    resultSet.getTime("nuova_ora_inizio").toLocalTime(),
                    resultSet.getTime("nuova_ora_fine").toLocalTime()
                );
                sl.setStato(resultSet.getString("stato"));
                lista.add(sl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean aggiornaStatoSpostamento(SpostamentoLezione spostamento, String stato) {
<!-- ... existing code ... -->
    
    @Override
    public List<SpostamentoLezione> getRichiesteSpostamento() {
        List<SpostamentoLezione> lista = new ArrayList<>();
        return lista;
    }

    @Override
    public boolean aggiornaStatoSpostamento(SpostamentoLezione spostamento, String stato) {
        String query = "UPDATE spostamento SET stato = ? WHERE insegnamento_id = ? AND giorno_corrente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stato);
            statement.setString(2, spostamento.getLezione().getInsegnamento().getNome());
            statement.setString(3, spostamento.getLezione().getGiornoSettimana());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
