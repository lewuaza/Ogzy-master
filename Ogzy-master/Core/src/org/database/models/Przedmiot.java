package org.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.SqlConnection;

/**
 *
 * @author Mariushrek
 */
public class Przedmiot {

    private int id; // PrimaryID, AI
    private String nazwa;
    private GrupaOcen grupaOcen;
    private TYP_OCENIANIA typ_oceniania;
    private int rok_akademicki_start;
    private SEMESTR semestr;

    public Przedmiot(int id, String nazwa, GrupaOcen grupaOcen, TYP_OCENIANIA typ_oceniania, int rok_akademicki_start, SEMESTR semestr) {
        this.id = id;
        this.nazwa = nazwa;
        this.grupaOcen = grupaOcen;
        this.typ_oceniania = typ_oceniania;
        this.rok_akademicki_start = rok_akademicki_start;
        this.semestr = semestr;
    }

    public int getId() {
        return this.id;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public GrupaOcen getGrupaOcen() {
        return this.grupaOcen;
    }

    public TYP_OCENIANIA getTypOceniania() {
        return this.typ_oceniania;
    }

    public int getRokAkademicki() {
        return this.rok_akademicki_start;
    }

    public SEMESTR getSemestr() {
        return this.semestr;
    }

    public void setId(int f_id) {
        this.id = f_id;
    }

    public void setNazwa(String f_nazwa) {
        this.nazwa = f_nazwa;
    }

    public void setGrupaOcen(GrupaOcen f_g) {
        this.grupaOcen = f_g;
    }

    public void setTypOceniania(TYP_OCENIANIA f_typ) {
        this.typ_oceniania = f_typ;
    }

    public void setRokAkademicki(int f_rok) {
        this.rok_akademicki_start = f_rok;
    }

    public void setSemestr(SEMESTR f_sem) {
        this.semestr = f_sem;
    }

    public static List<Przedmiot> getAllPrzedmioty() {
        List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT P.id AS p_id, P.nazwa AS p_nazwa, G.id AS g_id, G.sub_id AS g_sub_id, G.nazwa AS g_nazwa, G.waga AS g_waga, P.typ_oceniania AS p_typ_oceniania, P.rok_akademicki_start AS p_rok_akademicki, P.semestr AS p_semestr FROM przedmioty as P join grupa_ocen as G on P.id_grupa_ocen=G.id ORDER BY P.rok_akademicki_start DESC, P.id ASC");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Przedmiot jeden_przedmiot = new Przedmiot(resultSet.getInt("p_id"), resultSet.getString("p_nazwa"),
                        new GrupaOcen(resultSet.getInt("g_id"), resultSet.getInt("g_sub_id"), resultSet.getString("g_nazwa"), resultSet.getFloat("g_waga")),
                        TYP_OCENIANIA.values()[resultSet.getInt("p_typ_oceniania")], resultSet.getInt("p_rok_akademicki"), SEMESTR.values()[resultSet.getInt("p_semestr")]);

                przedmioty.add(jeden_przedmiot);
            }
            return przedmioty;
        } catch (SQLException ex) {
            Logger.getLogger(Przedmiot.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    /**
     * Metoda zwracajaca liste wszystkich przedmiotow jezeli m_rok_akademicki =
     * 0 - zwraca wszystko z bazy danych jezeli m_rok_akademicki != 0 - zwraca
     * przedmioty dla danego roku
     *
     * @param m_rok_akademicki
     * @return List<Przedmiot>
     */
    public static List<Przedmiot> getPrzedmioty(int m_rok_akademicki) {
        List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = null;
            if (m_rok_akademicki == 0) {
                prepStmt = conn.prepareStatement(
                        "SELECT P.id AS p_id, P.nazwa AS p_nazwa, G.id AS g_id, G.sub_id AS g_sub_id, G.nazwa AS g_nazwa, G.waga AS g_waga, P.typ_oceniania AS p_typ_oceniania, P.rok_akademicki_start AS p_rok_akademicki, P.semestr AS p_semestr FROM przedmioty as P join grupa_ocen as G on P.id_grupa_ocen=G.id ORDER BY P.rok_akademicki_start DESC, P.id ASC");
            } else {
                prepStmt = conn.prepareStatement(
                        "SELECT P.id AS p_id, P.nazwa AS p_nazwa, G.id AS g_id, G.sub_id AS g_sub_id, G.nazwa AS g_nazwa, G.waga AS g_waga, P.typ_oceniania AS p_typ_oceniania, P.rok_akademicki_start AS p_rok_akademicki, P.semestr AS p_semestr FROM przedmioty as P join grupa_ocen as G on P.id_grupa_ocen=G.id WHERE P.rok_akademicki_start >= " + m_rok_akademicki + " ORDER BY P.rok_akademicki_start DESC, P.id ASC");
            }
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Przedmiot jeden_przedmiot = new Przedmiot(resultSet.getInt("p_id"), resultSet.getString("p_nazwa"),
                        new GrupaOcen(resultSet.getInt("g_id"), resultSet.getInt("g_sub_id"), resultSet.getString("g_nazwa"), resultSet.getFloat("g_waga")),
                        TYP_OCENIANIA.values()[resultSet.getInt("p_typ_oceniania")], resultSet.getInt("p_rok_akademicki"), SEMESTR.values()[resultSet.getInt("p_semestr")]);
                przedmioty.add(jeden_przedmiot);
            }
            return przedmioty;
        } catch (SQLException ex) {
            Logger.getLogger(Przedmiot.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    public static List<Przedmiot> getCurrentYearPrzedmioty() {
        List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                        "SELECT P.id AS p_id, P.nazwa AS p_nazwa, G.id AS g_id, G.sub_id AS g_sub_id, G.nazwa AS g_nazwa, G.waga AS g_waga, P.typ_oceniania AS p_typ_oceniania, P.rok_akademicki_start AS p_rok_akademicki, P.semestr AS p_semestr FROM przedmioty as P join grupa_ocen as G on P.id_grupa_ocen=G.id WHERE P.rok_akademicki_start = " + startRokuAkademickiego() + " ORDER BY P.rok_akademicki_start DESC, P.id ASC");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Przedmiot jeden_przedmiot = new Przedmiot(resultSet.getInt("p_id"), resultSet.getString("p_nazwa"),
                        new GrupaOcen(resultSet.getInt("g_id"), resultSet.getInt("g_sub_id"), resultSet.getString("g_nazwa"), resultSet.getFloat("g_waga")),
                        TYP_OCENIANIA.values()[resultSet.getInt("p_typ_oceniania")], resultSet.getInt("p_rok_akademicki"), SEMESTR.values()[resultSet.getInt("p_semestr")]);
                przedmioty.add(jeden_przedmiot);
            }
            return przedmioty;
        } catch (SQLException ex) {
            Logger.getLogger(Przedmiot.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    /**
     * Metoda zwracajaca liste przedmiotow dla danego dnia tygodnia
     *
     * @param m_rok_akademicki
     * @param m_dzien
     * @return List<Przedmiot>
     * @throws SQLException
     */
    public static List<Przedmiot> getPrzedmioty(int m_rok_akademicki, Termin.DZIEN_TYG m_dzien) throws SQLException {
        List<Przedmiot> przedmioty = new ArrayList<Przedmiot>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = null;
            if (m_rok_akademicki == 0) {
                prepStmt = conn.prepareStatement(
                        "SELECT DISTINCT PRZ.nazwa AS Przedmiot, GRU.nazwa AS Grupa, TER.godzina_start AS Godzina_Start, TER.godzina_stop AS Godzina_stop "
                        + "FROM przedmioty AS PRZ JOIN terminy AS TER JOIN grupa_cwiczeniowa AS GRU "
                        + "ON PRZ.id = GRU.id_przedmiot AND TER.id_grupa_cwiczeniowa = GRU.id "
                        + "WHERE TER.dzien_tygodnia = ?");
                prepStmt.setString(1, m_dzien.name());
            } else {
                prepStmt = conn.prepareStatement(
                        "SELECT DISTINCT PRZ.nazwa AS Przedmiot, GRU.nazwa AS Grupa, TER.godzina_start AS Godzina_Start, TER.godzina_stop AS Godzina_stop "
                        + "FROM przedmioty AS PRZ JOIN terminy AS TER JOIN grupa_cwiczeniowa AS GRU "
                        + "ON PRZ.id = GRU.id_przedmiot AND TER.id_grupa_cwiczeniowa = GRU.id "
                        + "WHERE TER.dzien_tygodnia = ? AND PRZ.rok_akademicki_start = ?");
                prepStmt.setString(1, m_dzien.name());
                prepStmt.setInt(2, m_rok_akademicki);
            }

            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                /*Przedmiot jeden_przedmiot = new Przedmiot();
                 jeden_przedmiot.setId(resultSet.getInt("id"));
                 jeden_przedmiot.setNazwa(resultSet.getString("nazwa"));
                 jeden_przedmiot.setGrupaOcen(resultSet.getInt("id_grupa_ocen"));
                 jeden_przedmiot.setTypOceniania(TYP_OCENIANIA.valueOf(resultSet.getString("typ_oceniania")));
                 jeden_przedmiot.setRokAkademicki(resultSet.getInt("rok_akademicki_start"));
                 jeden_przedmiot.setSemestr(SEMESTR.valueOf(resultSet.getString("semestr")));
                 przedmioty.add(jeden_przedmiot);*/
            }
            prepStmt.close();
        } catch (Exception e) {

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return przedmioty;
    }

    /**
     * Metoda zwracajaca przedmioty dla danego studenta
     *
     * @param m_rok_akademicki
     * @param m_student
     * @return
     */
    public static List<Przedmiot> getPrzedmioty(int m_rok_akademicki, Student m_student) {
        /*
         SELECT DISTINCT PRZ.nazwa AS Przedmiot, GRU.nazwa AS Grupa, TER.dzien_tygodnia AS Dzien_Tygodnia, TER.godzina_start AS Godzina_Start, TER.godzina_stop AS Godzina_stop, PRZ.rok_akademicki_start AS Rok_Akademicki
         FROM przedmioty AS PRZ 
         JOIN terminy AS TER 
         JOIN grupa_cwiczeniowa AS GRU 
         JOIN grupa_student AS GS 
         JOIN student AS ST
         ON PRZ.id = GRU.id_przedmiot 
         AND TER.id_grupa_cwiczeniowa = GRU.id 
         AND GRU.id = GS.id_grupa_cwiczeniowa
         WHERE ST.id = 1
         */
        return null;
    }

    public static Przedmiot getPrzedmiot(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select id,id_grupa_ocen,nazwa,typ_oceniania,rok_akademicki_start,semestr"
                    + " from przedmioty where id=" + id);
            rs.next();
            Przedmiot pr = new Przedmiot(rs.getInt(1), rs.getString(3), GrupaOcen.getGrupaOcen(rs.getInt(2)), TYP_OCENIANIA.values()[rs.getInt(4)], rs.getInt(5), SEMESTR.values()[rs.getInt(6)]);
            st.close();
            return pr;
        } catch (SQLException ex) {
            Logger.getLogger(Przedmiot.class.getName()).log(Level.SEVERE, null, ex);
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

    public static boolean addPrzedmiot(String m_nazwa, GrupaOcen m_grupa_ocen, TYP_OCENIANIA m_typ_oceniania, int m_rok_aka, SEMESTR m_semestr) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO przedmioty(id_grupa_ocen, nazwa, typ_oceniania, rok_akademicki_start, semestr) VALUES (?, ?, ?, ?, ?)");
            prepStmt.setInt(1, m_grupa_ocen.getId());
            prepStmt.setString(2, m_nazwa);
            prepStmt.setInt(3, m_typ_oceniania.ordinal());
            prepStmt.setInt(4, m_rok_aka);
            prepStmt.setInt(5, m_semestr.ordinal());
            prepStmt.execute();
            return true;
        } catch (SQLException ex) {
            System.err.println("Klasa Przedmiot, Metoda addPrzedmiot - problem z dodaniem elementu");
            Logger.getLogger(Oceny.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void delPrzedmiot(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM przedmioty WHERE id = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();
            // Usuwanie podleglych mu grup cwiczeniowych
            prepStmt = conn.prepareStatement(
                    "SELECT id FROM grupa_cwiczeniowa WHERE id_przedmiot = ?");
            prepStmt.setInt(1, id);
            ResultSet resultSet = prepStmt.executeQuery();
            List<Integer> lista = new ArrayList<Integer>();
            while (resultSet.next()) {
                 lista.add(resultSet.getInt("id"));
            }
            for(int i = 0; i < lista.size(); i++) {
                System.out.println("usuwam grupy cwiczeniowe przedmiotu " + id);
                GrupaCwiczeniowa.delete(lista.get(i));
            }
            
        } catch (SQLException e) {
            System.err.println("Przedmiot -> delPrzedmiot(int) -> problem z usunieciem przedmiotu");
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

    public static void updatePrzedmiot(Przedmiot przedmiot) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE przedmioty SET id_grupa_ocen=?, nazwa=?, typ_oceniania=?, rok_akademicki_start=?, semestr=? WHERE id = ?");
            prepStmt.setInt(1, przedmiot.getGrupaOcen().getId());
            prepStmt.setString(2, przedmiot.getNazwa());
            prepStmt.setInt(3, przedmiot.getTypOceniania().ordinal());
            prepStmt.setInt(4, przedmiot.getRokAkademicki());
            prepStmt.setInt(5, przedmiot.getSemestr().ordinal());
            prepStmt.setInt(6, przedmiot.getId());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Przedmiot -> delPrzedmiot(int) -> problem z usunieciem przedmiotu");
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

    @Override
    public String toString() {
        //tymczasowe rozwiązanie dla celu uzupełniania comboboxa grup cwiczeniowych
        return nazwa;
    }

    private static int startRokuAkademickiego() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (month < 9) {
            year--;
        }
        return year;
    }
}
