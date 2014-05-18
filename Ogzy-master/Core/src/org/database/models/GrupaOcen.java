package org.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.SqlConnection;

/**
 *
 * @author Mariushrek
 */
/**
 * Pytanie - Czy sub_id powinnismy zostawic jako integer, czy raczej zamienic go
 * na obiekt klasy GrupaOcen??
 */
public class GrupaOcen {

    private int id;
    private int sub_id; // Foreignkey => GrupaOcen
    private String nazwa;
    private float waga;

    public GrupaOcen(int id, int sub_id, String nazwa, float waga) {
        this.id = id;
        this.sub_id = sub_id;
        this.nazwa = nazwa;
        this.waga = waga;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the sub_id
     */
    public int getSub_id() {
        return sub_id;
    }

    /**
     * @param sub_id the sub_id to set
     */
    public void setSub_id(int sub_id) {
        this.sub_id = sub_id;
    }

    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @param nazwa the nazwa to set
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * @return the waga
     */
    public float getWaga() {
        return waga;
    }

    /**
     * @param waga the waga to set
     */
    public void setWaga(float waga) {
        this.waga = waga;
    }

    public static GrupaOcen getGrupaOcen(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT id,sub_id,nazwa,waga FROM grupa_ocen WHERE id=" + id);

            GrupaOcen gr = null;
            if (rs.next()) {
                gr = new GrupaOcen(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getFloat(4));
            }
            st.close();
            return gr;
        } catch (SQLException ex) {
            Logger.getLogger(GrupaOcen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return null;
    }

    public static List<GrupaOcen> getAllGrupaOcen(int sub_id) {
        List<GrupaOcen> grupa = new ArrayList<GrupaOcen>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT id,sub_id,nazwa,waga FROM grupa_ocen WHERE sub_id=" + sub_id + " ORDER BY id DESC");

            while (rs.next()) {
                grupa.add(new GrupaOcen(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getFloat(4)));
            }
            st.close();
            return grupa;

        } catch (SQLException ex) {
            Logger.getLogger(GrupaOcen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.nazwa;
    }

    public static int addGrupaOcen(int sub_id, String nazwa, float waga) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO grupa_ocen(sub_id, nazwa, waga) VALUES (?, ?, ?)");
            prepStmt.setInt(1, sub_id);
            prepStmt.setString(2, nazwa);
            prepStmt.setFloat(3, waga);
            prepStmt.execute();

            prepStmt = conn.prepareStatement("SELECT last_insert_rowid();");
            return prepStmt.executeQuery().getInt(1);
        } catch (SQLException ex) {
            System.err.println("Klasa GrupaOcen, Metoda addGrupaOcen(int, String, float) - problem z dodaniem elementu");
            Logger.getLogger(Oceny.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
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

    public static void deleteGrupaOcen(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM grupa_ocen WHERE id=" + id + " OR sub_id=" + id);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GrupaOcen.class.getName()).log(Level.SEVERE, null, ex);
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

    public GrupaOcen[] toArray() {

        return null;
    }
}
