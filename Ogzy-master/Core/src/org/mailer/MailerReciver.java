/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mailer;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.jsoup.Jsoup;
import org.mailer.models.MessageModel;

/**
 *
 * @author lewuaza
 */
public class MailerReciver {

    private final Session session;
    private final MailerSettings settings;

    public MailerReciver(MailerSettings settings) {
        this.settings = settings;
        if (settings != null) {
            this.session = Session.getDefaultInstance(settings.getProperties());
        } else {
            this.session = null;
        }
    }

    public Folder[] getFolderList() {
        Folder[] folderList = null;
        Store emailStore = null;

        try {
            emailStore = session.getStore(this.settings.getStoreType());

            emailStore.connect(settings.getHostName(), settings.getUsername(), settings.getPassword());
            folderList = emailStore.getDefaultFolder().list("*");

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return folderList;
    }

    public Message[] getEmailsinFolder(Folder folder) {
        Message[] emailList = null;
        try {
            emailList = folder.getMessages();

        } catch (MessagingException ex) {
            Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailList;
    }

    public void addToJTree(JTree tree, boolean addMessages) {
        long start = System.currentTimeMillis();
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(settings.getAccount().getName());
        Folder[] folderList = this.getFolderList();
        MessageModel msgModel = new MessageModel();
        int stamp;
        root.add(node);
        StringBuilder sb = new StringBuilder();

        treeModel.reload();
        int j = 0;
        for (Folder f : folderList) {
            DefaultMutableTreeNode folderNode = new DefaultMutableTreeNode(f.getName());
            treeModel.insertNodeInto(folderNode, node, j++);
            if (addMessages) {
                try {
                    if (((f.getType() & Folder.HOLDS_MESSAGES) != 0)) {
                        f.open(Folder.READ_ONLY);
                        if (f.getMessageCount() > 0) {
                            int count = f.getMessageCount();
                            int i = 0;
                            stamp = MessageModel.getLastEmailID(f.getName() , settings.getAccount().getID());
                            for (int k = stamp; k < count; k++) {
                                Message msg = f.getMessage(k);
                                msgModel.setID(msg.getMessageNumber());
                                msgModel.setDate(new java.sql.Date(msg.getReceivedDate().getTime()));
                                msgModel.setFolderID(f.getName());
                                Address[] rec = msg.getFrom();
                                sb.setLength(0);
                                if(rec != null){
                                    for (Address addres : rec) {
                                        sb.append(addres.toString()).append(";");
                                    }
                                }
                                
                                msgModel.setFrom(sb.toString());
                                msgModel.setIsRead(false);
                                msgModel.setSubject(msg.getSubject());
                                msgModel.setContent(getMessageText(msg));
                                msgModel.setAccountID(settings.getAccount().getID());
                                msgModel.add();
                            }
                        }
                        f.close(false);
                    }
                    
                } catch (MessagingException ex) {
                    Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        treeModel.reload();
        tree.setRootVisible(false);
    }
    public String getMessageText(Message message) {
        String result = "";
        if (message instanceof MimeMessage) {
            try {
                MimeMessage m = new MimeMessage((MimeMessage) message);
                Object contentObject = m.getContent();
                if (contentObject instanceof Multipart) {
                    BodyPart clearTextPart = null;
                    BodyPart htmlTextPart = null;
                    Multipart content = (Multipart) contentObject;
                    int count = content.getCount();
                    for (int i = 0; i < count; i++) {
                        BodyPart part = content.getBodyPart(i);
                        if (part.isMimeType("text/plain")) {
                            clearTextPart = part;
                            break;
                        } else if (part.isMimeType("text/html")) {
                            htmlTextPart = part;
                        }
                    }

                    if (clearTextPart != null) {
                        result = (String) clearTextPart.getContent();
                    } else if (htmlTextPart != null) {
                        String html = (String) htmlTextPart.getContent();
                        result = Jsoup.parse(html).text();
                    }

                } else if (contentObject instanceof String) // a simple text message
                {
                    result = (String) contentObject;
                } else // not a mime message
                {
                    result = null;
                }
            } catch (IOException ex) {
                Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(MailerReciver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }
}
