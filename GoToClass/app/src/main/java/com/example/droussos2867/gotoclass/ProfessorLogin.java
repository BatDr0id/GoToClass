package com.example.droussos2867.gotoclass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sempiternalsearch on 11/15/17.
 */

    //This is class send params to website url to retrieve login information

public class ProfessorLogin extends StringRequest {
    //url to php for professor to login
    private static String REGISTER_REQUEST_URL = "https://dr1197dr.000webhostapp.com/login_professor.php";
    private Map<String,String> params;

    public ProfessorLogin(String user, String password, boolean userType, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        //Add username and password to params
        params = new HashMap<>();
        params.put("professorID", user);
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
