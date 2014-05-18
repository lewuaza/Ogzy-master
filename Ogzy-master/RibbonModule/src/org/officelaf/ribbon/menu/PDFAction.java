package org.officelaf.ribbon.menu;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.gui.eksport.JasperPrinter;
import org.gui.eksport.WybierzEksportPanel;
import org.openide.windows.TopComponent;

public class PDFAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        TopComponent top = TopComponent.getRegistry().getActivated();

        WybierzEksportPanel panel = new WybierzEksportPanel();

        Object[] options = {
            "OK", "Anuluj"
        };
        JOptionPane.showOptionDialog(top, panel, "Eksport", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (panel.isAllStudents()) {
            JasperPrinter.printAllStudents();
        } else if (panel.isAllGroups()) {
            JasperPrinter.printAllGroups();
        } else if (panel.isallSubjects()) {
            JasperPrinter.printAllSubjects();
        } else if (panel.isAllSchemas()) {
            JasperPrinter.printAllSchemas();
        }

    }

}
