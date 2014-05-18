/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui.oceny;

import org.database.models.GrupaCwiczeniowa;
import org.database.models.GrupaOcen;
import org.database.models.Oceny;
import org.database.models.Student;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.officelaf.listeners.TopComponentsManagerListener;
import org.officelaf.ribbon.BandsButtons;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.gui.oceny//NotesTable//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "NotesTableTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.gui.oceny.NotesTableTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_NotesTableAction",
        preferredID = "NotesTableTopComponent"
)
@Messages({
    "CTL_NotesTableAction=NotesTable",
    "CTL_NotesTableTopComponent=NotesTable Window",
    "HINT_NotesTableTopComponent=This is a NotesTable window"
})
public final class NotesTableTopComponent extends TopComponent {
    private GrupaCwiczeniowa grupa = null;
    private List<Student> lista_studentow = null;
    private List<GrupaOcen> podkategorie = null;
    private List<List<Oceny>> oceny_po_kategoriach = null;
    private boolean isEditingCell = true;
    private boolean add_or_edit = false;
    
    public JTable getStudentsTable(){
        return this.students_table;
    }
    
    public void setTable(GrupaCwiczeniowa grupa){
        //tu należy uzupełnić o wypełnianie tabeli ocenami
    }
        
    public void setGrupa(GrupaCwiczeniowa grupa) {
        this.grupa = grupa;
        System.out.println(grupa.getNazwa() + " - " + grupa.getPrzedmiot().getNazwa());
        setDisplayName(grupa.getNazwa()+" - "+grupa.getPrzedmiot().getNazwa()+" - Oceny");
        //this.titleLabel.setText("Oceny dla: "+grupa.getNazwa()+ " - "+grupa.getPrzedmiot().getNazwa());
        //this.titleLabel.setHorizontalAlignment( SwingConstants.CENTER ); 
        lista_studentow = Student.getByGroup(grupa.getId());
        setTable(grupa);
        setStudentsTable();
        //students_marks.setModel(new OcenyModel());
        this.titleLabel.setText(grupa.getPrzedmiot().getNazwa());
        this.grupa_przedmiotu.setText(grupa.getNazwa());
        this.schemat_nazwa.setText("Schemat oceniania: " + grupa.getPrzedmiot().getGrupaOcen().getNazwa());
        this.students_marks.setModel(new DefaultTableModel());
    }

    public NotesTableTopComponent() {
        initComponents();
        setName(Bundle.CTL_NotesTableTopComponent());
        setToolTipText(Bundle.HINT_NotesTableTopComponent());

    }

    private void setStudentsTable() {
        DefaultTableModel model = (DefaultTableModel) students_table.getModel();
        while(model.getRowCount()>0) {
            model.removeRow(0);
        }
        for(Student s : lista_studentow) {
            model.addRow(new Object[]{s.getImie(), s.getNazwisko()});
        }
        students_table.setModel(model);
    }
    
    private void setStudentsMarks() {
        Student student = lista_studentow.get(students_table.getSelectedRow());
        GrupaOcen schemat = grupa.getPrzedmiot().getGrupaOcen();
        List<Oceny> oceny_studenta = Oceny.getOceny(student, grupa);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kategoria");
        podkategorie = GrupaOcen.getAllGrupaOcen(schemat.getId());
        oceny_po_kategoriach = new ArrayList<List<Oceny>>();
        for(int i = 0; i < podkategorie.size(); i++) {
            oceny_po_kategoriach.add(new ArrayList<Oceny>());
            List<Oceny> temp = oceny_po_kategoriach.get(i);
            for(Oceny o : oceny_studenta) {
                if(o.getGrupaOcen().getId() == podkategorie.get(i).getId()) {
                    temp.add(o);
                }
            }
        }
        int maksymalny_size = 1;
        for(List<Oceny> l : oceny_po_kategoriach) {
            if(maksymalny_size < l.size()) maksymalny_size = l.size();
        }
        for(GrupaOcen g: podkategorie){
            model.addRow(new Object[]{g.getNazwa()});
        }
        for(int i=1;i<=maksymalny_size;i++){
            model.addColumn(i+"");
        }
        for(int i = 0; i < oceny_po_kategoriach.size(); i++) {
            for(int j = 0; j < oceny_po_kategoriach.get(i).size(); j++ ) {
                model.setValueAt(oceny_po_kategoriach.get(i).get(j).getWartoscOceny(), i, j+1);
            }
        }
        this.students_marks.setModel(model);
        students_marks.getColumnModel().getColumn(0).setPreferredWidth(250);
        students_marks.getColumnModel().getColumn(0).setMaxWidth(250);
        students_marks.getColumnModel().getColumn(0).setMinWidth(250);
        students_marks.getColumnModel().getColumn(0).setWidth(250);
        students_marks.getColumnModel().getColumn(1).setResizable(false);
    }
 
    public JTable getMarksTable() {
        return students_marks;
    }
    
    public void DeleteMarksFromColumn(int column) {
        for(int i = 0; i < podkategorie.size(); i++) {
            if(students_marks.getModel().getValueAt(i,column) !=null)
                Oceny.deleteOceny(oceny_po_kategoriach.get(i).get(column-1).getId());
        }
        setStudentsMarks();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        grupa_przedmiotu = new javax.swing.JLabel();
        schemat_nazwa = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        students_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        students_marks = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(titleLabel, org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.titleLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(grupa_przedmiotu, org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.grupa_przedmiotu.text")); // NOI18N

        schemat_nazwa.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        schemat_nazwa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(schemat_nazwa, org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.schemat_nazwa.text")); // NOI18N

        students_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Imie", "Nazwisko"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        students_table.getTableHeader().setReorderingAllowed(false);
        students_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                students_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(students_table);
        if (students_table.getColumnModel().getColumnCount() > 0) {
            students_table.getColumnModel().getColumn(0).setResizable(false);
            students_table.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.students_table.columnModel.title0")); // NOI18N
            students_table.getColumnModel().getColumn(1).setResizable(false);
            students_table.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.students_table.columnModel.title1")); // NOI18N
        }

        students_marks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        students_marks.setCellSelectionEnabled(true);
        students_marks.getTableHeader().setReorderingAllowed(false);
        students_marks.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                students_marksPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(students_marks);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(NotesTableTopComponent.class, "NotesTableTopComponent.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(grupa_przedmiotu, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(schemat_nazwa, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grupa_przedmiotu)
                    .addComponent(schemat_nazwa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void students_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_students_tableMouseClicked
       TopComponentsManagerListener.EnableDisableManageColumsButtons(this);
        if(students_table.getSelectedRow()!=-1) {   
           setStudentsMarks();
       }
    }//GEN-LAST:event_students_tableMouseClicked

    private void students_marksPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_students_marksPropertyChange
        if ("tableCellEditor".equals(evt.getPropertyName())) {
            if(isEditingCell) {
                if(students_marks.getModel().getValueAt(students_marks.getSelectedRow(), students_marks.getSelectedColumn()) == null){
                    add_or_edit = false;
                    //System.out.println("Wchodze i komorka jest pusta");
                } else {
                    add_or_edit = true;
                   // System.out.println("Wchodze ale edytuje komorke");
                }
                isEditingCell= false;
            } else {
                if(add_or_edit) {
                    //System.out.println("W komorce byla wrtosc i edytujr");
                    if(students_marks.getModel().getValueAt(students_marks.getSelectedRow(), students_marks.getSelectedColumn()).toString().equals("")) {
                        Oceny.deleteOceny(oceny_po_kategoriach.get(students_marks.getSelectedRow()).get(students_marks.getSelectedColumn()-1).getId());
                    } else {
                        Oceny.editOceny(oceny_po_kategoriach.get(students_marks.getSelectedRow()).get(students_marks.getSelectedColumn()-1).getId(),Float.parseFloat(students_marks.getModel().getValueAt(students_marks.getSelectedRow(), students_marks.getSelectedColumn()).toString()));
                    }
                } else {
                   // System.out.println("Komorka byla pusta i nowa wartosc");
                    Oceny.addOceny(podkategorie.get(students_marks.getSelectedRow()), grupa, lista_studentow.get(students_table.getSelectedRow()), Float.parseFloat(students_marks.getModel().getValueAt(students_marks.getSelectedRow(), students_marks.getSelectedColumn()).toString()));
                }
                isEditingCell = true;
                setStudentsMarks();
            }            
        }
         // TODO add your handling code here:
    }//GEN-LAST:event_students_marksPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel grupa_przedmiotu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel schemat_nazwa;
    private javax.swing.JTable students_marks;
    private javax.swing.JTable students_table;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        if(this.titleLabel.getText().equals("Nazwa przedmiotu")) this.close();
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    public GrupaCwiczeniowa getGrupaCwiczeniowa() {
        return this.grupa;
    }
    public List<Student> getStudentsList() {
        return this.lista_studentow;
    }
}
