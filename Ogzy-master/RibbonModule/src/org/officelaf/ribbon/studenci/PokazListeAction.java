/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.studenci;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.gui.studenci.StudentsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class PokazListeAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        //.open() - otwarcie okna
        //.close() - zamkniecie okna
        //.requestActive() - aktywowanie okna jeśli jest otwarte, czyli zmiana karty
        StudentsListTopComponent window = (StudentsListTopComponent) WindowManager.getDefault().findTopComponent("StudentsListTopComponent");
        window.open();
        window.requestActive();
    }
}
