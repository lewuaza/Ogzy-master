/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.przedmioty;

import org.gui.przedmioty.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.windows.WindowManager;
//import org.openide.windows.WindowManager;

/**
 *
 * @author Mariushrek
 */
public class PokazPrzedmiotyAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        PokazPrzedmiotyTopComponent w = (PokazPrzedmiotyTopComponent) WindowManager.getDefault().findTopComponent("PokazPrzedmiotyTopComponent");
        w.open();
        w.Aktualizuj();
        w.requestActive();
    }
}
