package implementazioneDao;

import dao.UtenteDAO;
import database_connection.ConnessioneDB;
import model.Utente;
import model.Studente;
import model.Docente;
import java.sql.*;

public class UtentePostgresDAO implements UtenteDAO {
    private Connection connection;

    public UtentePostgresDAO() {
        try {
            this.connection = ConnessioneDB.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utente login(String login, String password) {
        String query = "SELECT * FROM utente WHERE login = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String tipo = resultSet.getString("tipo");
                    String nome = resultSet.getString("nome");
                    String cognome = resultSet.getString("cognome");
                    String email = resultSet.getString("email");
                    if ("STUDENTE".equals(tipo)) {
                        return new Studente(nome, cognome, email, login, password, resultSet.getString("matricola"));
                    } else if ("DOCENTE".equals(tipo)) {
                        return new Docente(nome, cognome, email, login, password);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registraStudente(Studente studente) {
        String query = "INSERT INTO utente (nome, cognome, email, login, password, matricola, tipo) VALUES (?, ?, ?, ?, ?, ?, 'STUDENTE')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studente.getNome());
            statement.setString(2, studente.getCognome());
            statement.setString(3, studente.getEmail());
            statement.setString(4, studente.getLogin());
            statement.setString(5, studente.getPassword());
            statement.setString(6, studente.getMatricola());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean registraDocente(Docente docente) {
        String query = "INSERT INTO utente (nome, cognome, email, login, password, tipo) VALUES (?, ?, ?, ?, ?, 'DOCENTE')";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, docente.getNome());
            statement.setString(2, docente.getCognome());
            statement.setString(3, docente.getEmail());
            statement.setString(4, docente.getLogin());
            statement.setString(5, docente.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
