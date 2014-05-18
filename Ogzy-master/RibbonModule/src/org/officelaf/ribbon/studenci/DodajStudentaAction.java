/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.studenci;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.studenci.DodajEdytujStudentaPanel;
import org.gui.studenci.StudentsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class DodajStudentaAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        StudentsListTopComponent window = (StudentsListTopComponent) WindowManager.getDefault().findTopComponent("StudentsListTopComponent");
        DodajEdytujStudentaPanel panel = new DodajEdytujStudentaPanel();
        Object[] options = {
            "Dodaj", "Anuluj"
        };
        int input = JOptionPane.showOptionDialog(window, panel, "Dodaj studenta", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (input == JOptionPane.OK_OPTION) {
            panel.addStudent();
        }
    }
}
