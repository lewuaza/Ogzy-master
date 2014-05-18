/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mailer;

/**
 *
 * @author lewuaza
 */
public class Mailer {

    
	public static void main(String[] args) {
            /*
            MailerSettings settings = new MailerSettings("imaps" , "szaniec.marcin@gmail.com" , "Tatry1991");
            settings.configure("imap.gmail.com", "" , "imaps");
            MailerReciver rec = new MailerReciver(settings);
            Folder[] list = rec.getFolderList();
            for(Folder f: list)
            {
               try {
                    f.open(Folder.HOLDS_FOLDERS);
                    System.out.println(f.getFullName());
                    if("[Gmail]/Spam".equals(f.getFullName()))
                    {
                        Message[] msgs = rec.getEmailsinFolder(f);
                        for(Message msg : msgs)
                        {
                            System.out.println(msg.getSentDate().toString());
                            System.out.println(msg.getSubject());
                            try {
                                if(msg.getContentType().contains("multipart"))
                                {
                                    Multipart multipart = (Multipart) msg.getContent();
                                    for(int i=0;i<multipart.getCount();i++) {
                                        BodyPart bodyPart = multipart.getBodyPart(i);
                                        if (bodyPart.isMimeType("text/*")) {
                                            String s = (String) bodyPart.getContent();
                                            System.out.println(s);                                       
                                        }
                                    }    
                                }
                                else
                                {
                                    JFrame aab = new JFrame();
                                    aab.setSize(500, 500);
                                    JEditorPane aaa = new JEditorPane();
                                    aaa.setContentType("text/html"); 
                                    aaa.setText(msg.getContent().toString());
                                    aaa.setVisible(true);
                                    aab.add(aaa);
                                    aab.setVisible(true);
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                    }
                    f.close(false);
                } catch (MessagingException ex) {
                    Logger.getLogger(Mailer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            */
	}
}
