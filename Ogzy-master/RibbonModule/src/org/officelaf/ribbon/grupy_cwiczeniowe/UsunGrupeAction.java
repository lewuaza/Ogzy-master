/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.officelaf.ribbon.grupy_cwiczeniowe;

import org.database.models.GrupaCwiczeniowa;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Ja
 */
public class UsunGrupeAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        GroupsListTopComponent window = (GroupsListTopComponent) WindowManager.getDefault().findTopComponent("GroupsListTopComponent");
        if (!window.isOpened()) {
            window.open();
            window.requestActive();
        } else {
            int wybranyWiersz = window.getTabelaGrupCwiczeniowych().getSelectedRow();
            if (wybranyWiersz == -1) {
                JOptionPane.showMessageDialog(window, "Nie wybrano grupy!", "Usuwanie grupy ćwiczeniowej - BŁĄD", JOptionPane.ERROR_MESSAGE);
            } else {
                Object[] options = {
                    "Tak", "Nie"
                };
                int input = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz usunąć wybraną grupę", "Potwierdź usunięcie grupy", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (input == JOptionPane.OK_OPTION) {
                    Integer id = (Integer) window.getTabelaGrupCwiczeniowych().getValueAt(wybranyWiersz, 0);
                    GrupaCwiczeniowa.delete(id);
                    window.componentOpened();
                    window.open();
                    window.requestActive();
                }
            }
        }
    }
}
