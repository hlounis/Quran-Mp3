package malek.com.quranmp3;

import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import malek.com.quranmp3.tools.Urls;
import malek.com.quranmp3.tools.VolleySingleton;

public class RecitationActivity extends ListViewActivity {
    public final static String KEY_INTENT = "response";

    @Override
    public void setJsonObject() {
        super.setJsonObject();
        jsonDownload = getJsonFromIntent(KEY_INTENT);
    }

    @Override
    public void setData() {
        creatlistAsyn(Urls.cle_recitation, Urls.cle_Qura_Url);
    }

    @Override
    public void passage() {
        super.passage();
        progressDialog.dismiss();
        Intent intent = new Intent(RecitationActivity.this, SwarActivity.class);
        intent.putExtra(SwarActivity.KEY_INTENT, jsonDownload.toString());
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
