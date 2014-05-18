/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.mailer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.mailer.MailerTopComponent;
import org.gui.mailer.SendEmailPanel;
import org.gui.mailer.Settings;
import org.openide.windows.WindowManager;

/**
 *
 * @author lewuaza
 */
public class NewMessageAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        MailerTopComponent window = (MailerTopComponent) WindowManager.getDefault().findTopComponent("MailerMainTopComponent");
        SendEmailPanel panel = new SendEmailPanel();
        Object[] options = {
            "Wyślij", "Anuluj"
        };
        int input = JOptionPane.showOptionDialog(window, panel, "Wysyłanie nowej wiadomości", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (input == JOptionPane.OK_OPTION) {
            panel.sendEmail();
        }
    }
    
}
