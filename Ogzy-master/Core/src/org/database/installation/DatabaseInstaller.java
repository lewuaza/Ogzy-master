package org.database.installation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.SqlConnection;

/**
 *
 * @author Mariushrek
 * @edited Marcin
 *
 * Klasa umozliwiajaca utworzenie calej bazy danych od poczatku
 */
public class DatabaseInstaller {

    private Statement stat = null;
    private Connection conn = null;

    public boolean execute() {
        boolean result = createStudentTable()
                && createStudentTable()
                && createGrupaOcenTable()
                && createPrzedmiotyTable()
                && createGrupaCwiczeniowaTable()
                && createGrupaStudentTable()
                && createTerminyTable()
                && createObecnoscTable()
                && createOcenyTable()
                && buildMailer();
        System.out.println(result);
        return result;
    }

    private boolean createTable(String query, String successMessage) {
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            stat = conn.createStatement();
            stat.execute(query);
            stat.close();
            System.out.println(successMessage);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseInstaller.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // Logger.getLogger(DatabaseInstaller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private boolean createOcenyTable() {
        return createTable("CREATE TABLE IF NOT EXISTS oceny( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_grupa_ocen INTEGER,"
                + " id_grupa_cwiczeniowa INTEGER,"
                + " id_student INTEGER,"
                + " wartosc_oceny REAL,"
                + " FOREIGN KEY(id_grupa_ocen ) REFERENCES grupa_ocen(id),"
                + " FOREIGN KEY(id_grupa_cwiczeniowa ) REFERENCES grupa_cwiczeniowa(id),"
                + " FOREIGN KEY(id_student ) REFERENCES student(id))", "Tabela 'oceny' utworzona pomyslnie");
    }

    private boolean createObecnoscTable() {
        return createTable("CREATE TABLE IF NOT EXISTS obecnosc( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_termin INTEGER,"
                + " id_student INTEGER,"
                + " data TEXT,"
                + " obecnosc INTEGER,"
                + " FOREIGN KEY(id_termin ) REFERENCES grupa_terminy(id),"
                + " FOREIGN KEY(id_student ) REFERENCES student(id))", "Tabela 'obecnosc' utworzona pomyslnie");
    }

    private boolean createTerminyTable() {
        return createTable("CREATE TABLE IF NOT EXISTS terminy( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_grupa_cwiczeniowa INTEGER,"
                + " dzien_tygodnia TEXT,"
                + " godzina_start TEXT,"
                + " godzina_stop TEXT,"
                + " FOREIGN KEY(id_grupa_cwiczeniowa ) REFERENCES grupa_cwiczeniowa(id))", "Tabela 'terminy' utworzona pomyslnie");
    }

    private boolean createGrupaStudentTable() {
        return createTable("CREATE TABLE IF NOT EXISTS grupa_student( "
                + " id_student INTEGER,"
                + " id_grupa_cwiczeniowa INTEGER,"
                + " FOREIGN KEY(id_student) REFERENCES student(id),"
                + " FOREIGN KEY(id_grupa_cwiczeniowa) REFERENCES grupa_cwiczeniowa(id))", "Tabela 'grupa_student' utworzona pomyslnie");
    }

    private boolean createGrupaCwiczeniowaTable() {
        return createTable("CREATE TABLE IF NOT EXISTS grupa_cwiczeniowa( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_przedmiot INTEGER,"
                + " nazwa TEXT,"
                + " FOREIGN KEY(id_przedmiot) REFERENCES przedmioty(id))", "Tabela 'grupa_cwiczeniowa' utworzona pomyslnie");
    }

    private boolean createPrzedmiotyTable() {
        return createTable("CREATE TABLE IF NOT EXISTS przedmioty( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " id_grupa_ocen INTEGER,"
                + " nazwa TEXT,"
                + " typ_oceniania TEXT,"
                + " rok_akademicki_start INTEGER,"
                + " semestr TEXT,"
                + " FOREIGN KEY(id_grupa_ocen) REFERENCES grupa_ocen(id))", "Tabela 'przedmioty' utworzona pomyslnie");
    }

    private boolean createGrupaOcenTable() {
        return createTable("CREATE TABLE IF NOT EXISTS grupa_ocen( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " sub_id INTEGER,"
                + " nazwa TEXT,"
                + " waga REAL,"
                + " FOREIGN KEY(sub_id) REFERENCES grupa_ocen(id))", "Tabela 'grupa_ocen' utworzona pomyslnie");
    }

    private boolean createStudentTable() {
        return createTable("CREATE TABLE IF NOT EXISTS student( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " imie TEXT,"
                + " nazwisko TEXT,"
                + " email TEXT,"
                + " indeks INTEGER)", "Tabela 'student' utworzona pomyslnie");
    }
    
    public boolean buildMailer()
    {
        //createTable("DROP TABLE IF EXISTS mailer_mails", "");
        return createMailerConfigurationTable() && createRecivedMailsTable();
    }
    private boolean createMailerConfigurationTable()
    {
        return createTable("CREATE TABLE IF NOT EXISTS mailer_settings( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " name TEXT,"
                + " login TEXT,"
                + " password TEXT,"
                + " pop3Hostname TEXT,"
                + " pop3Port TEXT,"
                + " smptHostname TEXT,"
                + " smptPort TEXT)", "Tabela 'mailer_settings' utworzona pomyslnie");
    }
    private boolean createRecivedMailsTable()
    {
        return createTable("CREATE TABLE IF NOT EXISTS mailer_mails( "
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"                    
                + " folderName TEXT,"
                + " recived_date TEXT,"
                + " content TEXT,"
                + " [from] TEXT,"
                + " subject TEXT,"
                + " isRead INTEGER,"
                + " accountID INTEGER)", "Tabela 'mailer_mails' utworzona pomyslnie");
    }
    
}
