package com.example.droussos2867.gotoclass;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sempiternalsearch on 11/17/17.
 */

//Adapts data from query into the custom listview
public class DataAdapter extends ArrayAdapter<ListModel> {
    private ArrayList<ListModel> classList;
    private int resource;
    private LayoutInflater inflater;
    private TextView className, classID, professorName, sectionNumber;
    private View v;

    public DataAdapter(Context context, int resource, ArrayList<ListModel> objects) {
        super(context, resource, objects);
        classList = objects;
        this.resource = resource;
        //create Layout inflater
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        v = convertView;
        //Loads data into custom listview item layout
        v = inflater.inflate(R.layout.listview_layout, parent, false);

        //Assign textViews in layout names
        className = v.findViewById(R.id.textView);
        classID = v.findViewById(R.id.textView4);
        professorName = v.findViewById(R.id.textView2);
        sectionNumber = v.findViewById(R.id.textView3);

        //If account type is student set the professors name
        //This is done so professor acounts dont see their own name on classe
        if (classList.get(position).getType() == false) {
            professorName.setText(classList.get(position).getProfessorName());
        }
        else {
            professorName.setText("");
        }
        sectionNumber.setText("SEC.0" + classList.get(position).getSectionNumber());
        className.setText(classList.get(position).getClassName());
        classID.setText(classList.get(position).getClassID());


        return v;
    }
}
