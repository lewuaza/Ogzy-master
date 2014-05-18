/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.mailer;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.gui.mailer.InboxListTopComponent;
import org.gui.mailer.MailerTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author lewuaza
 */
public class InboxAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        final MailerTopComponent window = (MailerTopComponent) WindowManager.getDefault().findTopComponent("MailerMainTopComponent");
                new Thread(new Runnable(){
            @Override
            public void run()
            {
                window.setupForm();
            }
                }).start();
        window.open();
        window.requestActive();
    }
    
}
