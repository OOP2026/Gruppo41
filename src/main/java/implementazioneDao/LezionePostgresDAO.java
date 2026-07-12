package implementazioneDao;

import dao.LezioneDAO;
import database_connection.ConnessioneDatabase;
import model.Aula;
import model.Docente;
import model.Insegnamento;
import model.Lezione;
import model.SpostamentoLezione;
import model.Vincolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class LezionePostgresDAO implements LezioneDAO {

    private static final String SELECT_LEZIONE_BASE =
            "SELECT l.giorno AS l_giorno, l.ora_inizio AS l_ora_inizio, l.ora_fine AS l_ora_fine, "
                    + "l.aula_nome AS l_aula_nome, "
                    + "i.nome AS i_nome, i.cfu AS i_cfu, i.anno_corso AS i_anno_corso, "
                    + "u.nome AS u_nome, u.cognome AS u_cognome, u.email AS u_email, u.login AS u_login, u.password AS u_password "
                    + "FROM lezione l "
                    + "JOIN insegnamento i ON l.insegnamento_id = i.nome "
                    + "JOIN utente u ON i.docente_login = u.login";

    private final Connection connection;

    public LezionePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inizializzazione della connessione al database", e);
        }
    }

    private Lezione mappaLezione(ResultSet rs) throws SQLException {
        Docente docente = new Docente(rs.getString("u_nome"), rs.getString("u_cognome"),
                rs.getString("u_email"), rs.getString("u_login"), rs.getString("u_password"));
        Insegnamento insegnamento = new Insegnamento(rs.getString("i_nome"), rs.getInt("i_cfu"),
                rs.getString("i_anno_corso"), docente);
        Aula aula = new Aula(rs.getString("l_aula_nome"));
        return new Lezione(insegnamento, rs.getString("l_giorno"),
                rs.getTime("l_ora_inizio").toLocalTime(), rs.getTime("l_ora_fine").toLocalTime(), aula);
    }

    @Override
    public List<Lezione> getTutteLeLezioni() {
        List<Lezione> lista = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_LEZIONE_BASE);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                lista.add(mappaLezione(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero di tutte le lezioni", e);
        }
        return lista;
    }

    @Override
    public List<Lezione> getLezioniPerAnno(String annoCorso) {
        List<Lezione> lista = new ArrayList<>();
        String query = SELECT_LEZIONE_BASE + " WHERE i.anno_corso = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, annoCorso);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(mappaLezione(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero delle lezioni per anno corso", e);
        }
        return lista;
    }

    @Override
    public List<Lezione> getLezioniPerDocente(String loginDocente) {
        List<Lezione> lista = new ArrayList<>();
        String query = SELECT_LEZIONE_BASE + " WHERE i.docente_login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, loginDocente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(mappaLezione(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero delle lezioni del docente", e);
        }
        return lista;
    }

    @Override
    public boolean inserisciLezione(Lezione lezione) {
        String query = "INSERT INTO lezione (insegnamento_id, giorno, ora_inizio, ora_fine, aula_nome) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, lezione.getInsegnamento().getNome());
            statement.setString(2, lezione.getGiornoSettimana());
            statement.setTime(3, Time.valueOf(lezione.getOraInizio()));
            statement.setTime(4, Time.valueOf(lezione.getOraFine()));
            statement.setString(5, lezione.getAula().getNome());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento della lezione", e);
        }
    }

    @Override
    public List<Aula> getAuleDisponibili() {
        List<Aula> lista = new ArrayList<>();
        String query = "SELECT nome FROM aula";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                lista.add(new Aula(resultSet.getString("nome")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero delle aule disponibili", e);
        }
        return lista;
    }

    @Override
    public boolean inserisciAula(Aula aula) {
        String query = "INSERT INTO aula (nome) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, aula.getNome());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento dell'aula", e);
        }
    }

    @Override
    public List<Insegnamento> getInsegnamentiAttivi() {
        List<Insegnamento> lista = new ArrayList<>();
        String query = "SELECT i.nome AS i_nome, i.cfu AS i_cfu, i.anno_corso AS i_anno_corso, "
                + "u.nome AS u_nome, u.cognome AS u_cognome, u.email AS u_email, u.login AS u_login, u.password AS u_password "
                + "FROM insegnamento i JOIN utente u ON i.docente_login = u.login";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Docente docente = new Docente(resultSet.getString("u_nome"), resultSet.getString("u_cognome"),
                        resultSet.getString("u_email"), resultSet.getString("u_login"), resultSet.getString("u_password"));
                lista.add(new Insegnamento(resultSet.getString("i_nome"), resultSet.getInt("i_cfu"),
                        resultSet.getString("i_anno_corso"), docente));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero degli insegnamenti attivi", e);
        }
        return lista;
    }

    @Override
    public boolean inserisciInsegnamento(Insegnamento insegnamento) {
        String query = "INSERT INTO insegnamento (nome, cfu, anno_corso, docente_login) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, insegnamento.getNome());
            statement.setInt(2, insegnamento.getCfu());
            statement.setString(3, insegnamento.getAnnoCorso());
            statement.setString(4, insegnamento.getDocente().getLogin());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento dell'insegnamento", e);
        }
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
            throw new RuntimeException("Errore durante l'inserimento del vincolo", e);
        }
    }

    @Override
    public boolean richiediSpostamento(SpostamentoLezione spostamento) {
        String query = "INSERT INTO spostamento (insegnamento_id, giorno_corrente, nuovo_giorno, "
                + "nuova_ora_inizio, nuova_ora_fine, stato) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, spostamento.getLezione().getInsegnamento().getNome());
            statement.setString(2, spostamento.getLezione().getGiornoSettimana());
            statement.setString(3, spostamento.getNuovoGiorno());
            statement.setTime(4, Time.valueOf(spostamento.getNuovaOraInizio()));
            statement.setTime(5, Time.valueOf(spostamento.getNuovaOraFine()));
            statement.setString(6, spostamento.getStato());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la richiesta di spostamento", e);
        }
    }

    @Override
    public List<SpostamentoLezione> getRichiesteSpostamento() {
        List<SpostamentoLezione> lista = new ArrayList<>();
        String query = SELECT_LEZIONE_BASE.replace("l.giorno AS l_giorno", "s.giorno_corrente AS l_giorno")
                + ", s.nuovo_giorno AS s_nuovo_giorno, s.nuova_ora_inizio AS s_nuova_ora_inizio, "
                + "s.nuova_ora_fine AS s_nuova_ora_fine, s.stato AS s_stato "
                + " JOIN spostamento s ON s.insegnamento_id = i.nome AND s.giorno_corrente = l.giorno";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Lezione lezione = mappaLezione(resultSet);
                SpostamentoLezione spostamento = new SpostamentoLezione(
                        lezione,
                        resultSet.getString("s_nuovo_giorno"),
                        resultSet.getTime("s_nuova_ora_inizio").toLocalTime(),
                        resultSet.getTime("s_nuova_ora_fine").toLocalTime()
                );
                spostamento.setStato(resultSet.getString("s_stato"));
                lista.add(spostamento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero delle richieste di spostamento", e);
        }
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
            throw new RuntimeException("Errore durante l'aggiornamento dello stato di spostamento", e);
        }
    }
}
