package com.example.droussos2867.gotoclass;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sempiternalsearch on 11/28/17.
 */
    //This class sends params to website url to change the professors code
class ProfessorSendCode extends StringRequest {

    private static String REGISTER_REQUEST_URL = "https://dr1197dr.000webhostapp.com/professor_COD.php";
    private Map<String, String> params;

    public ProfessorSendCode(String user, String code, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("professorID", user);
        params.put("code", code);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
