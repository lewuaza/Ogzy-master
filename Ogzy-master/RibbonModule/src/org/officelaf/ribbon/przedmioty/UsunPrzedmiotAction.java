/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.przedmioty;

import org.database.models.Przedmiot;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.przedmioty.PokazPrzedmiotyTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Mariushrek
 */
public class UsunPrzedmiotAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        PokazPrzedmiotyTopComponent window = (PokazPrzedmiotyTopComponent) WindowManager.getDefault().findTopComponent("PokazPrzedmiotyTopComponent");
        if (!window.isOpened()) {
            window.open();
            window.requestActive();
        } else {
            int wybranyWiersz = window.getTabelaPrzedmiotow().getSelectedRow();
            if (wybranyWiersz == -1) {
                JOptionPane.showMessageDialog(window, "Nie wybrano przedmiotu!", "Usuwanie przedmiotu - BŁĄD", JOptionPane.ERROR_MESSAGE);
            } else {
                Object[] options = {
                    "Tak", "Nie"
                };
                int input = JOptionPane.showOptionDialog(null, "Czy na pewno chcesz usunąć wybrany przedmiot?", "Potwierdź usunięcie przedmiotu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
                if (input == JOptionPane.OK_OPTION) {
                    Integer id = (Integer) window.getTabelaPrzedmiotow().getValueAt(wybranyWiersz, 0);
                    Przedmiot.delPrzedmiot(id);
                    window.componentOpened();
                    window.open();
                    window.Aktualizuj();
                    window.requestActive();
                }
            }
        }
    }
    
}
