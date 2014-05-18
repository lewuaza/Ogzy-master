/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.gui.oceny;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.gui.Row;

/**
 *
 * @author Mariushrek
 */
public class OcenyModel extends DefaultTableModel {
 
    public OcenyModel() {
        columnIdentifiers = new Vector<String>();
        columnIdentifiers.add(0, "Id"); // Ukryte ID
        columnIdentifiers.add(1, ""); // Opis rzedu
        columnIdentifiers.add(2, "1");
        columnIdentifiers.add(3, "2");
        columnIdentifiers.add(4, "3");
        columnIdentifiers.add(5, "4");
        columnIdentifiers.add(6, "5");
        columnIdentifiers.add(7, "6");
        columnIdentifiers.add(8, "7");
        columnIdentifiers.add(9, "8");
        columnIdentifiers.add(10, "9");
        columnIdentifiers.add(11, "10");
        /*
        for(int i = 1; i < najwieksza_liczba_ocen; i++) {
            columnIdentifiers.add(i,i+"");
        }
        */
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
