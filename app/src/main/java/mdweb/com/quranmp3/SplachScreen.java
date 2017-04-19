package mdweb.com.quranmp3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import mdweb.com.quranmp3.tools.DataParser;
import mdweb.com.quranmp3.tools.Urls;
import mdweb.com.quranmp3.tools.VolleySingleton;

public class SplachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Urls.Url_Qura, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject, Object o) {
                Intent intent = new Intent(SplachScreen.this, QuraActivity.class);
                startActivity(intent);
                DataParser.getInstance().setListQuraJson(jsonObject);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                Toast.makeText(SplachScreen.this, "Connection problem", Toast.LENGTH_LONG).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}
