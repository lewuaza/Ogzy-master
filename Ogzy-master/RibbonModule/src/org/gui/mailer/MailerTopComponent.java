/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui.mailer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.mailer.MailerReciver;
import org.mailer.models.Account;
import org.mailer.models.MessageModel;
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
        dtd = "-//org.gui.mailer//MailerMain//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "MailerMainTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.gui.mailer.MailerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MailerMainAction",
        preferredID = "MailerMainTopComponent"
)
@NbBundle.Messages({
    "CTL_MailerMainAction=OcenyMain",
    "CTL_MailerMainTopComponent=OcenyMain Window",
    "HINT_MailerMainTopComponent=This is a OcenyMain window"
})
/**
 *
 * @author lewuaza
 */
public class MailerTopComponent extends TopComponent {

    private ArrayList<Account> accountList;
    private ArrayList<MessageModel> mailList;
    private int lastIndexRow;
    /**
     * Creates new form MailerTopComponent
     */
    public MailerTopComponent() {
        initComponents();
        setDisplayName("Skrzynka pocztowa");
        lastIndexRow = -1;
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = jTree1.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTree1.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 2) {
                        if (((DefaultMutableTreeNode) selPath.getLastPathComponent()).isLeaf()) {
                            setMailList(((DefaultMutableTreeNode) selPath.getLastPathComponent()).getUserObject().toString());
                        }
                    }
                }
            }
        };
        jTree1.addMouseListener(ml);
        jTextPane1.setEditable(false);
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int rowIndex = target.getSelectedRow();
                if (e.getClickCount() == 1) {
                    if (rowIndex >= 0) {
                        if(lastIndexRow != -1)
                        {
                            jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getFrom(), lastIndexRow, 0);
                            jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getDate(), lastIndexRow, 2);
                            jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getSubject(), lastIndexRow, 1);
                            mailList.get(lastIndexRow).setRead();
                        }
                        jLabel2.setText(mailList.get(rowIndex).getFrom());
                        jLabel6.setText(mailList.get(rowIndex).getDate().toGMTString());
                        if(mailList.get(rowIndex).getSubject() != null)
                        {
                            if(mailList.get(rowIndex).getSubject().length() > 20)
                                jLabel7.setText(mailList.get(rowIndex).getSubject().substring(0, 20) + (mailList.get(rowIndex).getSubject().length() > 20 ? "..." : ""));
                            else
                                jLabel7.setText(mailList.get(rowIndex).getSubject());
                            
                        }
                        jTextPane1.setText(mailList.get(rowIndex).getContent());
                        lastIndexRow = rowIndex;
                    }
                }
                else if(e.getClickCount() == 2)
                {
                    SendEmailPanel panel = new SendEmailPanel();
                    panel.setupForm(mailList.get(rowIndex).getFrom(), 
                            mailList.get(rowIndex).getSubject(), mailList.get(rowIndex).getContent());
                    Object[] options = {
                        "Wyślij", "Anuluj"
                    };
                    int input = JOptionPane.showOptionDialog(null, panel, "Wysyłanie nowej wiadomości", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    if (input == JOptionPane.OK_OPTION) {
                        panel.sendEmail();
                    }
                }
            }

        });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    int rowIndex = lse.getLastIndex();
                    if(lastIndexRow != -1)
                    {
                        jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getFrom(), lastIndexRow, 0);
                        jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getDate(), lastIndexRow, 2);
                        jTable1.getModel().setValueAt(mailList.get(lastIndexRow).getSubject(), lastIndexRow, 1);
                        mailList.get(lastIndexRow).setRead();
                    }
                    jLabel2.setText("<html>" + mailList.get(rowIndex).getFrom() + "</html>");
                    jLabel6.setText("<html>" + mailList.get(rowIndex).getDate().toGMTString() + "</html>");
                    jLabel7.setText("<html>" + mailList.get(rowIndex).getSubject().substring(0, 20) + (mailList.get(rowIndex).getSubject().length() > 20 ? "..." : "") + "</html>");
                    jTextPane1.setText("<html>" + mailList.get(rowIndex).getContent() + "</html>");
                    lastIndexRow = rowIndex;   
                }
            }
        });
        tableModel.addColumn("Nadawca");
        tableModel.addColumn("Temat");
        tableModel.addColumn("Data");
        jTable1.setModel(tableModel);
        new Thread(new Runnable() {
            @Override
            public void run() {
                setupForm();
            }
        }).start();
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        jScrollPane1.setViewportView(jTree1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(MailerTopComponent.class, "MailerTopComponent.jLabel7.text")); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        jScrollPane3.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addComponent(jScrollPane4)))
        );
    }// </editor-fold>//GEN-END:initComponents

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

    public void setupForm() {
        accountList = Account.getAll();
        ((DefaultMutableTreeNode) jTree1.getModel().getRoot()).removeAllChildren();
        jTree1.setRootVisible(false);
        for (Account acc : accountList) {
            org.mailer.MailerSettings settings = new org.mailer.MailerSettings(acc, "imaps", "");
            MailerReciver mr = new MailerReciver(settings);
            mr.addToJTree(jTree1, true);
        }
    }

    @Override
    public void componentOpened() {
        ((DefaultMutableTreeNode) jTree1.getModel().getRoot()).removeAllChildren();
        jTree1.setRootVisible(false);
    }

    public void setMailList(String folderName) {
        this.mailList = MessageModel.getEmailsByFolder(folderName, 1);

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Nadawca");
        tableModel.addColumn("Temat");
        tableModel.addColumn("Data");
        for (MessageModel model : mailList) {
            if(!model.getIsRead())
                tableModel.addRow(new Object[]{"<html><b>" + (model.getFrom() == null ? "" : model.getFrom()) + " </b></html>", 
                    "<html><b>" + model.getSubject()+" </b></html>" , "<html><b>" + model.getDate()+" </b></html>"});
            else
                tableModel.addRow(new Object[]{model.getFrom(),  model.getSubject(),model.getDate()});
                
        }
        jTable1.setModel(tableModel);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}
