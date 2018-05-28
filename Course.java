/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.util.ArrayList;

import java.io.Serializable;

/**
 *
 * @author waseem syed
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> listOfRequiremnts = new ArrayList<>();
    private ArrayList<String> noteOfRequirement = new ArrayList<>();
    private String CourseName;

    public void addList(String cr) {
        listOfRequiremnts.add(cr);
        noteOfRequirement.add("");
    }

    public void setCourseName(String course) {
        this.CourseName = course;
    }

    public void addNote(int index, String note) {
        noteOfRequirement.set(index, note);

    }

    public void addRequirement(int index, String note) {
        listOfRequiremnts.add(index, note);

    }

    String getNote(int index) {
        return noteOfRequirement.get(index);
    }

    void setLists(ArrayList<String> rL, ArrayList<String> nL) {
        listOfRequiremnts = rL;
        noteOfRequirement = nL;

    }

    ArrayList<String> getRequirementList() {

        return listOfRequiremnts;
    }

    void removeElement(int index) {

        listOfRequiremnts.remove(index);
        noteOfRequirement.remove(index);

    }

    ArrayList<String> getNoteOFList() {

        return noteOfRequirement;
    }

    String getCourseName() {

        return CourseName;
    }
}
