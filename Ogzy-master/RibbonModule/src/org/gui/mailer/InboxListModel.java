package org.gui.mailer;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.gui.Row;

public class InboxListModel extends DefaultTableModel {

    public InboxListModel() {
        columnIdentifiers = new Vector<String>();
        columnIdentifiers.add(0, "Id");
        columnIdentifiers.add(1, "Nazwa");
        columnIdentifiers.add(2, "Login");
        columnIdentifiers.add(3, "Pop3 Server");
        columnIdentifiers.add(4, "SMTP Server");
        dataVector = new Vector<Row>();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Row) dataVector.get(rowIndex)).getRow(columnIndex);
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (dataVector.size() <= row) {
            dataVector.add(new Row());
            ((Row) dataVector.get(row)).setRow(col, value);
        } else {
            ((Row) dataVector.get(row)).setRow(col, value);
        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}