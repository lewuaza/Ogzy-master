/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.oceny;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.gui.oceny.NotesTableTopComponent;
import org.gui.oceny.OcenyKoncoweTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class PodsumowanieAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
            NotesTableTopComponent notestop = (NotesTableTopComponent) WindowManager.getDefault().findTopComponent("NotesTableTopComponent");
            if(notestop.isOpened()) {
                OcenyKoncoweTopComponent oceny = (OcenyKoncoweTopComponent) WindowManager.getDefault().findTopComponent("OcenyKoncoweTopComponent");
                oceny.setArguments(notestop.getGrupaCwiczeniowa(), notestop.getStudentsList());
                oceny.open();
                oceny.requestActive();
            }
            
    }
    
}
