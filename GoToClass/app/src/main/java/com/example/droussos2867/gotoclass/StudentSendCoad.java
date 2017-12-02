package com.example.droussos2867.gotoclass;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sempiternalsearch on 11/20/17.
 */

public class StudentSendCoad extends StringRequest {
    private static String REGISTER_REQUEST_URL = "https://dr1197dr.000webhostapp.com/student_COD.php";
    private Map<String, String> params;


    public StudentSendCoad(String user, String classID, String classSection, String code, String date, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("studentID", user);
        params.put("sectionID", classSection);
        params.put("classID", classID);
        params.put("code", code);
        params.put("date", date);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
