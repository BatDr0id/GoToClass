package com.example.droussos2867.gotoclass;

/**
 * Created by sempiternalsearch on 11/30/17.
 */

    //StudentModel which creates is used to create the roster list view
public class StudentModel {
    private String fName, lName, studentID, name;
    private boolean attendance;
    public StudentModel(String studentID, String fName, String lName,  boolean attendance){
        this.fName = fName;
        this.lName = lName;
        this.studentID = studentID;
        this.attendance = attendance;
        name = fName + " " + lName;

    }

    //needed get functions
    //setters were removed because they were not being utilized
    //So why keep unused code
    public String getName() { return name;}

    public String getStudentID() {
        return studentID;
    }

    public boolean isAttendance() {
        return attendance;
    }

}
