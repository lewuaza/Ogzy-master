package org.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.SqlConnection;

/**
 *
 * @author Mariushrek
 */
public class Oceny {

    private int id;
    private GrupaOcen grupa_ocen;
    private GrupaCwiczeniowa grupa_cwiczeniowa;
    private Student student;
    private float wartosc_oceny;

    public Oceny(int k_id, GrupaOcen k_grupa_ocen, GrupaCwiczeniowa k_grupa_cwiczeniowa, Student k_student, float k_wartosc_oceny) {
        this.id = k_id;
        this.grupa_ocen = k_grupa_ocen;
        this.grupa_cwiczeniowa = k_grupa_cwiczeniowa;
        this.student = k_student;
        this.wartosc_oceny = k_wartosc_oceny;
    }

    /**
     * Pobranie id
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Pobranie grupa_ocen
     *
     * @return grupa_ocen
     */
    public GrupaOcen getGrupaOcen() {
        return this.grupa_ocen;
    }

    /**
     * Pobranie grupa_cwiczeniowa
     *
     * @return grupa_cwiczeniowa
     */
    public GrupaCwiczeniowa getGrupaCwiczeniowa() {
        return this.grupa_cwiczeniowa;
    }

    /**
     * Pobranie student
     *
     * @return student
     */
    public Student getStudent() {
        return this.student;
    }

    /**
     * Pobranie oceny
     *
     * @return wartosc_oceny
     */
    public float getWartoscOceny() {
        return this.wartosc_oceny;
    }

    /**
     * Ustawienie id
     *
     * @param m_id
     */
    public void setId(int m_id) {
        this.id = m_id;
    }

    /**
     * Ustawienie grupa_ocen
     *
     * @param m_grupa_ocen
     */
    public void setGrupaOcen(GrupaOcen m_grupa_ocen) {
        this.grupa_ocen = m_grupa_ocen;
    }

    /**
     * Ustawienie grupa_cwiczeniowa
     *
     * @param m_grupa_cwiczeniowa
     */
    public void setId_GrupaCwiczeniowa(GrupaCwiczeniowa m_grupa_cwiczeniowa) {
        this.grupa_cwiczeniowa = m_grupa_cwiczeniowa;
    }

    /**
     * Ustawienie student
     *
     * @param m_student
     */
    public void setStudent(Student m_student) {
        this.student = m_student;
    }

    /**
     * Ustawienie oceny
     *
     * @param m_ocena
     */
    public void setWartoscOceny(float m_ocena) {
        this.wartosc_oceny = m_ocena;
    }

    /**
     * Metoda, ktora umozliwia pobranie ocen dla danego studenta i grupy
     * cwiczeniowej
     *
     * @param student
     * @param grupa
     * @return List<Oceny>
     */
    public static List<Oceny> getOceny(Student student, GrupaCwiczeniowa grupa) {        
        List<Oceny> oceny = new LinkedList<Oceny>();
        Connection conn = null;
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT O.id AS Oid, O.wartosc_oceny AS Owo,"
                    + "GO.id AS Goid, GO.sub_id AS GOsub_id, GO.nazwa AS GOnazwa, GO.waga AS GOwaga "
                    + "FROM oceny AS O JOIN grupa_ocen AS GO ON O.id_grupa_ocen=GO.id "
                    + "WHERE O.id_student=" + student.getId() + " AND O.id_grupa_cwiczeniowa=" + grupa.getId());
            while (rs.next()) {
                Oceny ocena = new Oceny(rs.getInt("Oid"),
                        new GrupaOcen(rs.getInt("GOid"), rs.getInt("GOsub_id"), rs.getString("GOnazwa"), rs.getFloat("GOwaga")),
                        grupa,
                        student,
                        rs.getFloat("Owo"));
                oceny.add(ocena);
            }
            return oceny;
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Funkcja getOceny(Student, GrupaCwiczeniowa) - problem z pobraniem elementu");
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
        return null;
    }

    /**
     * Metoda, ktora umozliwia pobranie ocen dla danego studenta
     *
     * @param student
     * @return List<Oceny>
     */
    public static List<Oceny> getOceny(Student student) {        
        List<Oceny> oceny = new LinkedList<Oceny>();
        Connection conn = null;
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT O.id AS Oid, O.wartosc_oceny AS Owo, "
                    + "GO.id AS GOid, GO.sub_id AS GOsub_id, GO.nazwa AS GOnazwa, GO.waga AS GOwaga,"
                    + "GC.id AS GCid, GC.nazwa AS GCnazwa, GC.color AS GCcolor,"
                    + "P.id AS Pid, P.nazwa AS Pnazwa, P.typ_oceniania AS Ptypoceniania, P.rok_akademicki_start AS Prokakademicki, P.semestr AS Psemestr "
                    + "FROM oceny AS O JOIN grupa_ocen AS GO JOIN grupa_cwiczeniowa AS GC JOIN przedmioty AS P"
                    + "ON O.id_grupa_ocen=GO.id AND O.id_grupa_cwiczeniowa=GC.id AND GC.id_przedmiot=P.id"
                    + "WHERE id_student=" + student.getId());
            while (rs.next()) {
                Oceny ocena = new Oceny(rs.getInt("Oid"),
                        new GrupaOcen(rs.getInt("GOid"), rs.getInt("GOsub_id"), rs.getString("GOnazwa"), rs.getFloat("GOwaga")),
                        new GrupaCwiczeniowa(rs.getInt("GCid"), rs.getString("GCnazwa"),
                                new Przedmiot(rs.getInt("Pid"), rs.getString("Pnazwa"), new GrupaOcen(rs.getInt("GOid"), rs.getInt("GOsub_id"), rs.getString("GOnazwa"), rs.getFloat("GOwaga")),
                                        TYP_OCENIANIA.values()[rs.getInt("Ptypoceniania")], rs.getInt("Prokskademicki"), SEMESTR.values()[rs.getInt("Psemestr")]),rs.getString("GCcolor")),
                        student,
                        rs.getFloat("Owo"));
                oceny.add(ocena);
            }
            return oceny;
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Funkcja getOceny(Student) - problem z pobraniem elementu");
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
        return null;
    }

    /**
     * Metoda, ktora umozliwia pobranie ocen dla danej grupy cwiczeniowej
     *
     * @param grupa
     * @return List<Oceny>
     */
    public static List<Oceny> getOceny(GrupaCwiczeniowa grupa) {
        List<Oceny> oceny = new LinkedList<Oceny>();
        Connection conn = null;        
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT O.id AS Oid, O.wartosc_oceny AS Owo, "
                    + "GO.id AS GOid, GO.sub_id AS GOsub_id, GO.nazwa AS GOnazwa, GO.waga AS GOwaga,"
                    + "S.id AS Sid, S.imie AS Simie, S.nazwisko AS Snazwisko, S.email AS Smail, S.indeks AS Sindeks"
                    + "FROM oceny AS O JOIN grupa_ocen AS GO JOIN student as S"
                    + "ON O.id_grupa_ocen=GO.id AND O.id_student = S.id"
                    + "WHERE id_grupa_cwiczeniowa=" + grupa.getId());
            while (rs.next()) {
                Oceny ocena = new Oceny(rs.getInt("Oid"),
                        new GrupaOcen(rs.getInt("GOid"), rs.getInt("GOsub_id"), rs.getString("GOnazwa"), rs.getFloat("GOwaga")),
                        grupa,
                        new Student(rs.getInt("Sid"), rs.getString("Simie"), rs.getString("Snazwisko"), rs.getString("Semail"), rs.getInt("Sindeks")),
                        rs.getFloat("Owo"));
                oceny.add(ocena);
            }
            return oceny;
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Funkcja getOceny(Student) - problem z pobraniem elementu");
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
        return null;
    }

    /**
     * Metoda, umozliwiajaca dodanie oceny do bazy dla podanej grupy_ocen,
     * grupy_cwiczeniowej, studenta, oceny
     *
     * @param grupa_ocen
     * @param grupa_cwiczeniowa
     * @param student
     * @param ocena
     */
    public static void addOceny(GrupaOcen grupa_ocen, GrupaCwiczeniowa grupa_cwiczeniowa, Student student, float ocena) {
        Connection conn = null;
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO oceny(id_grupa_ocen, id_grupa_cwiczeniowa, id_student, wartosc_oceny) VALUES (?, ?, ?, ?)");
            prepStmt.setInt(1, grupa_ocen.getId());
            prepStmt.setInt(2, grupa_cwiczeniowa.getId());
            prepStmt.setInt(3, student.getId());
            prepStmt.setFloat(4, ocena);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Metoda addOceny(GrupaOcen, GrupaCwiczeniowa, Student, Float) - problem z dodaniem elementu");
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
    }

    /**
     * Metoda, umozliwiajaca dodanie oceny do bazy dla podanej Oceny
     *
     * @param ocena
     */
    public static void addOceny(Oceny ocena) {
        Connection conn = null;
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO oceny(id_grupa_ocen, id_grupa_cwiczeniowa, id_student, wartosc_oceny) VALUES (?, ?, ?, ?)");
            prepStmt.setInt(1, ocena.getGrupaOcen().getId());
            prepStmt.setInt(2, ocena.getGrupaCwiczeniowa().getId());
            prepStmt.setInt(3, ocena.getStudent().getId());
            prepStmt.setFloat(4, ocena.getWartoscOceny());
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Metoda addOceny(Ocena) - problem z dodaniem elementu");
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
    }

    /**
     * Metoda umozliwiajaca edycje oceny po jej ID
     *
     * @param id
     * @param wartosc
     */
    public static void editOceny(int id, float wartosc) {
        Connection conn = null;
        try {
             conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE oceny SET wartosc_oceny = ? WHERE id = ?");
            prepStmt.setFloat(1, wartosc);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Metoda editOceny(int, float) - problem z edycja elementu");
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
    }

    /**
     * Metoda umozliwiajaca usuniecie oceny po jej ID
     *
     * @param id
     */
    public static void deleteOceny(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM oceny WHERE id = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Oceny, Metoda deleteOceny(int) - problem z usunieciem elementu");
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
    }
}
