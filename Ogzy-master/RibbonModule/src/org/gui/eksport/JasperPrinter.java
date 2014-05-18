package org.gui.eksport;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.database.SqlConnection;
import org.openide.util.Exceptions;

public class JasperPrinter {

    private static void print(String reportName) {
        String reportSource = "RibbonModule\\src\\org\\gui\\eksport\\" + reportName + ".jasper";
        //String reportDest = "RibbonModule\\src\\org\\gui\\eksport\\"+reportName+".html";

        Map<String, Object> params = new HashMap<String, Object>();

        try {
//            JasperReport jasperReport
//                    = JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint
                    = JasperFillManager.fillReport(
                            reportSource, params, SqlConnection.getInstance().getSqlConnection());

//            JasperExportManager.exportReportToHtmlFile(
//                    jasperPrint, reportDest);
            boolean exitOnClose = false;

            JasperViewer.viewReport(jasperPrint, exitOnClose);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JRException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void printAllStudents() {
        print("AllStudents");
    }

    public static void printAllGroups() {
        print("AllGroups");
    }

    public static void printAllSubjects() {
        print("AllSubjects");
    }

    public static void printAllSchemas() {
        print("AllSchemas");
    }
}
