package mdweb.com.quranmp3;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import mdweb.com.quranmp3.tools.DataParser;
import mdweb.com.quranmp3.tools.Urls;
import mdweb.com.quranmp3.tools.VolleySingleton;

public class RecitationActivity extends ListViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData() {
        try {
            data = DataParser.createList(new JSONObject(getIntent().getExtras().getString("response")), Urls.cle_recitation, Urls.cle_Qura_Url, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void passage() {
        super.passage();
        progressDialog.dismiss();
        Intent intent = new Intent(RecitationActivity.this, SwarActivity.class);
        intent.putExtra("JsonSwar", jsonDownload.toString());
        startActivityForResult(intent, SwarActivity.REQUEST_CODE_SWAR);
        listViewQuray.setEnabled(true);
    }

    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        VolleySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest(data.get(position).getApi_url(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response, Object o) {
                jsonDownload = response;
                downloaded = true;
                if (progressDialog.isShowing() && runing) {
                    passage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                if (progressDialog.isShowing() && runing) {
                    progressDialog.dismiss();
                    listViewQuray.setEnabled(true);
                }
            }
        }));


    }
}
