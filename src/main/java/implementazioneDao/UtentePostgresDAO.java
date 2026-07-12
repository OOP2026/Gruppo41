package implementazioneDao;

import dao.UtenteDAO;
import database_connection.ConnessioneDatabase;
import model.Coordinatore;
import model.Docente;
import model.ResponsabileOrario;
import model.Studente;
import model.Utente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtentePostgresDAO implements UtenteDAO {

    private final Connection connection;

    public UtentePostgresDAO() {
        try {
            this.connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("Impossibile connettersi al database", e);
        }
    }

    @Override
    public Utente login(String login, String password) {
        String query = "SELECT * FROM utente WHERE login = ? AND password = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, hashPassword(password));

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mappaUtente(rs, login);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login dell'utente", e);
        }
    }

    private Utente mappaUtente(ResultSet rs, String login) throws SQLException {
        String tipo = rs.getString("tipo");
        String nome = rs.getString("nome");
        String cognome = rs.getString("cognome");
        String email = rs.getString("email");
        String passwordHash = rs.getString("password");

        switch (tipo) {
            case "STUDENTE":
                return new Studente(nome, cognome, email, login, passwordHash,
                        rs.getString("matricola"), rs.getString("anno_corso"));
            case "DOCENTE":
                return new Docente(nome, cognome, email, login, passwordHash);
            case "RESPONSABILE":
                return new ResponsabileOrario(nome, cognome, email, login, passwordHash);
            case "COORDINATORE":
                return new Coordinatore(nome, cognome, email, login, passwordHash);
            default:
                return null;
        }
    }

    @Override
    public boolean registraStudente(Studente studente) {
        String query = "INSERT INTO utente (nome, cognome, email, login, password, tipo, matricola, anno_corso) "
                + "VALUES (?, ?, ?, ?, ?, 'STUDENTE', ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studente.getNome());
            statement.setString(2, studente.getCognome());
            statement.setString(3, studente.getEmail());
            statement.setString(4, studente.getLogin());
            statement.setString(5, hashPassword(studente.getPassword()));
            statement.setString(6, studente.getMatricola());
            statement.setString(7, studente.getAnnoCorso());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la registrazione dello studente", e);
        }
    }

    @Override
    public boolean registraDocente(Docente docente) {
        String query = "INSERT INTO utente (nome, cognome, email, login, password, tipo) "
                + "VALUES (?, ?, ?, ?, ?, 'DOCENTE')";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, docente.getNome());
            statement.setString(2, docente.getCognome());
            statement.setString(3, docente.getEmail());
            statement.setString(4, docente.getLogin());
            statement.setString(5, hashPassword(docente.getPassword()));
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la registrazione del docente", e);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo di hashing non disponibile", e);
        }
    }
}
