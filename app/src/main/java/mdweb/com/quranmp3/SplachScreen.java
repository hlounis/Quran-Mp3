package mdweb.com.quranmp3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.VolleyError;

import mdweb.com.quranmp3.tools.JsonRequestHelper;
import mdweb.com.quranmp3.tools.ResponseCompleteInterface;
import mdweb.com.quranmp3.tools.Urls;

public class SplachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonRequestHelper jsonRequestHelper = new JsonRequestHelper(this);
        jsonRequestHelper.makeStringRequestGet(Urls.Url_Qura, Urls.File_Qura, new ResponseCompleteInterface() {
            @Override
            public void onResponseComplete(String response) {

            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =new Intent(SplachScreen.this,QuraActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
