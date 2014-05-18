/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.oceny;

import org.database.models.GrupaCwiczeniowa;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.gui.MainTopComponent;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.gui.grupy_cwiczeniowe.GrupaCwiczeniowaTopComponent;
import org.gui.obecnosci.PresenceTableTopComponent;
import org.gui.oceny.NotesTableTopComponent;
import org.gui.oceny.OcenyMainTopComponent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class PokazTabeleOcenAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        
        GrupaCwiczeniowaTopComponent top = (GrupaCwiczeniowaTopComponent) WindowManager.getDefault().findTopComponent("GrupaCwiczeniowaTopComponent");
        TopComponent top1 = TopComponent.getRegistry().getActivated();
        MainTopComponent maintop = null;
        GroupsListTopComponent grouptop = null;
        PresenceTableTopComponent presencetop = null;
        String maintop_value = null;
        String grouptop_value_g = null;
        String grouptop_value_p = null;
        if(top1 instanceof MainTopComponent){
            maintop = (MainTopComponent)top1;
            if(maintop.getTable().getSelectedRow() != -1 && maintop.getTable().getSelectedColumn()>0)
                maintop_value = (String) maintop.getTableModel().getValueAt(maintop.getTable().getSelectedRow(), maintop.getTable().getSelectedColumn());
        }else if(top1 instanceof GroupsListTopComponent){
            grouptop = (GroupsListTopComponent) top1;
            if(grouptop.getTabelaGrupCwiczeniowych().getSelectedRow() != -1)
                grouptop_value_g = (String) grouptop.getTabelaGrupCwiczeniowych().getModel().getValueAt(grouptop.getTabelaGrupCwiczeniowych().getSelectedRow(), 1);
                grouptop_value_p = (String) grouptop.getTabelaGrupCwiczeniowych().getModel().getValueAt(grouptop.getTabelaGrupCwiczeniowych().getSelectedRow(), 2);
        }else if(top1 instanceof PresenceTableTopComponent){
            presencetop = (PresenceTableTopComponent)top1;
            grouptop_value_g = presencetop.getGroupName();
            grouptop_value_p = presencetop.getSubjectName();
        }
        
        if(top.isOpened() || (maintop != null && maintop_value !=null) || (grouptop !=null && grouptop_value_g != null) || (presencetop !=null)){
            //W każdym z 3 przypadków można dostać grupę dla której chcemy wyświetlić oceny
            GrupaCwiczeniowa grupa = null;
            
            //1.jeśli jest otwarte okno pojedynczej grupy
            if(top.isOpened()){
                grupa = top.getGrupa();
            }
            //2. jeśli jest wybrana grupa na planie zajęć
            else if(maintop != null && maintop_value !=null){
                List<GrupaCwiczeniowa> grupy= GrupaCwiczeniowa.getAll();
                for(GrupaCwiczeniowa g: grupy){
                    if(g.getNazwa().equals(maintop_value.substring(6, maintop_value.indexOf("<br>")))
                            && g.getPrzedmiot().getNazwa().equals(maintop_value.substring(maintop_value.indexOf("<br>")+4, maintop_value.length()-7))){
                        grupa = g;
                        break;
                    }
                }
            }
            //3. jeśli jest wybrana grupa w liście grupa
            else if((grouptop !=null && grouptop_value_g != null) || presencetop !=null){
                List<GrupaCwiczeniowa> grupy= GrupaCwiczeniowa.getAll();
                for(GrupaCwiczeniowa g: grupy){
                    if(g.getNazwa().equals(grouptop_value_g) && g.getPrzedmiot().getNazwa().equals(grouptop_value_p)){
                        grupa = g;
                        break;
                    }
                }
            }
            
            // w tym momencie w obiekt grupa jest uzupełniony
            NotesTableTopComponent notestop = (NotesTableTopComponent) WindowManager.getDefault().findTopComponent("NotesTableTopComponent");
            notestop.setGrupa(grupa); //ustawiam tabele ocen
            //wyswietlam tabele
            notestop.open();
            notestop.requestActive();
            
            //OcenyMainTopComponent oceny = (OcenyMainTopComponent) WindowManager.getDefault().findTopComponent("OcenyMainTopComponent");
            
            //System.out.println("to jest to czego chce " + oceny);
            //oceny.setGrupa(grupa); //ustawiam tabele ocen
            
            //wyswietlam tabele
           // oceny.open();
           // oceny.requestActive();
        }else{
            JOptionPane.showMessageDialog(top, "Wybierz w dowolnym miejscu grupę aby otworzyć okno jej ocen", "Oceny grupy - BŁĄD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
