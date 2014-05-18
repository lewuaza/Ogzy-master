/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.obecnosc;

import org.database.models.Termin;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.MainTopComponent;
import org.gui.grupy_cwiczeniowe.GrupaCwiczeniowaTopComponent;
import org.gui.obecnosci.PresenceTableTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class SprawdzObecnoscAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = TopComponent.getRegistry().getActivated();
        MainTopComponent maintop = null;
        GrupaCwiczeniowaTopComponent grouptop = null;
        if(top instanceof MainTopComponent) maintop = (MainTopComponent) top;
        else if(top instanceof GrupaCwiczeniowaTopComponent) grouptop = (GrupaCwiczeniowaTopComponent) top;
        if(maintop != null && maintop.getTable().getSelectedRow() != -1 && maintop.getTable().getSelectedColumn() > 0 && maintop.getTableModel().getValueAt(maintop.getTable().getSelectedRow(), maintop.getTable().getSelectedColumn()) != null){
            String data = (String) maintop.getTableModel().getValueAt(maintop.getTable().getSelectedRow(), 0);
            String godzina_start = data.substring(0, 5);
            String godzina_stop = data.substring(8, data.length());
            Termin.DZIEN_TYG tyg = Termin.DZIEN_TYG.values()[maintop.getTable().getSelectedColumn()-1];
            LinkedList<Termin> terms = Termin.getAllTerms();
            Termin term = null;
            for(Termin t: terms){
                if(t.getGodzina_start().equals(godzina_start) && t.getGodzina_stop().equals(godzina_stop) && t.getDzien_tygodnia()==tyg){
                    term = t;
                    break;
                }
            }
            //w term jest teraz zaznaczony termin
            PresenceTableTopComponent ptop = (PresenceTableTopComponent) WindowManager.getDefault().findTopComponent("PresenceTableTopComponent");
            ptop.setTerm(term);
            ptop.open();
            ptop.requestActive();
        }else if(grouptop !=null && grouptop.getTable().getSelectedRow() != -1){
            String data = (String) grouptop.getTableModel().getValueAt(grouptop.getTable().getSelectedRow(), 1);
            String godzina_start = data.substring(0, 5);
            String godzina_stop = data.substring(8, data.length());
            Termin.DZIEN_TYG tyg = Termin.DZIEN_TYG.valueOf((String) grouptop.getTableModel().getValueAt(grouptop.getTable().getSelectedRow(), 0));
            LinkedList<Termin> terms = Termin.getAllTerms();
            Termin term = null;
            for(Termin t: terms){
                if(t.getGodzina_start().equals(godzina_start) && t.getGodzina_stop().equals(godzina_stop) && t.getDzien_tygodnia()==tyg){
                    term = t;
                    break;
                }
            }
            // tu znajduje sie termin wypełniony
            PresenceTableTopComponent ptop = (PresenceTableTopComponent) WindowManager.getDefault().findTopComponent("PresenceTableTopComponent");
            ptop.setTerm(term);
            ptop.open();
            ptop.requestActive();
        }
        else if(top instanceof PresenceTableTopComponent){
            JOptionPane.showMessageDialog(maintop, "Już jesteś we właściwym oknie głupku :D", "Obecności - BŁĄD", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(maintop, "Wybierz termin na planie zajęć lub w bezpośrednio w grupe aby sprawdzić dla niego obecność", "Obecności - BŁĄD", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
