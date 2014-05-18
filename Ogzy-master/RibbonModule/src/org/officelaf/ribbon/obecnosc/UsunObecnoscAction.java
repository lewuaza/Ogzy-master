/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.obecnosc;

import org.database.models.Obecnosc;
import org.database.models.Termin;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.gui.obecnosci.DeletePresencePanel;
import org.gui.obecnosci.PresenceTableTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class UsunObecnoscAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        PresenceTableTopComponent top = (PresenceTableTopComponent) WindowManager.getDefault().findTopComponent("PresenceTableTopComponent");
        if(top.isOpened()){
            top.requestActive();
            Object[] op = {"Usuń","Anuluj"};
            DeletePresencePanel panel = new DeletePresencePanel(top.getDates());
            int n = JOptionPane.showOptionDialog(top, panel, "Usuwanie kolumny obecności", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0]);
            if(n==0){
                Obecnosc.deleteObecnosc(top.getTermId(), panel.getSelectedValue());
                LinkedList<Termin> terms = Termin.getAllTerms();
                for(Termin t:terms){
                    if(t.getId()==top.getTermId()){
                        top.setTerm(t);
                        break;
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog(top, "Przejdź do tabeli ocen korzystając z przycisku obok, aby móc usuwać obecności", "Obecności - BŁĄD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
