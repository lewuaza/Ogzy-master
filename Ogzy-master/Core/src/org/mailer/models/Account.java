/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mailer.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.database.SqlConnection;

/**
 *
 * @author lewuaza
 */
public class Account {
    private String login;
    private String password;
    private String pop3hostname;
    private String name;
    private String smptHostname;
    private String smptPort;
    private String pop3Port;
    private int id;
    public Account(String login , String password, String hostname , String name , String smptHostname)
    {
        this.login = login;
        this.password = password;
        this.pop3hostname = hostname;
        this.name = name;
        this.smptHostname = smptHostname;
    }
    public Account()
    {
    }
    public String getLogin()
    {
        return this.login;
    }
    public void setLogin(String login)
    {
        this.login = login;
    }
    public String getPassword()
    {        return this.password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPOP3Hostname()
    {
        return this.pop3hostname;
    }
    public void setPOP3Hostname(String hostname)
    {        this.pop3hostname = hostname;
    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getSMTP()
    {
        return this.smptHostname;
    }
    public void setSMTP(String hostname)
    {
        this.smptHostname = hostname;
    }
    public void setSMTPPort(String port)
    {
        this.smptPort = port;
    }
    public void setPOP3Port(String port)
    {
        this.pop3Port = port;
    }
    public String getSMTPPort()
    {
        return this.smptPort;
    }
    public String getPOP3Port()
    {
        return this.pop3Port;
    }
    public static ArrayList<Account> getAll()
    {
        ArrayList<Account> list = new ArrayList<Account>();
                Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();

            //PreparedStatement prepStmt = conn.prepareStatement(
            //        "SELECT id, name , login , password , pop3Hostname , pop3Port , smptHostname , smptPort FROM mailer_settings");
            //ResultSet resultSet = prepStmt.executeQuery();
            
                         conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT id, name , login , password , pop3Hostname , pop3Port , smptHostname , smptPort FROM mailer_settings");

            while (resultSet.next()) {

                Account acc = new Account();
                acc.setID(resultSet.getInt(1));
                acc.setName(resultSet.getString(2));
                acc.setLogin(resultSet.getString(3));
                acc.setPassword(resultSet.getString(4));
                acc.setPOP3Hostname(resultSet.getString(5));
                acc.setPOP3Port(resultSet.getString(6));
                acc.setSMTP(resultSet.getString(7));
                acc.setSMTPPort(resultSet.getString(8));
                list.add(acc);
            }
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // Logger.getLogger(GrupaCwiczeniowa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return list;
    }
    public void delete()
    {
        delete(this.id);
    }
    public void delete(int id)
    {
        try {
            Connection conn = SqlConnection.getInstance().getSqlConnection();
            // Usuwanie studenta
            PreparedStatement prepStmt = conn.prepareStatement(
                    "DELETE FROM mailer_settings WHERE id = ?");
            prepStmt.setInt(1, id);
            prepStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void update()
    {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE mailer_settings SET name=? , login=? , password=? ,"
                            + " pop3Hostname=? , pop3Port=? , smptHostname=? , smptPort=? "
                            + "WHERE id = ? ");
            prepStmt.setString(1, name);
            prepStmt.setString(2, login);
            prepStmt.setString(3, password);
            prepStmt.setString(4, pop3hostname);
            prepStmt.setString(5, pop3Port);
            prepStmt.setString(6, smptHostname);
            prepStmt.setString(7, smptPort);
            prepStmt.setInt(8, id);
            boolean result = prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // Logger.getLogger(GrupaCwiczeniowa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void add()
    {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO mailer_settings values (?, ?, ?, ? , ? , ? , ? , ?)");
            prepStmt.setString(2, name);
            prepStmt.setString(3, login);
            prepStmt.setString(4, password);
            prepStmt.setString(5, pop3hostname);
            prepStmt.setString(6, pop3Port);
            prepStmt.setString(7, smptHostname);
            prepStmt.setString(8, smptPort);
            boolean result = prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // Logger.getLogger(GrupaCwiczeniowa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void setID(int id)
    {
        this.id = id;
    }
    public int getID()
    {
        return this.id;
    }
}
