/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon;

import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.ribbon.RibbonElementPriority;

/**
 *
 * @author Kuchtar
 */
public class BandsButtons {
    public static final AbstractCommandButton SHOW_PLAN = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.TERMS, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_TERM = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.TERMS, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_TERM = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.TERMS, 0, RibbonElementPriority.TOP, 2);
    public static final AbstractCommandButton SHOW_NOTES_TABLE = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.NOTES, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_NOTES_COLUMN = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.NOTES, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_NOTES_COLUMN = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.NOTES, 0, RibbonElementPriority.TOP, 2);
    public static final AbstractCommandButton SHOW_NOTES_SUM = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.NOTES, 0, RibbonElementPriority.TOP, 3);
    public static final AbstractCommandButton SHOW_PRESENCE_TABLE = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.PRESENCE, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton REMOVE_PRESENCE = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.PRESENCE, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton EXPORT_TO_PDF = MainRibbon.getRibbonButton(RibbonTasks.MAIN_TOOLS, RibbonBands.EXPORT, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton SHOW_GROUPS = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.GROUPS, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_GROUP = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.GROUPS, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_GROUP = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.GROUPS, 0, RibbonElementPriority.TOP, 2);
    public static final AbstractCommandButton SHOW_STUDENTS = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.STUDENTS, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_STUDENT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.STUDENTS, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_STUDENT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.STUDENTS, 0, RibbonElementPriority.TOP, 2);
    public static final AbstractCommandButton ATTACH_STUDENT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.STUDENTS, 0, RibbonElementPriority.TOP, 3);
    public static final AbstractCommandButton DETACH_STUDENT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_GROUPS, RibbonBands.STUDENTS, 0, RibbonElementPriority.TOP, 4);
    public static final AbstractCommandButton SHOW_SUBJECTS = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SUBJECTS, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_SUBJECT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SUBJECTS, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_SUBJECT = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SUBJECTS, 0, RibbonElementPriority.TOP, 2);
    public static final AbstractCommandButton SHOW_SCHEMAS = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SCHEME, 0, RibbonElementPriority.TOP, 0);
    public static final AbstractCommandButton ADD_SCHEME = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SCHEME, 0, RibbonElementPriority.TOP, 1);
    public static final AbstractCommandButton REMOVE_SCHEME = MainRibbon.getRibbonButton(RibbonTasks.MANAGE_SUBJECTS, RibbonBands.SCHEME, 0, RibbonElementPriority.TOP, 2);
    
    public static void enableButton(AbstractCommandButton button){
        button.setEnabled(true);
    }
    public static void disableButton(AbstractCommandButton button){
        button.setEnabled(false);
    }
    
    public static void disableManageTermsButtons(){
        disableButton(ADD_TERM);
        disableButton(REMOVE_TERM);
        enableButton(SHOW_PLAN);
    }
    
    public static void disableAllNotesButtons(){
        disableManageNotesButtons();
        disableButton(SHOW_NOTES_TABLE);
    }
    
    public static void disableManageNotesButtons(){
        disableButton(SHOW_NOTES_SUM);
        disableButton(ADD_NOTES_COLUMN);
        disableButton(REMOVE_NOTES_COLUMN);
        enableButton(SHOW_NOTES_TABLE);
    }
    
    public static void disableManagePresenceButtons(){
        disableButton(REMOVE_PRESENCE);
        enableButton(SHOW_PRESENCE_TABLE);
    }
    
    public static void disableAllPresenceButtons(){
        disableManagePresenceButtons();
        disableButton(SHOW_PRESENCE_TABLE);
    }
    
    public static void disableManageColumnsNotesButtons(){
        disableButton(ADD_NOTES_COLUMN);
        disableButton(REMOVE_NOTES_COLUMN);
    }
    
    public static void enableManageColumnsNotesButtons(){
        enableButton(ADD_NOTES_COLUMN);
        enableButton(REMOVE_NOTES_COLUMN);
    }
}
