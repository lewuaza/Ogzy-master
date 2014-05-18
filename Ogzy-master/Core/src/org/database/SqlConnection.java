package org.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.database.installation.DatabaseInstaller;

/**
 * Klasa łącząca się z bazą danych
 * @author Marcin
 */

public class SqlConnection {
    private Connection conn = null;
    private final String db_name = "ogzy";
    
    
    private SqlConnection() {
    }

    private static class SqlConnectionHolder {
        private static final SqlConnection INSTANCE = new SqlConnection();
    }

    /**
     * Zwraca singleton klasy
     *
     * @return obiekt klasy SqlConnection
     */
    public static SqlConnection getInstance() {
        return SqlConnectionHolder.INSTANCE;
    }
    /**
     * Zwraca obiekt klasy Connection potrzebny do wykonania zapytan SQL
     *
     * @return obiekt klasy Connection
     * @throws java.sql.SQLException
     */
    public Connection getSqlConnection() throws SQLException{
         try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+db_name+".db");
            return conn;
         } catch ( ClassNotFoundException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
}

