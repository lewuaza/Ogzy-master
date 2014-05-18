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
import org.database.models.TYP_OCENIANIA;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Pair;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.gui.oceny//OcenyKoncowe//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "OcenyKoncoweTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.gui.oceny.OcenyKoncoweTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_OcenyKoncoweAction",
        preferredID = "OcenyKoncoweTopComponent"
)
@Messages({
    "CTL_OcenyKoncoweAction=OcenyKoncowe",
    "CTL_OcenyKoncoweTopComponent=Oceny Koncowe",
    "HINT_OcenyKoncoweTopComponent=W tym oknie sprawdzisz oceny koncowe studentow"
})
public final class OcenyKoncoweTopComponent extends TopComponent {
    private GrupaCwiczeniowa grupa = null;
    private List<Student> lista_studentow = null;
    public OcenyKoncoweTopComponent() {
        initComponents();
        setName(Bundle.CTL_OcenyKoncoweTopComponent());
        setToolTipText(Bundle.HINT_OcenyKoncoweTopComponent());
    }

    public void setArguments(GrupaCwiczeniowa grupa, List<Student> lista_studentow) {
        this.grupa = grupa;
        this.lista_studentow = lista_studentow;  
        setTable();
        obliczanie_oceny();
        titleLabel.setText(grupa.getPrzedmiot().getNazwa() +" - oceny");
        grupa_przedmiotu.setText(grupa.getNazwa());
        schemat_nazwa.setText("Schemat oceniania: " + grupa.getPrzedmiot().getGrupaOcen().getNazwa() + ", Typ oceniania: "+ grupa.getPrzedmiot().getTypOceniania().toString());
    }
    
    private void setTable() {
        DefaultTableModel model = new DefaultTableModel();
        if(grupa.getPrzedmiot().getTypOceniania() == TYP_OCENIANIA.OCENA) {
            model.addColumn("Imie");
            model.addColumn("Nazwisko");
            model.addColumn("Numer Indeksu");
            model.addColumn("Ocena koncowa");
        } else {
            model.addColumn("Imie");
            model.addColumn("Nazwisko");
            model.addColumn("Numer Indeksu");
            model.addColumn("Wynik punktowy");
            model.addColumn("Ocena koncowa");
        }
        this.oceny_koncowe.setModel(model);
        oceny_koncowe.getColumnModel().getColumn(0).setMinWidth(100);
        oceny_koncowe.getColumnModel().getColumn(0).setPreferredWidth(100);
        oceny_koncowe.getColumnModel().getColumn(0).setMaxWidth(100);
        oceny_koncowe.getColumnModel().getColumn(1).setMinWidth(100);
        oceny_koncowe.getColumnModel().getColumn(1).setPreferredWidth(100);
        oceny_koncowe.getColumnModel().getColumn(1).setMaxWidth(100);
        oceny_koncowe.getColumnModel().getColumn(2).setMinWidth(100);
        oceny_koncowe.getColumnModel().getColumn(2).setPreferredWidth(100);
        oceny_koncowe.getColumnModel().getColumn(2).setMaxWidth(100);
    }
    private void replaceTable(Student student, float ocena) {
        DefaultTableModel model = (DefaultTableModel) oceny_koncowe.getModel();
        if(grupa.getPrzedmiot().getTypOceniania() == TYP_OCENIANIA.OCENA) {
            model.addRow(new Object[]{student.getImie(), student.getNazwisko(), student.getIndeks(), ocena_z_srednich(ocena)});
        } else {
            model.addRow(new Object[]{student.getImie(), student.getNazwisko(), student.getIndeks(), ocena, ocena_z_punktow(ocena)});
        }        
    }
    
    private void obliczanie_oceny() {
        for(Student s: lista_studentow) {
            // Wszystkie oceny studenta
            List<Oceny> oceny_studenta = Oceny.getOceny(s, grupa);
            // Podkategorie dla danego przedmiotu
            List<GrupaOcen>podkategorie = GrupaOcen.getAllGrupaOcen(grupa.getPrzedmiot().getGrupaOcen().getId());
            // Oceny podzielone juz po kategoriach 
            List<List<Oceny>> oceny_po_kategoriach = new ArrayList<List<Oceny>>(); 
            // Lista ocen do obliczania sredniej wazonej??
            List<Float> lista_ocen = new ArrayList<Float>();
            for(int i = 0; i < podkategorie.size(); i++) {
                oceny_po_kategoriach.add(new ArrayList<Oceny>());
                List<Oceny> temp = oceny_po_kategoriach.get(i);
                for(Oceny o : oceny_studenta) {
                    if(o.getGrupaOcen().getId() == podkategorie.get(i).getId()) {
                        temp.add(o);
                    }
                }
                if(temp.size()==0) {
                    lista_ocen.add((float)0);
                } else {
                    if(grupa.getPrzedmiot().getTypOceniania() == TYP_OCENIANIA.OCENA) {
                        lista_ocen.add(srednia_arytmetyczna(temp)); 
                    } else {
                        float suma_punktow = 0;
                        for(int o = 0; o < temp.size(); o++) {
                            suma_punktow += temp.get(o).getWartoscOceny();
                        }
                        if(suma_punktow > 100) suma_punktow = 100;
                        lista_ocen.add(suma_punktow);
                    }
                }                
            }
            replaceTable(s, srednia_wazona(podkategorie, lista_ocen));
        }
    }
    
    private float srednia_arytmetyczna(List<Oceny> lista) {
        float suma_ocen = 0;
        for(int i = 0; i < lista.size(); i++) {
            suma_ocen += lista.get(i).getWartoscOceny();
        }
        return suma_ocen/lista.size();
    }
    
    private float srednia_wazona(List<GrupaOcen> lista_go, List<Float> lista_ocen) {
        float srednia = 0;
            for(int i = 0; i < lista_go.size(); i++) {
                srednia += lista_ocen.get(i)*lista_go.get(i).getWaga();
            }
        return srednia;
    }
    
    private float ocena_z_punktow(float punkty) {
        if(punkty > 91 && punkty <=100) return 5;
        if(punkty > 81 && punkty <=90) return (float) 4.5;
        if(punkty > 71 && punkty <=80) return 4;
        if(punkty > 61 && punkty <=70) return (float) 3.5;
        if(punkty > 51 && punkty <=60) return 3;
        return 2;
    }
    private float ocena_z_srednich(float ocena) {
        if(ocena >= 5 && ocena < 5.5) return 5;
        if(ocena >= 4.5 && ocena < 5) return (float) 4.5;
        if(ocena >= 4 && ocena < 4.5) return 4;
        if(ocena >= 3.5 && ocena < 4) return (float) 3.5;
        if(ocena >= 3.0 && ocena < 3.5) return 3;
        return 2;
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
        jSeparator1 = new javax.swing.JSeparator();
        schemat_nazwa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        oceny_koncowe = new javax.swing.JTable();

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(titleLabel, org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.titleLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(grupa_przedmiotu, org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.grupa_przedmiotu.text")); // NOI18N

        schemat_nazwa.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        schemat_nazwa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(schemat_nazwa, org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.schemat_nazwa.text")); // NOI18N

        oceny_koncowe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Imie", "Nazwisko", "Numer indeksu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        oceny_koncowe.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(oceny_koncowe);
        if (oceny_koncowe.getColumnModel().getColumnCount() > 0) {
            oceny_koncowe.getColumnModel().getColumn(0).setMinWidth(100);
            oceny_koncowe.getColumnModel().getColumn(0).setPreferredWidth(100);
            oceny_koncowe.getColumnModel().getColumn(0).setMaxWidth(100);
            oceny_koncowe.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.oceny_koncowe.columnModel.title0")); // NOI18N
            oceny_koncowe.getColumnModel().getColumn(1).setMinWidth(100);
            oceny_koncowe.getColumnModel().getColumn(1).setPreferredWidth(100);
            oceny_koncowe.getColumnModel().getColumn(1).setMaxWidth(100);
            oceny_koncowe.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.oceny_koncowe.columnModel.title1")); // NOI18N
            oceny_koncowe.getColumnModel().getColumn(2).setMinWidth(100);
            oceny_koncowe.getColumnModel().getColumn(2).setPreferredWidth(100);
            oceny_koncowe.getColumnModel().getColumn(2).setMaxWidth(100);
            oceny_koncowe.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(OcenyKoncoweTopComponent.class, "OcenyKoncoweTopComponent.oceny_koncowe.columnModel.title2")); // NOI18N
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(grupa_przedmiotu, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(schemat_nazwa, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane1)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel grupa_przedmiotu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable oceny_koncowe;
    private javax.swing.JLabel schemat_nazwa;
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
}
