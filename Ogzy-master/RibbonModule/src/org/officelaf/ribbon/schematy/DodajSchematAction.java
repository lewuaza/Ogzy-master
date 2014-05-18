/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.officelaf.ribbon.schematy;

import org.database.models.GrupaOcen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.gui.przedmioty.GrupaOcenDodajPanel;
import org.openide.windows.TopComponent;

/**
 *
 * @author Duży
 */
public class DodajSchematAction extends AbstractAction{
    
    private JButton OK_Button = new JButton("Dodaj do bazy");
    private JButton CANCEL_Button= new JButton("Anuluj");
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = TopComponent.getRegistry().getActivated();
        
        GrupaOcenDodajPanel panel = new GrupaOcenDodajPanel(OK_Button);
        OK_Button.setSelected(false); CANCEL_Button.setSelected(false);
        OK_Button.setEnabled(false);
        OK_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dobieramy sie do tego Jbuttona
                JButton btn = (JButton)e.getSource();
                // Przez dluga sciezke ojcow docieramy do JDialogu
                JDialog panel = (JDialog) btn.getParent().getParent().getParent().getParent().getParent().getParent();
                OK_Button.setSelected(true);
                panel.dispose();
            }
        });
        CANCEL_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dobieramy sie do tego Jbuttona
                JButton btn = (JButton)e.getSource();
                // Przez dluga sciezke ojcow docieramy do JDialogu
                JDialog panel = (JDialog) btn.getParent().getParent().getParent().getParent().getParent().getParent();
                CANCEL_Button.setSelected(true);
                panel.dispose();
            }
        });
        JButton[] objects = new JButton[]{OK_Button, CANCEL_Button};
        int zwrot = JOptionPane.showOptionDialog(top, panel, "Dodaj schemat oceniania", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, objects, objects[0]);
        if(OK_Button.isSelected()) zwrot = 0;
        if(CANCEL_Button.isSelected()) zwrot = 1;
        if(zwrot == 0) {
            panel.SaveAll();
        }
    }
    
}
