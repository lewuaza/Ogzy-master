package org.mailer;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author lewuaza
 */
public class MailerSender {
    private final Session session;
    private final MailerSettings settings;
    public MailerSender( MailerSettings settings)
    {
        this.settings = settings;
        this.settings.setSMTPAuth(true);
        this.session = Session.getDefaultInstance(settings.getProperties());
    }
    public String[] getRecipients(String recipients)
    {
        return recipients.split(";");
    }
    public boolean sendEmail(String from, String[] to , String subject , String msg)
    {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for(String s:to){
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(s));
            }
            message.setSubject(subject);
            message.setText(msg);
            
            Transport transport = session.getTransport("smtps");
            transport.connect(settings.getSMTP(), settings.getUsername(), settings.getPassword());
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(MailerSender.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
