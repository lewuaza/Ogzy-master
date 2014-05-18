package org.database.models;

import java.sql.Connection;
import java.sql.Date;
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
public class Obecnosc {

    private int id;
    private Termin termin;
    private Student student;
    private String data;
    private boolean obecnosc;

    public Obecnosc(int k_id, Termin k_termin, Student k_student, String k_data, boolean k_obecnosc) {
        this.id = k_id;
        this.termin = k_termin;
        this.student = k_student;
        this.data = k_data;
        this.obecnosc = k_obecnosc;
    }

    public int getId() {
        return this.id;
    }

    public Termin getTermin() {
        return this.termin;
    }

    public Student getStudent() {
        return this.student;
    }

    public String getData() {
        return this.data;
    }

    public boolean getObecnosc() {
        return this.obecnosc;
    }

    public void setId(int m_id) {
        this.id = m_id;
    }

    public void setTermin(Termin m_termin) {
        this.termin = m_termin;
    }

    public void setStudent(Student m_student) {
        this.student = m_student;
    }

    public void setData(String m_data) {
        this.data = m_data;
    }

    public void setObecnosc(boolean m_obecnosc) {
        this.obecnosc = m_obecnosc;
    }

    public static boolean isThisDateOnDB(String today, Termin termin) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement("SELECT data FROM obecnosc WHERE data = ? AND id_termin = ?");
            prepStmt.setString(1, today);
            prepStmt.setInt(2, termin.getId());
            ResultSet rs = prepStmt.executeQuery();
            boolean val = rs.next();
            prepStmt.close();
            return val;
        } catch (SQLException ex) {
            Logger.getLogger(Obecnosc.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void check(Student student, int idTerminu, String data, boolean valueAt) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            String query;
            if (valueAt) {
                query = "UPDATE obecnosc SET obecnosc=1 WHERE id_termin = ? AND id_student = ? AND data = ?";
            } else {
                query = "UPDATE obecnosc SET obecnosc=0 WHERE id_termin = ? AND id_student = ? AND data = ?";
            }
            PreparedStatement prepStmt = conn.prepareStatement(query);
            prepStmt.setInt(1, idTerminu);
            prepStmt.setInt(2, student.getId());
            prepStmt.setString(3, data);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Obecnosc.class.getName()).log(Level.SEVERE, null, ex);
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
     * Metoda zwracaja Liste<Obecnosc> dla danego terminu i daty
     *
     * @param termin
     * @return
     */
    public static List<Obecnosc> getObecnosci(Termin termin) {
        List<Obecnosc> obecnosci = new LinkedList<Obecnosc>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT O.id AS Oid, O.obecnosc AS Oobecnosc, O.data AS Odata, "
                    + "S.id AS Sid, S.imie AS Simie, S.nazwisko AS Snazwisko, S.email AS Semail, S.indeks AS Sindeks "
                    + "FROM obecnosc AS O JOIN student AS S "
                    + "ON S.id=O.id_student "
                    + "WHERE id_termin=" + termin.getId());
            while (rs.next()) {
                Obecnosc obecnosc = new Obecnosc(rs.getInt("Oid"),
                        termin,
                        new Student(rs.getInt("Sid"), rs.getString("Simie"), rs.getString("Snazwisko"), rs.getString("Semail"), rs.getInt("Sindeks")),
                        rs.getString("Odata"),
                        rs.getBoolean("Oobecnosc"));
                obecnosci.add(obecnosc);
            }
            return obecnosci;
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Funkcja getObecnosci(Termin,Date) - problem z pobraniem elementu");
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
     * Metoda zwracaja Liste<Obecnosc> dla danego terminu i daty
     *
     * @param termin
     * @param data
     * @return
     */
    public static List<Obecnosc> getObecnosci(Termin termin, String data) {
        List<Obecnosc> obecnosci = new LinkedList<Obecnosc>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement("SELECT O.id AS Oid, O.obecnosc AS Oobecnosc, "
                    + "S.id AS Sid, S.imie AS Simie, S.nazwisko AS Snazwisko, S.email AS Semail, S.indeks AS Sindeks "
                    + "FROM obecnosc AS O JOIN student AS S "
                    + "ON S.id=O.id_student "
                    + "WHERE id_termin = ? AND data = ?");
            prepStmt.setInt(1, termin.getId());
            prepStmt.setString(2, data);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                Obecnosc obecnosc = new Obecnosc(rs.getInt("Oid"),
                        termin,
                        new Student(rs.getInt("Sid"), rs.getString("Simie"), rs.getString("Snazwisko"), rs.getString("Semail"), rs.getInt("Sindeks")),
                        data,
                        rs.getBoolean("Oobecnosc"));
                obecnosci.add(obecnosc);
            }
            prepStmt.close();
            return obecnosci;
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Funkcja getObecnosci(Termin,Date) - problem z pobraniem elementu");
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
     * Metoda zwracajaca liste obecnosci dla danego terminu i studenta
     *
     * @param termin
     * @param student
     * @return
     */
    public static List<Obecnosc> getObecnosci(Termin termin, Student student) {
        List<Obecnosc> obecnosci = new LinkedList<Obecnosc>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, data, obecnosc FROM obecnosc WHERE id_termin=" + termin.getId() + " AND id_student =" + student.getId());
            while (rs.next()) {
                Obecnosc obecnosc = new Obecnosc(rs.getInt("id"),
                        termin,
                        student,
                        rs.getString("data"),
                        rs.getBoolean("obecnosc"));
                obecnosci.add(obecnosc);
            }
            return obecnosci;
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Funkcja getObecnosci(Termin, Student) - problem z pobraniem elementu");
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
     * Metoda zwracajaca obecnosc dla danego terminu, studenta i daty
     *
     * @param termin
     * @param student
     * @param data
     * @return
     */
    public static Obecnosc getObecnosci(Termin termin, Student student, Date data) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement("SELECT id, data, obecnosc FROM obecnosc WHERE id_termin = ? AND id_student = ? AND data = ?");
            ResultSet rs = prepStmt.executeQuery();
            prepStmt.setInt(1, termin.getId());
            prepStmt.setInt(2, student.getId());
            prepStmt.setDate(3, data);
            Obecnosc obecnosc = new Obecnosc(rs.getInt("id"),
                    termin,
                    student,
                    rs.getString("data"),
                    rs.getBoolean("obecnosc"));
            prepStmt.close();
            return obecnosc;
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Funkcja getObecnosci(Termin, Student, Date) - problem z pobraniem elementu");
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
     * Metoda dodajca do bazy obecnosc (wazne - Student student)
     *
     * @param termin
     * @param student
     * @param data
     * @param obecnosc
     */
    public static void addObecnosc(Termin termin, Student student, String data, boolean obecnosc) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO obecnosc(id_termin, id_student, data, obecnosc) VALUES (?, ?, ?, ?)");
            prepStmt.setInt(1, termin.getId());
            prepStmt.setInt(2, student.getId());
            prepStmt.setString(3, data);
            prepStmt.setBoolean(4, obecnosc);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Metoda addObecnosc(Termin, Student, Date, boolean) - problem z dodaniem elementu");
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
     * Metoda dodajaca do bazy obecnosc (wazne - int student)
     *
     * @param termin
     * @param student
     * @param data
     * @param obecnosc
     */
    public static void addObecnosc(Termin termin, int student, Date data, boolean obecnosc) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO obecnosc(id_termin, id_student, data, obecnosc) VALUES (?, ?, ?, ?)");
            prepStmt.setInt(1, termin.getId());
            prepStmt.setInt(2, student);
            prepStmt.setDate(3, data);
            prepStmt.setBoolean(4, obecnosc);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Metoda addObecnosc(Termin, int, Date, boolean) - problem z dodaniem elementu");
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
     * Metoda edytujaca obecnosc po jej ID
     *
     * @param id
     * @param obecnosc
     */
    public static void editObecnosc(int id, boolean obecnosc) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE obecnosc SET obecnosc = ? WHERE id = ?");
            prepStmt.setBoolean(1, obecnosc);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Metoda editObecnosc(int, boolean) - problem z edycja elementu");
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
     * Metoda edytujaca date obecnosci po jej ID
     *
     * @param id
     * @param data
     */
    public static void editObecnosc(int id, String data) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE obecnosc SET data = ? WHERE id = ?");
            prepStmt.setString(1, data);
            prepStmt.setInt(2, id);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Metoda editObecnosc(int, Date) - problem z edycja elementu");
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
     * Metoda usuwajaca obecnosc po jej ID
     *
     * @param id_terminu
     * @param data
     *
     */
    public static void deleteObecnosc(int id_terminu, String data) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM obecnosc WHERE id_termin = ? AND data = ?");
            prepStmt.setInt(1, id_terminu);
            prepStmt.setString(2, data);
            prepStmt.execute();
        } catch (SQLException ex) {
            System.err.println("Klasa Obecnosc, Metoda deleteObecnosc(int) - problem z usunieciem elementu");
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
