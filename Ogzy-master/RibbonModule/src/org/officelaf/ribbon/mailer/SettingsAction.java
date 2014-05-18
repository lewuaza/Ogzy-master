/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.mailer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.database.installation.DatabaseInstaller;
import org.gui.grupy_cwiczeniowe.DodajEdytujGrupeCwiczeniowaPanel;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.gui.mailer.InboxListTopComponent;
import org.gui.mailer.MailerTopComponent;
import org.gui.mailer.Settings;
import org.openide.windows.WindowManager;

/**
 *
 * @author lewuaza
 */
public class SettingsAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        DatabaseInstaller installer = new DatabaseInstaller();
        installer.execute();
        InboxListTopComponent window = (InboxListTopComponent) WindowManager.getDefault().findTopComponent("MailerListMainTopComponent");
        Settings panel = new Settings();
        Object[] options = {
            "Zapisz", "Anuluj"
        };
        int input = JOptionPane.showOptionDialog(window, panel, "Konfiguracja skrzynki mailowej", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (input == JOptionPane.OK_OPTION) {
            panel.save();
        }
        if (window != null)
            window.setupForm();
    }
}
