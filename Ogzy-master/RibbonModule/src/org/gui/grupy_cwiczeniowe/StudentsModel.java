/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui.grupy_cwiczeniowe;

import org.database.models.Student;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class StudentsModel implements ListModel<Student> {

    private final List<Student> list;

    public StudentsModel(List<Student> list) {
        this.list = list;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Student getElementAt(int i) {
        try {
            return list.get(i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addElement(Student s) {
        list.add(s);
    }

    public void removeElement(Student s) {
        list.remove(s);
    }

    @Override
    public void addListDataListener(ListDataListener ll) {

    }

    @Override
    public void removeListDataListener(ListDataListener ll) {

    }

}
