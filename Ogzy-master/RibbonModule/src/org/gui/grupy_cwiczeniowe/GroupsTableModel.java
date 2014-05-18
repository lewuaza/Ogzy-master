/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui.grupy_cwiczeniowe;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.gui.Row;

/**
 *
 * @author Ja
 */
public class GroupsTableModel extends DefaultTableModel {

    public GroupsTableModel() {
        columnIdentifiers = new Vector<String>();
        columnIdentifiers.add("Id");
        columnIdentifiers.add("Nazwa");
        columnIdentifiers.add("Przedmiot");
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
