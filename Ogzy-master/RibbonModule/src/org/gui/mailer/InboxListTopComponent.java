/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gui.mailer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.mailer.models.Account;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.gui.mailer//MailerListMain//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "MailerListMainTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.gui.mailer.MailerListTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MailerListMainAction",
        preferredID = "MailerListMainTopComponent"
)
@NbBundle.Messages({
    "CTL_MailerListMainAction=OcenyMain",
    "CTL_MailerListMainTopComponent=OcenyMain Window",
    "HINT_MailerListMainTopComponent=This is a OcenyMain window"
})
/**
 *
 * @author lewuaza
 */
public class InboxListTopComponent extends TopComponent{

    /**
     * Creates new form InboxListTopComponent
     */
    private ArrayList<Account> accountList;
    public InboxListTopComponent() {
        initComponents();
        setDisplayName("Lista skrzynek pocztowych");
        tabelaStudentow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaStudentow.addMouseListener(new  MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int rowIndex = target.getSelectedRow();
                    int accountID = (Integer)tabelaStudentow.getModel().getValueAt(rowIndex, 0);
                        for(Account acc : accountList)
                            if(acc.getID() == accountID)
                            {
                                MailerTopComponent window = (MailerTopComponent) WindowManager.getDefault().findTopComponent("MailerTopComponent");
                                Settings panel = new Settings();
                                panel.setupForm(acc);
                                Object[] options = {
                                    "Zapisz", "Anuluj"
                                };
                                int input = JOptionPane.showOptionDialog(window, panel, "Konfiguracja skrzynki mailowej", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                if (input == JOptionPane.OK_OPTION) {
                                    panel.update();
                                }
                                setupForm();
                                break;
                            }           
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                int rowindex = tabelaStudentow.getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.getButton() == MouseEvent.BUTTON3 && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem deleteItem = new JMenuItem("Usuń"){
                    };
                    deleteItem.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int rowindex = tabelaStudentow.getSelectedRow();
                            if (rowindex < 0)
                                return;
                            int accountID = (Integer)tabelaStudentow.getModel().getValueAt(rowindex, 0);
                            for(Account acc : accountList)
                                if(acc.getID() == accountID)
                                    acc.delete();
                            setupForm();
                        }
                    }
                    );
                    popup.add(deleteItem);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }            
        });
        setupForm();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaStudentow = new javax.swing.JTable();

        tabelaStudentow.setModel(new InboxListModel());
        tabelaStudentow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaStudentowMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaStudentow);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaStudentowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaStudentowMouseClicked
        //TopComponentsManagerListener.StudentsListTopComponentActivated(this);
    }//GEN-LAST:event_tabelaStudentowMouseClicked

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
    public void setupForm()
    {
       ArrayList<Account> list = Account.getAll();
       DefaultTableModel model = new DefaultTableModel(){
           @Override
           public boolean isCellEditable(int row, int column){  
                return false;  
           }
       };
       model.addColumn("ID");
       model.addColumn("Nazwa");
       model.addColumn("Login");
       model.addColumn("Pop3 Serwer");
       model.addColumn("SMTP Serwer");
       for(Account acc: list)
       {
           model.addRow(new Object[]{acc.getID() , acc.getName() , acc.getLogin() , acc.getPOP3Hostname() , acc.getSMTP()});
       }
       tabelaStudentow.setModel(model);
       this.accountList = list;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelaStudentow;
    // End of variables declaration//GEN-END:variables
}
