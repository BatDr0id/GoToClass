package com.example.droussos2867.gotoclass;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sempiternalsearch on 11/12/17.
 */

    //This is class send params to website url to retrieve login information
public class StudentLogin extends StringRequest {
    private static String REGISTER_REQUEST_URL = "https://dr1197dr.000webhostapp.com/login_user.php";
    private static String ATTENDANCE_REQUEST_URL;
    private Map<String, String> params;

    public StudentLogin(String user, String password, boolean userType, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("studentID", user);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}