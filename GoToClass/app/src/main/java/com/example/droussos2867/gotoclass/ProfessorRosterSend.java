package com.example.droussos2867.gotoclass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sempiternalsearch on 11/30/17.
 */
    //This class sends params to website url to retieve the student roster
public class ProfessorRosterSend extends StringRequest{
    //url to php that process params and returns roster data
    private static String ROSTER_REQUEST_URL = "https://dr1197dr.000webhostapp.com/professor_roster.php";
    private Map<String, String> params;

    public ProfessorRosterSend(String classID, String sectionID, String date, Response.Listener<String> listener) {

        super(Method.POST, ROSTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("classID", classID);
        params.put("sectionID", sectionID);
        params.put("date", date);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}

