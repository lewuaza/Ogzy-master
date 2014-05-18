/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.oceny;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.gui.oceny.NotesTableTopComponent;
import org.gui.oceny.UsunSlupek;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class UsunSlupekAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        NotesTableTopComponent notestop = (NotesTableTopComponent) WindowManager.getDefault().findTopComponent("NotesTableTopComponent");
        JTable students_marks = notestop.getMarksTable();
        
        if(notestop.isOpened()){
            notestop.requestActive();
            Object[] op = {"Usuń","Anuluj"};
            UsunSlupek panel = new UsunSlupek(students_marks);
            int n = JOptionPane.showOptionDialog(notestop, panel, "Usuwanie kolumny z ocenami", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0]);
            if(n==0){
                notestop.DeleteMarksFromColumn(panel.WhatColumnToDelete());
            }
        }
        else{
            JOptionPane.showMessageDialog(notestop, "Przejdź do tabeli ocen korzystając z przycisku obok, aby móc usuwać oceny", "Usuwanie ocen - BŁĄD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
