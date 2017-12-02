package com.example.droussos2867.gotoclass;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by sempiternalsearch on 11/30/17.
 */

    //This class is the new activity which allows the prfoessor to view the class
    //roster and see whichs student have currently been makred present
public class RosterActivity extends AppCompatActivity {
    private int counter;
    private TextView className;
    private ListView listView;
    private String classNameText;
    private RosterAdapter rosterAdapter;
    private ArrayList<StudentModel> studentModel;
    private String tempID, tempFname, tempLname;
    private boolean tempAttendance;
    protected void onCreate(final Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.roster_layout);

        className = (TextView) findViewById(R.id.className);
        classNameText = getIntent().getStringExtra("className");

        className.setText(classNameText);

        studentModel = new ArrayList<>();   //Create array based on ListModel's data structure
        /*All data from php are put into array */
        while (getIntent().getStringExtra("" + counter + "") != null) {                       //While there are rows of data from query
            JSONArray array;                                                                        //Establish variable to store data
            try {
                array = new JSONArray(getIntent().getStringExtra("" + counter + ""));         //Pull the row from JSON 2D Array
                                                                                                     //Set variable according to data
                tempID = array.getString(0);                                                  //structure in JSON array
                tempFname = array.getString(1);
                tempLname = array.getString(2);
                tempAttendance = array.getBoolean(3);
                studentModel.add(new StudentModel(tempID, tempFname, tempLname,  tempAttendance));  //Add data from JsonArray to studenModel
                }//Add data to classes ArrayList
            catch (JSONException e) {
                e.printStackTrace();
            }

            counter++;                                                                              //Up the counter for next row
        }

        //Creaee adapter to append data
        rosterAdapter = new RosterAdapter(getApplicationContext(), R.layout.roster_list, studentModel);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(rosterAdapter); //set adapater to list view

    }
}
