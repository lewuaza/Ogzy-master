/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mailer.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.database.SqlConnection;
import org.jsoup.Jsoup;

/**
 *
 * @author lewuaza
 */
public class MessageModel {
    private int id;
    private String folderID;
    private Date recivedDate;
    private String content;
    private String from;
    private String subject;
    private boolean isRead;
    private int accountID;
    public MessageModel(){}
    public void setID(int id)
    {
        this.id = id;
    }
    public int getID()
    {
        return this.id;
    }
    public String getFolderID()
    {
        return this.folderID;
    }
    public void setFolderID(String folderID)
    {
        this.folderID = folderID;
    }
    public Date getDate(){
        return this.recivedDate;
    }
    public void setDate(Date date){
        this.recivedDate = date;
    }
    public String getContent()
    {
        return this.content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    public String getSubject()
    {
        return this.subject == null ? "" :  this.subject;
    }
    public String getFrom()
    {
        return Jsoup.parse(this.from).text();
    }
    public void setFrom(String from)
    {
        this.from = from;
    }
    public boolean getIsRead()
    {
        return this.isRead;
    }
    public void setIsRead(boolean isRead)
    {
        this.isRead = isRead;
    }
    
    public void setAccountID(int accountID)
    {
        this.accountID = accountID;
    }
    
    public int getAccountID()
    {
        return this.accountID;
    }
    
    public static int getLastEmailID(String folderID, int accountID)
    {
        int result = 1;
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT MAX(id) FROM mailer_mails where folderName = '" + folderID +"' AND accountID = " + accountID +"");   
            if(resultSet.next())
                result = resultSet.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(MessageModel.class.getName()).log(Level.SEVERE, null, ex);
        }
         finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        if (result < 1) result = 1; 
        return result;
    }
    
    public static ArrayList<MessageModel> getEmailsByFolder(String folderName , int accountID)
    {
        ArrayList<MessageModel> list = new ArrayList<MessageModel>();
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT id, folderName , recived_date , content , [from] , subject , isRead FROM mailer_mails where folderName = '" + folderName +"' AND accountID = " + accountID+"" );
            while (resultSet.next()) {
                MessageModel msg = new MessageModel();
                msg.setID(resultSet.getInt(1));
                msg.setFolderID(resultSet.getString(2));
                msg.setDate(resultSet.getDate(3));
                msg.setContent(resultSet.getString(4));
                msg.setFrom(resultSet.getString(5));
                msg.setSubject(resultSet.getString(6));
                msg.setIsRead(resultSet.getBoolean(7));
                list.add(msg);
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
    
    public void setRead()
    {
        Connection conn = null;
        try {
            conn = SqlConnection.getInstance().getSqlConnection();
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE mailer_mails set isRead = 1 WHERE id = ?");
            prepStmt.setInt(1, this.id);
            boolean result = prepStmt.execute();
            prepStmt.close();
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
                    "INSERT INTO mailer_mails values (?, ?, ?, ? , ? , ? , ? , ?)");
            prepStmt.setString(2, folderID);
            prepStmt.setDate(3, recivedDate);
            prepStmt.setString(4, content);
            prepStmt.setString(5, from);
            prepStmt.setString(6, subject);
            prepStmt.setBoolean(7, isRead);
            prepStmt.setInt(8, accountID);
            boolean result = prepStmt.execute();
            prepStmt.close();
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
}
