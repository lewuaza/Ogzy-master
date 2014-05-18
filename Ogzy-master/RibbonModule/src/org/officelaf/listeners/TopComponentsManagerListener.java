/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.listeners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.gui.MainTopComponent;
import org.gui.grupy_cwiczeniowe.GroupsListTopComponent;
import org.gui.grupy_cwiczeniowe.GrupaCwiczeniowaTopComponent;
import org.gui.obecnosci.PresenceTableTopComponent;
import org.gui.oceny.NotesTableTopComponent;
import org.gui.oceny.OcenyKoncoweTopComponent;
import org.gui.przedmioty.PokazPrzedmiotyTopComponent;
import org.gui.schematy.SchemeTopComponent;
import org.gui.studenci.StudentsListTopComponent;
import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;
import org.officelaf.ribbon.BandsButtons;
import org.officelaf.ribbon.MainRibbon;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class TopComponentsManagerListener implements PropertyChangeListener{

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("activated")){
            //gdy aktywowano component
            TopComponent top = WindowManager.getDefault().getRegistry().getActivated();
            if(top instanceof MainTopComponent){
                MainTopComponentActivated((MainTopComponent)top);
            }
            else if(top instanceof GrupaCwiczeniowaTopComponent){
                SingleGroupTopComponentActivated((GrupaCwiczeniowaTopComponent)top);
            }
            else if(top instanceof GroupsListTopComponent){
                GroupsListTopComponentActivated((GroupsListTopComponent)top);
            }
            else if(top instanceof PresenceTableTopComponent){
                PresenceTableTopComponentActivated((PresenceTableTopComponent)top);
            }
            else if(top instanceof NotesTableTopComponent){
                NotesTableTopComponentActivated((NotesTableTopComponent)top);
            }
            else if(top instanceof OcenyKoncoweTopComponent){
                OcenyKoncoweTopComponentActivated((OcenyKoncoweTopComponent)top);
            }
            else if(top instanceof PokazPrzedmiotyTopComponent){
                PokazPrzedmiotyTopComponentActivated((PokazPrzedmiotyTopComponent)top);
            }
            else if(top instanceof StudentsListTopComponent){
                StudentsListTopComponentActivated((StudentsListTopComponent)top);
            }
        }
        else if(evt.getPropertyName().equals("tcOpened")){
            //gdy otwarto top component
        }
        else if(evt.getPropertyName().equals("tcClosed")){
            //gdy zamknięto top component
        }
        else if(evt.getPropertyName().equals("opened")){
            //gdy otwarto component
        }
    }
    
    public static void MainTopComponentActivated(MainTopComponent top){
  
        JTable topTable = top.getTable();
        DefaultTableModel topTableModel = top.getTableModel();
        int selectedRow = topTable.getSelectedRow();
        int selectedColumn = topTable.getSelectedColumn();
        Object topValue = null;
        
        if(selectedRow != -1 && selectedColumn != -1) topValue = topTableModel.getValueAt(selectedRow, selectedColumn);
        
        if(topValue != null || (topValue == null && selectedColumn == -1)){
            if(topTable.getSelectedColumn() <= 0){
                BandsButtons.disableManageTermsButtons();
                BandsButtons.disableAllNotesButtons();
                BandsButtons.disableAllPresenceButtons();
            }else{
                BandsButtons.disableButton(BandsButtons.ADD_TERM);
                BandsButtons.enableButton(BandsButtons.REMOVE_TERM);
                BandsButtons.disableManageNotesButtons();
                BandsButtons.disableManagePresenceButtons();
            }
        }else{
            BandsButtons.enableButton(BandsButtons.ADD_TERM);
            BandsButtons.disableButton(BandsButtons.REMOVE_TERM);
            BandsButtons.disableAllNotesButtons();
            BandsButtons.disableAllPresenceButtons();
        }
    }

    public static void SingleGroupTopComponentActivated(GrupaCwiczeniowaTopComponent mainTopComponent) {
        BandsButtons.disableManageTermsButtons();
        BandsButtons.disableManageNotesButtons();
        BandsButtons.disableManagePresenceButtons();
    }

    public static void GroupsListTopComponentActivated(GroupsListTopComponent groupsListTopComponent) {
        if(groupsListTopComponent.getTabelaGrupCwiczeniowych().getSelectedRow() < 0){
            BandsButtons.disableButton(BandsButtons.REMOVE_GROUP);
        }else{
            BandsButtons.enableButton(BandsButtons.REMOVE_GROUP);
        }
    }

    public static void PresenceTableTopComponentActivated(PresenceTableTopComponent presenceTableTopComponent) {
        BandsButtons.disableManageTermsButtons();
        BandsButtons.disableManagePresenceButtons();
        BandsButtons.disableButton(BandsButtons.SHOW_PRESENCE_TABLE);
        BandsButtons.enableButton(BandsButtons.REMOVE_PRESENCE);
    }

    public static void NotesTableTopComponentActivated(NotesTableTopComponent notesTableTopComponent) {
        BandsButtons.enableButton(BandsButtons.SHOW_NOTES_SUM);
        BandsButtons.disableButton(BandsButtons.SHOW_NOTES_TABLE);
        
        EnableDisableManageColumsButtons(notesTableTopComponent);
        
        BandsButtons.disableManageTermsButtons();
        BandsButtons.disableAllPresenceButtons();
    }
    
    public static void EnableDisableManageColumsButtons(NotesTableTopComponent notesTableTopComponent){
        if(notesTableTopComponent.getStudentsTable().getSelectedRow() < 0){
            BandsButtons.disableManageColumnsNotesButtons();
        }else{
            BandsButtons.enableManageColumnsNotesButtons();
        }
    }

    public static void OcenyKoncoweTopComponentActivated(OcenyKoncoweTopComponent ocenyKoncoweTopComponent) {
        BandsButtons.disableAllNotesButtons();
        BandsButtons.disableAllPresenceButtons();
        BandsButtons.disableManageTermsButtons();
    }

    public static void PokazPrzedmiotyTopComponentActivated(PokazPrzedmiotyTopComponent pokazPrzedmiotyTopComponent) {
        if(pokazPrzedmiotyTopComponent.getTabelaPrzedmiotow().getSelectedRow() < 0){
            BandsButtons.disableButton(BandsButtons.REMOVE_SUBJECT);
        }else{
            BandsButtons.enableButton(BandsButtons.REMOVE_SUBJECT);
        }
    }

    public static void StudentsListTopComponentActivated(StudentsListTopComponent studentsListTopComponent) {
        if(studentsListTopComponent.getTabelaStudentow().getSelectedRow() < 0){
            BandsButtons.disableButton(BandsButtons.REMOVE_STUDENT);
        }else{
            BandsButtons.enableButton(BandsButtons.REMOVE_STUDENT);
        }
    }
}
