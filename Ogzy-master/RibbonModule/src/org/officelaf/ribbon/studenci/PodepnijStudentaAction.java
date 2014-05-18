/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.studenci;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.studenci.PodepnijStudentaPanel;
import org.gui.studenci.StudentsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class PodepnijStudentaAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        StudentsListTopComponent window = (StudentsListTopComponent) WindowManager.getDefault().findTopComponent("StudentsListTopComponent");
        if (!window.isOpened()) {
            window.open();
            window.requestActive();
        } else {
            int wybranyWiersz = window.getTabelaStudentow().getSelectedRow();
            if (wybranyWiersz == -1) {
                JOptionPane.showMessageDialog(window, "Nie wybrano studenta!", "Podpinanie studenta - BŁĄD", JOptionPane.ERROR_MESSAGE);
            } else {
                Object[] options = {
                    "Tak", "Nie"
                };
                Integer studentId = (Integer) window.getTabelaStudentow().getValueAt(wybranyWiersz, 0);
                PodepnijStudentaPanel panel = new PodepnijStudentaPanel(studentId);
                int input = JOptionPane.showOptionDialog(window, panel, "Podepnij studenta", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (input == JOptionPane.OK_OPTION) {
                    panel.podepnijStudenta(studentId);
                }
                window.componentOpened();
                window.open();
                window.requestActive();
            }
        }
    }
}
