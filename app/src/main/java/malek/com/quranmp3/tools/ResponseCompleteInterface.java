package malek.com.quranmp3.tools;

/**
 * Interface of CallBack Method
 */

import com.android.volley.VolleyError;

/**
 * Created by Slim Tonnech.
 */
public interface ResponseCompleteInterface {
    void onResponseComplete(String response);// Callback method
    void onError(VolleyError volleyError);// Callback method
}
