/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.plan;

import org.database.models.Termin;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.gui.MainTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class UsunTerminAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = TopComponent.getRegistry().getActivated();
        if(top instanceof MainTopComponent){
            MainTopComponent maintop = (MainTopComponent)top;
            JTable table = maintop.getTable();
            DefaultTableModel model = maintop.getTableModel();
            if(table.getSelectedRow() != -1 && table.getSelectedColumn() > 0){
                if(model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()) != null)
                {
                        String data = (String)model.getValueAt(table.getSelectedRow(), 0);
                        String data_start = data.substring(0, 5);
                        String data_stop = data.substring(8, data.length());
                        
                        Object[] options = {
                            "Tak", "Nie"
                        };
                        int op = JOptionPane.showOptionDialog(maintop,"Czy napewno chcesz usunąć termin?", "Usuwanie terminu", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        
                        // -1 krzyżyk 1 nie
                        if(op == -1 || op == 1) return;
                        
                        //usun termin z bazy
                        Termin.usunTermin(Termin.DZIEN_TYG.values()[table.getSelectedColumn()-1], data_start, data_stop);
                        //odswież liste
                        maintop.componentOpened();
                }
                else{
                   JOptionPane.showMessageDialog(maintop, "Ten termin jest pusty!", "Usuwanie termiu - BŁĄD", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(maintop, "Wybierz termin na planie zajęć, który chesz usunąć", "Usuwanie termiu - BŁĄD", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            MainTopComponent main = (MainTopComponent) WindowManager.getDefault().findTopComponent("MainTopComponent");
            if(main.isOpened())
                main.requestActive();
            else{
                main.open();
                main.requestActive();
            }
            this.actionPerformed(e);
        }
    }
    
}
