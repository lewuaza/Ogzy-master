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

public class Student {

    private int id;
    private String imie;
    private String nazwisko;
    private String email;
    private int indeks;

    public Student(int k_id, String k_imie, String k_nazwisko, String k_email, int k_indeks) {
        this.id = k_id;
        this.imie = k_imie;
        this.nazwisko = k_nazwisko;
        this.email = k_email;
        this.indeks = k_indeks;
    }

    private Student() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    @Override
    public String toString() {
        return getImie() + " " + getNazwisko();
    }

    public static List<Student> getAll() {
        List<Student> students = new ArrayList<Student>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT * FROM student ORDER BY nazwisko");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getInt("id"), resultSet.getString("imie"), resultSet.getString("nazwisko"), resultSet.getString("email"), resultSet.getInt("indeks"));
                students.add(student);
            }
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
        return students;
    }

    public static Student getById(Integer studentId) {
        Student student = new Student();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT * FROM student WHERE id = ?");
            prepStmt.setInt(1, studentId);
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                student = new Student(resultSet.getInt("id"), resultSet.getString("imie"), resultSet.getString("nazwisko"), resultSet.getString("email"), resultSet.getInt("indeks"));
            }
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
        return student;
    }

    public static void add(String imie, String nazwisko, String email, int indeksNr) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO student values (?, ?, ?, ?, ?)");
            prepStmt.setString(2, imie);
            prepStmt.setString(3, nazwisko);
            prepStmt.setString(4, email);
            prepStmt.setInt(5, indeksNr);
            prepStmt.execute();
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

    public static void edit(Student student) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE student SET imie = ?, nazwisko = ?, email = ?, indeks = ? WHERE id = ?");
            prepStmt.setInt(5, student.getId());
            prepStmt.setString(1, student.getImie());
            prepStmt.setString(2, student.getNazwisko());
            prepStmt.setString(3, student.getEmail());
            prepStmt.setInt(4, student.getIndeks());
            prepStmt.execute();
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

    public static void delete(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            // Usuwanie studenta
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM student WHERE id = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();
            // Usuwanie przynaleznosci studentow do grup
            prepStmt = conn.prepareStatement(
                    "DELETE FROM grupa_student WHERE id_student = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();
            // Usuwanie obecnosci studenta
            prepStmt = conn.prepareStatement(
                    "DELETE FROM obecnosc WHERE id_student = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();          
            // Usuwanie ocen studenta
            prepStmt = conn.prepareStatement(
                    "DELETE FROM oceny WHERE id_student = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();             
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

    public static void delete(String imie, String nazwisko, String email, int indeksNr) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT id FROM student WHERE imie = ? AND nazwisko = ? AND email = ? AND indeks = ?");
            prepStmt.setString(1, imie);
            prepStmt.setString(2, nazwisko);
            prepStmt.setString(3, email);
            prepStmt.setInt(4, indeksNr);
            prepStmt.execute();
            ResultSet resultSet = prepStmt.executeQuery();
            List<Integer> lista = new ArrayList<Integer>();
            while (resultSet.next()) {
                lista.add(resultSet.getInt("id"));
            }
            for(int i = 0; i < lista.size(); i++) {
                delete(resultSet.getInt("id"));
            }
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

    public static List<Student> getByGroup(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            List<Student> students = new ArrayList<Student>();
            ResultSet rs = st.executeQuery("SELECT S.id,S.imie,S.nazwisko,S.email,S.indeks FROM student as S JOIN grupa_student as G ON S.id = G.id_student WHERE G.id_grupa_cwiczeniowa=" + id);
            while (rs.next()) {
                students.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            st.close();
            return students;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<Student> getAllWithoutGroup(int id) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            List<Student> students = new ArrayList<Student>();
            ResultSet rs = st.executeQuery("SELECT S.id, S.imie, S.nazwisko, S.email, S.indeks FROM student as S  WHERE s.id NOT IN (SELECT S.id FROM student as S JOIN grupa_student as G ON S.id = G.id_student WHERE G.id_grupa_cwiczeniowa = " + id + ")");
            while (rs.next()) {
                students.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            st.close();
            return students;
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
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
}
