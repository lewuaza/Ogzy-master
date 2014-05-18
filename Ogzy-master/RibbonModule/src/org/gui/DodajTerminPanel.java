/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui;

import org.database.models.GrupaCwiczeniowa;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Duży
 */
public class DodajTerminPanel extends javax.swing.JPanel {

    /**
     * Creates new form DodajTerminPanel
     */
    List<GrupaCwiczeniowa> grupy = new ArrayList<GrupaCwiczeniowa>();

    public DodajTerminPanel() {
        initComponents();
        activateList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        groupList = new javax.swing.JList();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(DodajTerminPanel.class, "DodajTerminPanel.jLabel1.text")); // NOI18N

        groupList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(groupList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 188, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList groupList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void activateList() {
        grupy = GrupaCwiczeniowa.getAll();
        DefaultListModel<String> model = new DefaultListModel<String>();

        for (GrupaCwiczeniowa gr : grupy) {
            model.addElement(gr.getPrzedmiot().getNazwa() + " - " + gr.getNazwa());
        }

        groupList.setModel(model);

    }

    public int getSelectedIndexList() {
        return this.groupList.getSelectedIndex();
    }

    public GrupaCwiczeniowa getSelectedGroup() {
        if (groupList.getSelectedIndex() == -1) {
            return null;
        } else {
            return grupy.get(groupList.getSelectedIndex());
        }
    }

}
