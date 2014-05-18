package org.database.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.database.SqlConnection;

/**
 *
 * @author Marcin
 * @edited Mariushrek (23.01.2014)
 */
public class GrupaStudent {

    private Student student;
    private GrupaCwiczeniowa grupa;

    public GrupaStudent(Student k_student, GrupaCwiczeniowa k_grupa) {
        this.student = k_student;
        this.grupa = k_grupa;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student m_student) {
        this.student = m_student;
    }

    public GrupaCwiczeniowa getGrupaCwiczeniowa() {
        return grupa;
    }

    public void setGrupaCwiczeniowa(GrupaCwiczeniowa m_grupa) {
        this.grupa = m_grupa;
    }

    @Override
    public String toString() {
        return "GrupaStudent{" + "studentId=" + student.getId() + ", grupaCwId=" + grupa.getId() + '}';
    }

    public static List<Student> getAllStudentForGroup() {
        List<Student> studentIds = new ArrayList<Student>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT DISTINCT S.id AS Sid, S.imie AS Simie, S.nazwisko AS Snazwisko, S.email AS Smail, S.indeks AS Sindeks"
                    + "FROM grupa_student AS GS JOIN student AS S "
                    + "ON GS.id_student=S.id");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("id_student");
                studentIds.add(new Student(resultSet.getInt("Sid"), resultSet.getString("Simie"), resultSet.getString("Snazwisko"), resultSet.getString("Smail"), resultSet.getInt("Sindeks")));
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
        return studentIds;
    }

    public static List<Integer> getAllGroupForStudent() {
        //TODO: Zaimplementowanie tego
        return null;
    }

    public static List<Integer> getAllStudentIdsForGroup() {
        List<Integer> studentIds = new ArrayList<Integer>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT DISTINCT id_student FROM grupa_student");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("id_student");
                studentIds.add(studentId);
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
        return studentIds;
    }

    public static List<Integer> getAllGroupIdsForStudent() {
        List<Integer> groupIds = new ArrayList<Integer>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "SELECT DISTINCT id_grupa_cwiczeniowa FROM grupa_student");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                int groupId = resultSet.getInt("id_grupa_cwiczeniowa");
                groupIds.add(groupId);
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
        return groupIds;
    }

    public static void add(int studentId, int grupaId) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO grupa_student values (?, ?)");
            prepStmt.setInt(1, studentId);
            prepStmt.setInt(2, grupaId);
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

    public static void add(Student student, GrupaCwiczeniowa grupa) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO grupa_student values (?, ?)");
            prepStmt.setInt(1, student.getId());
            prepStmt.setInt(2, grupa.getId());
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

    public static void delete(int studentId, int grupaCwId) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM grupa_student WHERE id_student = ? AND id_grupa_cwiczeniowa = ?");
            prepStmt.setInt(1, studentId);
            prepStmt.setInt(2, grupaCwId);
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

    public static void delete(Student student, GrupaCwiczeniowa grupa) {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM grupa_student WHERE id_student = ? AND id_grupa_cwiczeniowa = ?");
            prepStmt.setInt(1, student.getId());
            prepStmt.setInt(2, grupa.getId());
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
}
