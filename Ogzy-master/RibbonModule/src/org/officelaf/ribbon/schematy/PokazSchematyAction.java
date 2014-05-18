/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.schematy;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class PokazSchematyAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = WindowManager.getDefault().findTopComponent("SchemeTopComponent");
        top.open();
        top.requestActive();
    }
    
}
