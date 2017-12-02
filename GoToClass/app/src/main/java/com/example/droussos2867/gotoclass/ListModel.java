package com.example.droussos2867.gotoclass;

/**
 * Created by sempiternalsearch on 11/17/17.
 */

public class ListModel {
    private String className, sectionNumber, professorF, professorL, classID, professorName;
    private boolean type;

    //ListModel which creates the list of classes for both Professor and Student
    public ListModel(String className, String classID, String sectionNumber, String professorF, String professorL, boolean type ) {
        this.className = className;
        this.classID = classID;
        this.sectionNumber = sectionNumber;
        this.professorL = professorL;
        this.professorF = professorF;
        professorName = this.professorF + " " + this.professorL;
        this.type = type;
    }
    //needed get functions
    //setters were removed because they were not being utilized
    //So why keep unused code
    public boolean getType() {
        return type;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public String getProfessorName() {
        return professorF + " " + professorL;
    }

    public String getClassName() {
        return className;
    }

    public String getClassID() {
        return classID;
    }

}
