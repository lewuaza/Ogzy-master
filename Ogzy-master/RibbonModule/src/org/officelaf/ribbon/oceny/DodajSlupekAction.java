/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.oceny;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.gui.oceny.NotesTableTopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Duży
 */
public class DodajSlupekAction extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        NotesTableTopComponent notestop = (NotesTableTopComponent) WindowManager.getDefault().findTopComponent("NotesTableTopComponent");
        JTable students_marks = notestop.getMarksTable();
        DefaultTableModel model = (DefaultTableModel) students_marks.getModel();
        model.addColumn(model.getColumnCount());
    }
    
}
