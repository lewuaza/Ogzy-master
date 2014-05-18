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
import org.gui.DodajTerminPanel;
import org.gui.MainTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class DodajTerminAction extends AbstractAction{
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = TopComponent.getRegistry().getActivated();
        if(top instanceof MainTopComponent){
            MainTopComponent maintop = (MainTopComponent)top;
            JTable table = maintop.getTable();
            DefaultTableModel model = maintop.getTableModel();
            if(table.getSelectedRow() != -1 && table.getSelectedColumn() > 0){
                if(model.getValueAt(table.getSelectedRow(), table.getSelectedColumn()) == null)
                {
                        String data = (String)model.getValueAt(table.getSelectedRow(), 0);
                        String data_start = data.substring(0, 5);
                        String data_stop = data.substring(8, data.length());
                        
                        DodajTerminPanel panel = new DodajTerminPanel();
                        //wywal okienko ktore uzupełni panel o grupę.
                        
                        Object[] options = {
                            "OK", "Anuluj"
                        };
                        JOptionPane.showOptionDialog(maintop, panel, "Dodawanie terminu", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                        
                        if(panel.getSelectedIndexList() == -1) return;
                        //dodaj termin do bazy
                        Termin.dodajTermin(new Termin(0, panel.getSelectedGroup(), Termin.DZIEN_TYG.values()[table.getSelectedColumn()-1], data_start, data_stop));
                        //odswież liste
                        maintop.componentOpened();
                }
                else{
                   JOptionPane.showMessageDialog(maintop, "Ten termin jest zajęty!", "Dodawanie termiu - BŁĄD", JOptionPane.ERROR_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(maintop, "Wybierz wolny termin na planie zajęć", "Dodawanie termiu - BŁĄD", JOptionPane.ERROR_MESSAGE);
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
