/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.plan;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.gui.MainTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class PokazPlanAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        MainTopComponent w = (MainTopComponent) WindowManager.getDefault().findTopComponent("MainTopComponent");
        w.open();
        w.requestActive();
    }
    
}
