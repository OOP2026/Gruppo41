package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
    
    private static ConnessioneDatabase instance;
    private Connection connection;
    
    private final String url = "jdbc:postgresql://localhost:5432/nome_tuo_db";
    private final String user = "postgres";
    private final String password = "la_tua_password_di_postgres";

    private ConnessioneDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connessione al database stabilita con successo!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trovato!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database!");
            e.printStackTrace();
        }
    }

    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
