package com.ted.lcmanager.app.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ted.lcmanager.app.Constants;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ted on 2015/3/27.
 */
public class MyJsonRequest extends JsonObjectRequest{
    public MyJsonRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public MyJsonRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public MyJsonRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type", "application/json");
        headers.put("X-AVOSCloud-Application-Id", Constants.SERVER_ID);
        headers.put("X-AVOSCloud-Application-Key", Constants.SERVER_KEY);
        return headers;
        //return super.getHeaders();
    }


}
