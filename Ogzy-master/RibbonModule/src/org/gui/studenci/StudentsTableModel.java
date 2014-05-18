package org.gui.studenci;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.gui.Row;

public class StudentsTableModel extends DefaultTableModel {

    public StudentsTableModel() {
        columnIdentifiers = new Vector<String>();
        columnIdentifiers.add(0, "Id");
        columnIdentifiers.add(1, "Imię");
        columnIdentifiers.add(2, "Nazwisko");
        columnIdentifiers.add(3, "Email");
        columnIdentifiers.add(4, "Numer indeksu");
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

    @Override
    public String getColumnName(int col) {
        return ((String) columnIdentifiers.get(col));
    }
}