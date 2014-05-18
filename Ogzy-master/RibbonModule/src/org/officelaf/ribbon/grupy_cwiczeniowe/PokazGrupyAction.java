/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.grupy_cwiczeniowe;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class PokazGrupyAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupsListTopComponent window = (GroupsListTopComponent) WindowManager.getDefault().findTopComponent("GroupsListTopComponent");
        window.open();
        window.requestActive();
    }
}
