package com.example.droussos2867.gotoclass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


    /*--------------------Mock Login Information-------------------------
    *
    * Professor Account:
    * username: professor1  password: guest
    *
    * Student Account:
    * username: student1  password: guest
    * username: student2  password: guest
    *
    * -------------------------------------------------------------------*/
public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button bLogin;
    private EditText eUsername, ePassword;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;
    private RadioGroup radialSelect;
    private RadioButton selectedR;
    private boolean type;
    private String nameF, nameL, email, userID, imagePath, code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        bLogin = (Button) findViewById(R.id.button);
        radialSelect = (RadioGroup) findViewById(R.id.radioGroup);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        eUsername = (EditText) findViewById(R.id.userIDText);
        ePassword = (EditText) findViewById(R.id.passwordEditText);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();

        //Checks SharedPreference if it contains data and if true to set EditTexts and radiogroup to them
        if (preferences.contains("username")) {
            eUsername.setText(preferences.getString("username", null));
            ePassword.setText(preferences.getString("password", null));
            type = preferences.getBoolean("type", type);
            checkBox.setChecked(true);

            //Selects the correct radio group based on account type from boolean type
            if (type == true) {
                selectedR = (RadioButton) findViewById(R.id.radioButton);
                selectedR.setChecked(true);
            } else {
                selectedR = (RadioButton) findViewById(R.id.radioButton2);
                selectedR.setChecked(true);
            }
        }

    }

    @Override
    //OnClick of the login button
    public void onClick(View v) {

        //Creates SharedPrefernce and adds data
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = preferences.edit();
        final String userid = eUsername.getText().toString();
        final String password = ePassword.getText().toString();

        RadioButton selectID = (RadioButton) findViewById(radialSelect.getCheckedRadioButtonId());

        //Sets type to which account student = false or professor = true
        switch (selectID.getId()) {
            case (R.id.radioButton):
                type = true;
                break;
            case (R.id.radioButton2):
                type = false;
                break;
            default:
                type = false;
        }

        //Find if Remember Me checkbox is checked and append or delete data to SharedPreference
        if (checkBox.isChecked()) {
            editor.putString("username", userid);
            editor.putString("password", password);
            editor.putBoolean("type", type);
            editor.commit();
        }
        if (!checkBox.isChecked()) {
            editor.remove("username");
            editor.remove("password");
            editor.remove("type");
            editor.commit();
        }

        //Response Listener which retrieves the data sent from the String Request
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Code get information from JSONOnject sent back from php
                    //and pushes data to new intent
                    JSONObject json = new JSONObject(response);
                    boolean success = json.getBoolean("success"); //Boolean used to check if the query was parsed correctly

                    if (success) { //if query was successful
                        Intent intent = new Intent(Login.this, Welcome.class);
                        if (type == true) {
                            userID = json.getString("professorID");
                            intent.putExtra("professorID", userID);
                            code = json.getString("code");
                            intent.putExtra("code", code);
                        } else {
                            userID = json.getString("studentID");
                            intent.putExtra("studentID", userID);
                        }
                        nameF = json.getString("firstName");
                        nameL = json.getString("lastName");
                        email = json.getString("email");
                        //If user does not have custom image in website server set to default image
                        if (json.getString("image") != "null") {
                            imagePath = json.getString("image");
                        } else {
                            imagePath = "/images/default.png";
                        }
                        intent.putExtra("image", imagePath);
                        intent.putExtra("firstName", nameF);
                        intent.putExtra("lastName", nameL);
                        intent.putExtra("email", email);
                        intent.putExtra("type",type);
                        //get classes by creating a counter which corresponds to the php
                        //pulled query data for classes data is created into a array
                        //counter will go through each row of class data and push it to next activity through intent
                        int counter= 0;
                        while(json.has(""+counter+"") ){
                            JSONArray array = json.getJSONArray(""+counter+"");
                            intent.putExtra(""+counter+"", array.toString());
                            counter++;
                        }
                        Login.this.startActivity(intent);
                    } else {

                        //Login information is incorrect display toast
                        Toast.makeText(getApplicationContext(), "Incorrect login information", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //If there is a server side error
                    Toast.makeText(getApplicationContext(), "Sorry, somethign is not right", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Uses proper login in class based on account type
        //true = professor account
        //false = student account
        if (type == true) {
            //ProfessorLogin sends included attributes to website to parse data
            ProfessorLogin professorConnection = new ProfessorLogin(userid, password, type, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Login.this); //Create queue
            queue.add(professorConnection); //Send queue
        } else {
            //StudentLogin sends included attributes to website to parse data
            StudentLogin studentConnection = new StudentLogin(userid, password, type, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Login.this);
            queue.add(studentConnection);
        }

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
}
