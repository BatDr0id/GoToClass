package com.example.droussos2867.gotoclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sempiternalsearch on 11/30/17.
 */
    //Class creates the adapter to add the student roster
    //information to the list view
public class RosterAdapter extends ArrayAdapter<StudentModel> {
    private ArrayList<StudentModel> studentModel;   //Uses ArrayList based on StudentModel
    private int resource;
    private LayoutInflater inflater;
    private TextView studentName, attendance, studentID;
    private View v;

    public RosterAdapter(Context context, int resource, ArrayList<StudentModel> objects){
        super(context, resource, objects);
        studentModel = objects;
        this.resource = resource;
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public View getView(int position, View convertView, ViewGroup parent) {

        v = convertView;
        v = inflater.inflate(R.layout.roster_list, parent, false);

        //Gets TextViews from roster_list
        studentName = v.findViewById(R.id.studentName);
        studentID = v.findViewById(R.id.studentID);
        attendance = v.findViewById(R.id.status);

        //Sets Present or Absent based on boolean attendance in
        //JsonArray in RosterActivity
        if (studentModel.get(position).isAttendance()) {
            attendance.setText("Present");
        }
        else {
            attendance.setText("Probably skipping");
        }
        studentName.setText(studentModel.get(position).getName());
        studentID.setText(studentModel.get(position).getStudentID());
        return v;
    }
}
