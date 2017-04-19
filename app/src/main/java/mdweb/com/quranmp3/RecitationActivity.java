package mdweb.com.quranmp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;

import mdweb.com.quranmp3.tools.DataParser;
import mdweb.com.quranmp3.tools.ResponseCompleteInterface;
import mdweb.com.quranmp3.tools.Urls;

public class RecitationActivity extends ListViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData() {
        data = DataParser.getListQura(getIntent().getExtras().getString("response"), Urls.cle_recitation, Urls.cle_Qura_Url,false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, QuraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void passage() {
        super.passage();
        progressDialog.dismiss();
        Intent intent = new Intent(RecitationActivity.this, SwarActivity.class);
        intent.putExtra("JsonSwar", jsonDownload);
        intent.putExtra("JsonRecitation", getIntent().getExtras().getString("response"));
        startActivity(intent);
        listViewQuray.setEnabled(true);
    }

    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        jsonRequestHelper.makeStringRequestGet(data.get(position).getApi_url(), null, new ResponseCompleteInterface() {
            @Override
            public void onResponseComplete(String response) {
                Log.d("reponse", response + "");
                jsonDownload = response;
                downloaded = true;
                if (progressDialog.isShowing() && runing) {
                    passage();
                }


            }

            @Override
            public void onError(VolleyError volleyError) {
                if (progressDialog.isShowing() && runing) {
                    progressDialog.dismiss();
                    listViewQuray.setEnabled(true);
                }


            }
        });


    }
}
