/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.studenci;

import org.database.models.Student;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.studenci.StudentsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class UsunStudentaAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        StudentsListTopComponent window = (StudentsListTopComponent) WindowManager.getDefault().findTopComponent("StudentsListTopComponent");
        if (!window.isOpened()) {
            window.open();
            window.requestActive();
        } else {
            int wybranyWiersz = window.getTabelaStudentow().getSelectedRow();
            if (wybranyWiersz == -1) {
                JOptionPane.showMessageDialog(window, "Nie wybrano studenta!", "Usuwanie studenta - BŁĄD", JOptionPane.ERROR_MESSAGE);
            } else {
                Object[] options = {
                    "Tak", "Nie"
                };
                int input = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz usunąć wybranego studenta", "Potwierdź usunięcie studenta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (input == JOptionPane.OK_OPTION) {
                    Integer id = (Integer) window.getTabelaStudentow().getValueAt(wybranyWiersz, 0);
                    Student.delete(id);
                    window.componentOpened();
                    window.open();
                    window.requestActive();
                }
            }
        }
    }
}
