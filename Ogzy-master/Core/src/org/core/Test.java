package org.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.database.SqlConnection;

/**
 *
 * @author zzzmkp01
 */
public class Test {

    public void test() {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement stat = conn.createStatement();
            stat.execute("CREATE TABLE IF NOT EXISTS test (id INTEGER PRIMARY KEY AUTOINCREMENT, imie varchar(255))");
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO test VALUES(NULL, ?);");
            prepStmt.setString(1, "text");
            prepStmt.execute();

            prepStmt = conn.prepareStatement(
                    "INSERT INTO test VALUES(NULL, ?);");
            prepStmt.setString(1, "text2");
            prepStmt.execute();
            System.out.println("Poszlo to pozytywnie");
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
    }

    public String get_test() {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM test WHERE id='1'");
            String imie = result.getString("imie");
            return imie;
        } catch (SQLException e) {
            e.printStackTrace();
            return "get_test error";
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
    }
}
