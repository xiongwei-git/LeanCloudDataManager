package com.ted.lcmanager.app.network;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ted.lcmanager.app.Constants;
import org.json.JSONObject;

/**
 * Created by Ted on 2015/3/28.
 */
public class VolleyClient {
    private RequestQueue mRequestQueue;

    public VolleyClient(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void sendRequest(JsonObjectRequest myJsonRequest){
        mRequestQueue.add(myJsonRequest);
    }
}
