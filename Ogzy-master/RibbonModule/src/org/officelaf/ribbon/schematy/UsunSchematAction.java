/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.schematy;

import org.database.models.GrupaOcen;
import org.database.models.Przedmiot;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.schematy.SchemeTopComponent;
import org.gui.schematy.UsunSchematPanel;
import org.gui.schematy.ZmianaGrupyPanel;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class UsunSchematAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        SchemeTopComponent top = (SchemeTopComponent) WindowManager.getDefault().findTopComponent("SchemeTopComponent");
        UsunSchematPanel panel = new UsunSchematPanel();
        Object[] op = {"Usuń"," Anuluj"};
        int n =JOptionPane.showOptionDialog(top, panel,"Usuwanie schematu", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0]);
        if(n==0){
            GrupaOcen dousuniecia = panel.getSelectedGrupa();
            List<Przedmiot> przedmioty = Przedmiot.getPrzedmioty(0);
            List<Przedmiot> pogrupie = new ArrayList<Przedmiot>();
            for(Przedmiot p: przedmioty){
                if(p.getGrupaOcen().getId() == dousuniecia.getId()) pogrupie.add(p);
            }
            //w po grupie mamy wszystkie przedmioty które mają wybraną te grupe
            for(Przedmiot p: pogrupie){
                //zmiana grupy dla każdego
                ZmianaGrupyPanel panel1 = new ZmianaGrupyPanel(p.getNazwa(),dousuniecia);
                Object[] op1 = {"Wybierz","Anuluj"};
                int n1 = JOptionPane.showOptionDialog(top, panel1, "Zmiana schematu", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op1, op1[0]);
                if(n1 == 0){
                    p.setGrupaOcen(panel1.getSelectedGrupa());
                    Przedmiot.updatePrzedmiot(p);
                }else{
                    return;
                }
                
            }
            
            GrupaOcen.deleteGrupaOcen(dousuniecia.getId());
            top.componentOpened();
        }
    }
    
}
