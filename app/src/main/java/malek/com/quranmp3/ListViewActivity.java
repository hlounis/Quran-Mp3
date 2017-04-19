package malek.com.quranmp3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import malek.com.quranmp3.adapter.QuraAdapter;
import malek.com.quranmp3.models.ApiModel;
import malek.com.quranmp3.tools.DataParser;
import malek.com.quranmp3.tools.RecylerViewClickItem;

public class ListViewActivity extends AppCompatActivity implements RecylerViewClickItem {
    protected List<ApiModel> data = new ArrayList<>();
    protected ProgressDialog progressDialog;
    protected boolean runing = true;
    protected RecyclerView listViewQuray;
    protected boolean downloaded = false;
    protected JSONObject jsonDownload;
    protected QuraAdapter quraAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qura);
        setJsonObject();
        setData();

    }

    public JSONObject getJsonFromIntent(String key) {
        try {
            return new JSONObject(getIntent().getExtras().getString(key));
        } catch (JSONException e) {
            finish();
            return null;
        }
    }

    public void setJsonObject() {
    }

    public void passage() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        runing = true;
        if (downloaded && progressDialog.isShowing()) {
            progressDialog.dismiss();
            listViewQuray.setEnabled(true);
            if (!jsonDownload.equals(""))
                passage();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        runing = false;
    }

    public void initRecylerView() {
        quraAdapter = new QuraAdapter(this, R.layout.item_list, data);
        listViewQuray = (RecyclerView) findViewById(R.id.recycler);
        listViewQuray.setLayoutManager(new LinearLayoutManager(this));
        if (listViewQuray != null) {
            listViewQuray.setAdapter(quraAdapter);
            quraAdapter.setRecylerViewClickItem(this);
        }

    }

    public void setData() {
    }

    public void creatlistAsyn(String keyPrincipal, String keySec) {
        if (jsonDownload != null) {
            Observable.fromIterable(DataParser.createList(jsonDownload, keyPrincipal, keySec, false))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()).
                    subscribe(new Observer<ApiModel>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiModel apiModel) {
                            data.add(apiModel);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("error", e.toString());
                        }

                        @Override
                        public void onComplete() {
                            initRecylerView();

                        }
                    });
        }

    }

    @Override
    public void onClickItem(int position) {
        Log.d("onclikItem", position + "");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        progressDialog.setCancelable(false);
        listViewQuray.setEnabled(false);

    }
}
