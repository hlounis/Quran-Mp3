package mdweb.com.quranmp3.tools;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Slim Tonnech.
 */

public class JsonRequestHelper {

    //Context
    private Context context;
    //Instance of LocalFilesManager to save the file
    LocalFilesManager locallyFiles;
    // Request's result
    String result;
    String TAG = JsonRequestHelper.class.getSimpleName();
     String fileName;

    public JsonRequestHelper(Context context) {
        this.context = context;
        locallyFiles = new LocalFilesManager(context);
    }

    /**
     * Making JSON STRING GET request
     * Saves String in fileName
     * Returns response Instantly with callback method mOnCompleteListener
     */
    public void makeStringRequestGet(String wsUrl, final String fileName, final ResponseCompleteInterface call ) {
        //Define the response listener
        Response.Listener<String> respStringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response, Object o) {
                Log.d(TAG, response);
                result = response;
                //save json in a local file
                if (fileName != null)
                    locallyFiles.saveLocallyFile(fileName, result);
                //do something with the Callback method using the json result
                if (call != null)
                    call.onResponseComplete(result);
            }
        };
        //Define the Error listener
        Response.ErrorListener resErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                if (call != null)
                    call.onError(volleyError);
            }
        };
        //Define the String Request
        StringRequest strgReq = new StringRequest(Request.Method.GET, wsUrl, respStringListener, resErrorListener);
        // Adding request to requestQueue
        strgReq.setShouldCache(false);
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(strgReq);
    }

    /**
     * Making JSON STRING POST request With PARAMS
     * Saves String in fileName
     * Returns response Instantly with callback method mOnCompleteListener
     */
    public void makeStringRequestPost(String wsUrl, final HashMap<String, String> paramss, final String fileName, final ResponseCompleteInterface call ) {
        //Define the response listener
        Response.Listener<String> respStringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response, Object o) {
                Log.d(TAG, response);
                result = response;
                Log.d("test", result);
                //save json in a local file
                if (fileName != null)
                    locallyFiles.saveLocallyFile(fileName, result);
                //do something with the Callback method using the json result
                if (call != null)
                    call.onResponseComplete(result);


            }
        };
        //Define the Error listener
        Response.ErrorListener resErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                if (call != null)
                    call.onError(volleyError);
            }
        };
        //Define the String Request
        StringRequest strgReq = new StringRequest(Request.Method.POST, wsUrl, respStringListener, resErrorListener) {
            @Override
            protected Map<String, String> getParams() {
                return paramss;
            }
        };
        // Adding request to requestQueue
        strgReq.setShouldCache(false);
        VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(strgReq);
    }
}
