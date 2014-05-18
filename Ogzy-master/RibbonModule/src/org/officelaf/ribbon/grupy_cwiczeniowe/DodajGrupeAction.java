/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.grupy_cwiczeniowe;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.grupy_cwiczeniowe.DodajEdytujGrupeCwiczeniowaPanel;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class DodajGrupeAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupsListTopComponent window = (GroupsListTopComponent) WindowManager.getDefault().findTopComponent("GroupsListTopComponent");
        DodajEdytujGrupeCwiczeniowaPanel panel = new DodajEdytujGrupeCwiczeniowaPanel();
        Object[] options = {
            "Dodaj", "Anuluj"
        };
        int input = JOptionPane.showOptionDialog(window, panel, "Dodaj grupę ćwiczeniową", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (input == JOptionPane.OK_OPTION) {
            panel.addGroup();
        }
    }
}
