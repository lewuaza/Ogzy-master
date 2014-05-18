package org.mailer;

import java.util.Properties;
import org.mailer.models.Account;

/**
 *
 * @author lewuaza
 */
public final class MailerSettings {
    private final Properties properties;
    private String storeType;
    private String port;
    private String username;
    private String password;
    private String protocol;
    private String hostname;
    private String smtpHostname;
    private Account acc;
    public MailerSettings()
    {
        properties = new Properties();
    }
    public MailerSettings(String storeType , String username , String password)
    {
        properties = new Properties();
        this.storeType = storeType;
        this.username = username;
        this.password = password;
    }
    public MailerSettings(Account acc , String storeType , String port)
    {
        properties = new Properties();
        this.storeType = storeType;
        setUsername(acc.getLogin());
        setPassword(acc.getPassword());
        setHost(acc.getPOP3Hostname());
        setPort(port);
        setSMTP(acc.getSMTP());
        this.acc = acc;
    }
    public Properties getProperties()
    {
        return this.properties;
    }
    public void configure(String hostname , String port,String protocol , String smtpHostname)
    {
        setHost(hostname);
        setPort(port);
        setProtocol(protocol);
        setSMTP(smtpHostname);
    }
    public final void setHost(String hostname)
    {
        if(!"".equals(hostname)){
            if (!properties.contains("mail.pop3.host"))
                properties.put("mail.pop3.host" , hostname);
            else
                properties.setProperty("mail.pop3.host", hostname);
            this.hostname = hostname;
        }
        
    }
    public final void setPort(String port)
    {
        if(!"".equals(port)){
            if (!properties.contains("mail.pop3.port"))
                properties.put("mail.pop3.port" , port);
            else
                properties.setProperty("mail.pop3.port", port);
            this.port = port;
        }
    }
    public void setProtocol(String protocol)
    {
        if(!"".equals(protocol)){
            if (!properties.contains("mail.store.protocol"))
                properties.put("mail.store.protocol" , protocol);
            else
                properties.setProperty("mail.store.protocol",protocol);
            this.protocol = protocol;
        }
            
    }
    public void setSMTP(String hostname)
    {
        if(!"".equals(hostname)){
            if(!properties.contains("mail.smtp.host"))
                properties.put("mail.smtp.host" , hostname);
            else
                properties.setProperty("mail.smtp.host" , hostname);
            this.smtpHostname = hostname;
        }
    }
    public String getSMTP()
    {
        return this.smtpHostname;
    }
    public String getStoreType()
    {
        return this.storeType;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getUsername()
    {
        return this.username;
    }
    public final void setPassword(String password)
    {
        if(!"".equals(password)){
            if (!properties.contains("mail.password"))
                properties.put("mail.password" , password);
            else
                properties.setProperty("mail.password",password);
            this.password = password;
        }
    }
    public void setUsername(String username)
    {
        
        if(!"".equals(password)){
            if (!properties.contains("mail.user"))
                properties.put("mail.user" , username);
            else
                properties.setProperty("mail.user",username);
            this.username = username;
        }
    }
    public String getHostName()
    {
        return this.hostname;
    }
    public Account getAccount()
    {
        return this.acc;
    }
    public void setSMTPAuth(boolean value)
    {
        if (!properties.contains("mail.user"))
            properties.put("mail.smtps.auth", value ? "true" : "false");
        else
            properties.put("mail.smtps.auth", value ? "true" : "false");
    }
            
}
