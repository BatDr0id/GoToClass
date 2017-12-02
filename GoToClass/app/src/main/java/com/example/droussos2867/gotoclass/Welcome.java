package com.example.droussos2867.gotoclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sempiternalsearch on 11/12/17.
 */

//Activity after Login view classes to both professor and student
//Also creates appropriate action for each account type
public class Welcome extends AppCompatActivity {

    private int counter;
    private AlertDialog.Builder alertDialog;
    private boolean type, status;
    private EditText text;
    private DataAdapter dataAdapter;
    private ArrayList<ListModel> listModels;
    private ListView listView;
    private TextView userIDText, userEmailText, userNameText, codeText;
    private ImageView imageIcon;
    private String firstExtra, lastExtra, emailExtra, idExtra, imageExtra;
    private final String urlLink = "http://dr1197dr.000webhostapp.com";
    private String tempClass, tempID, tempSection, tempFname, tempLname, code;


    @Override
    protected void onCreate(final Bundle saved) {
        super.onCreate(saved);

        //type used to determine account type
        //true = professor account
        //false = student account
        type = getIntent().getBooleanExtra("type", type);

        //If account is an professor account use professor welcome layout
        //and add text to code bar at bottom
        //else use regular welcome layout
        if (type == true) {
            setContentView(R.layout.welcome_professor);
            codeText = (TextView) findViewById(R.id.codeText);
            code = getIntent().getStringExtra("code");
            codeText.setText("Your code currently is \"" + code + "\"");

        } else {
            setContentView(R.layout.welcome);
        }

        //Date format which will be used for tracking attendance
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        final String currentTime = sdf.format(dt);

        //Gets items from welcome.xml and assigns variable name
        imageIcon = (ImageView) findViewById(R.id.profileImage);
        userNameText = (TextView) findViewById(R.id.userNameTextView);
        userIDText = (TextView) findViewById(R.id.userIDText);
        userEmailText = (TextView) findViewById(R.id.userEmailTextView);
        firstExtra = getIntent().getStringExtra("firstName");
        lastExtra = getIntent().getStringExtra("lastName");


        //Distinguish between student and professor account to get id
        if (getIntent().getStringExtra("studentID") == null) {
            idExtra = getIntent().getStringExtra("professorID");
        } else {
            idExtra = getIntent().getStringExtra("studentID");
        }
        emailExtra = getIntent().getStringExtra("email");
        imageExtra = getIntent().getStringExtra("image");

        //Makes ImageView icon round
        new ImageTask(imageIcon, getResources()).execute(urlLink + imageExtra);


        //Set text in Views
        String name = firstExtra + " " + lastExtra;
        userNameText.setText(name);
        userIDText.setText(idExtra);
        userEmailText.setText(emailExtra);

        counter = 0;
        //Push classes to List View
        listModels = new ArrayList<>();                                                             //Create array based on ListModel's data structure
        while (getIntent().getStringExtra("" + counter + "") != null) {                       //While there are rows of data from query
            JSONArray array;                                                                        //Establish variable to store data
            try {
                array = new JSONArray(getIntent().getStringExtra("" + counter + ""));         //Pull the row from JSON 2D Array
                tempClass = array.getString(0);                                               //Set variable according to data
                tempID = array.getString(1);                                                  //structure in JSON array
                tempSection = array.getString(2);
                if (!type) {
                    //Student Account
                    tempFname = array.getString(3);
                    tempLname = array.getString(4);
                    listModels.add(new ListModel(tempClass, tempID, tempSection, tempFname, tempLname, type));
                } else {
                    //Professor Account
                    //No need to add the professor name
                    listModels.add(new ListModel(tempClass, tempID, tempSection, "", "", type));
                }//Add data to classes ArrayList
            } catch (JSONException e) {
                e.printStackTrace();
            }
            counter++;                                                                              //Up the counter for next row
        }


        dataAdapter = new DataAdapter(getApplicationContext(), R.layout.listview_layout, listModels);  //Push the array to DataAdapter
        listView = (ListView) findViewById(R.id.listView);                                           // This will populate the listView
        listView.setAdapter(dataAdapter);                                                           //with data

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {                     //Listens to which class is clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //Professor Account Alert
                if (type) {

                    String[] data = {"Enter code of the day", "View students roster"};    //String to add item to alertDialog

                    //Create new alert dialog
                    alertDialog = new AlertDialog.Builder(Welcome.this);
                    alertDialog.setTitle(listModels.get(position).getClassName());
                    alertDialog.setItems(data, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {

                                //If enter new code is selected
                                case 0:
                                    alertDialog = new AlertDialog.Builder(Welcome.this);
                                    text = new EditText(getApplicationContext());
                                    text.setInputType(InputType.TYPE_CLASS_TEXT);
                                    alertDialog.setView(text);
                                    alertDialog.setTitle(listModels.get(position).getClassName());
                                    alertDialog.setMessage("Please enter the new code");
                                                  //Get new code that was entered
                                    alertDialog.setPositiveButton("Enter",

                                            //Parse and send new code
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    /*When the alert dialog is clicked for professor to submit the new code
                                                    This sends the String Request created in Professor Send Code to the actual
                                                    server to update the database with the new code */
                                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //Sets the new code in code bar and makes toast
                                                            //that code change was succefull on response back from the server
                                                            codeText.setText("Your code currently is \"" +  text.getText().toString() + "\"");
                                                            Toast.makeText(getApplicationContext(), "Your code has successfully been" +
                                                                    " changed", Toast.LENGTH_LONG).show();
                                                        }
                                                    };
                                                    //Sends variables to be sent with String Request to website containg the php
                                                    ProfessorSendCode sendCode = new ProfessorSendCode(idExtra, text.getText().toString() , responseListener);
                                                    RequestQueue queue = Volley.newRequestQueue(Welcome.this);
                                                    queue.add(sendCode);


                                                }
                                            });
                                    alertDialog.show();
                                    break;


                                // if view class roster is selected
                                case 1:
                                    tempClass = listModels.get(position).getClassID();
                                    tempSection = listModels.get(position).getSectionNumber();

                                    //Response Listener for when the StringRequest to ProfessorRosterSend is done
                                    //fetching data from website
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject json = new JSONObject(response);
                                                boolean success = json.getBoolean("success");
                                                if (success == true) {
                                                    //Create intent which points to roster class
                                                    Intent intent = new Intent(Welcome.this, RosterActivity.class);
                                                    intent.putExtra("className", listModels.get(position).getClassName());

                                                    /*All data is stored into a 2D json array
                                                    * on the php side all students are added both absent and present
                                                    * and to store data and retrieve it efficiently, all arrays
                                                    * are assigned a name which is actually a number starting from 0
                                                    * Counter is used to retieve first array within array and to have
                                                    * a number to increment to retrieve future arrays
                                                    * inorder to retrieve the next */
                                                    int counter= 0;
                                                    while(json.has(""+counter+"") ){
                                                        JSONArray array = json.getJSONArray(""+counter+"");
                                                        intent.putExtra(""+counter+"", array.toString());
                                                        counter++;
                                                    }

                                                    //When everything is complete open new activity
                                                    Welcome.this.startActivity(intent);
                                                }
                                            }
                                            //There is a server side error
                                            catch (JSONException e) {
                                                Toast.makeText(getApplicationContext(), "There was a catch error", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    //StringRequest to submit request to view roster for a specific class
                                    ProfessorRosterSend sendCode = new ProfessorRosterSend(tempClass, tempSection, currentTime, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(Welcome.this);
                                    queue.add(sendCode);
                                    break;
                            }



                        }
                    });
                    alertDialog.show();


                    //Student Account AlertDialog
                } else {

                    //Create new alert Dialog
                    alertDialog = new AlertDialog.Builder(Welcome.this);
                    text = new EditText(getApplicationContext());
                    text.setInputType(InputType.TYPE_CLASS_TEXT);
                    alertDialog.setView(text);
                    alertDialog.setTitle(listModels.get(position).getClassName());
                    alertDialog.setMessage("Please enter the code of the day");

                    alertDialog.setPositiveButton("Enter",
                            new DialogInterface.OnClickListener() {
                                final String tempText = text.getText().toString();
                                public void onClick(DialogInterface dialog, int which) {

                                    //Response Listener for when the StringRequest to StudentSendCode is done
                                    //fetching data from website
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{

                                                //Tell the user if the code they enter was correct
                                                JSONObject json = new JSONObject(response);
                                                boolean success = json.getBoolean("success");
                                                if (success == true) {
                                                    Toast.makeText(getApplicationContext(), "You have successful been marked here", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(), "Sorry the code is incorrect", Toast.LENGTH_LONG).show();
                                                }
                                            }catch (JSONException e){}
                                        }
                                    };

                                    //StringRequest to submit students code for attendence
                                    StudentSendCoad studentCode = new StudentSendCoad(idExtra, listModels.get(position).getClassID(),
                                            listModels.get(position).getSectionNumber(), text.getText().toString(), currentTime, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(Welcome.this);
                                    queue.add(studentCode);

                                }
                            });

                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(int mPosition) {

    }
}
