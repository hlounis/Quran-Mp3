package mdweb.com.quranmp3;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;

import java.util.ArrayList;

import mdweb.com.quranmp3.models.Qura;
import mdweb.com.quranmp3.tools.DataParser;
import mdweb.com.quranmp3.tools.LocalFilesManager;
import mdweb.com.quranmp3.tools.ResponseCompleteInterface;
import mdweb.com.quranmp3.tools.Urls;

public class QuraActivity extends ListViewActivity implements SearchView.OnQueryTextListener {


    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    private String url;
    private Qura quraCurent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }


    }

    private void doMySearch(String query) {
        Log.d("query", query + "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setData() {
        data = DataParser.getListQura(new LocalFilesManager(this).getFileContentText(Urls.File_Qura), Urls.cle_Qura, Urls.cle_Qura_Url, true);
    }


    @Override
    public void passage() {
        super.passage();
        progressDialog.dismiss();
        Intent intent;
        if (quraCurent.getCount() == 1) {
            intent = new Intent(QuraActivity.this, SwarActivity.class);
            intent.putExtra("JsonSwar", jsonDownload);
            intent.putExtra("comeQura",true);
        } else {
            intent = new Intent(QuraActivity.this, RecitationActivity.class);
            intent.putExtra("response", jsonDownload);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        quraCurent = quraAdapter.getQuras().get(position);
        if (quraAdapter.getQuras().get(position).getCount() == 1) {
            url = quraCurent.getApi_url().substring(0, quraCurent.getApi_url().indexOf("get-author"));
            url = url + "get-recitation/" + quraCurent.getUniqueResitance() + "/ar/json/";
            Log.d("url", url);

        } else {
            url = quraCurent.getApi_url();
        }
        jsonRequestHelper.makeStringRequestGet(url, null, new ResponseCompleteInterface() {
            @Override
            public void onResponseComplete(String response) {
                jsonDownload = response;
                downloaded = true;
                if (progressDialog.isShowing() && runing) {
                    passage();
                    listViewQuray.setEnabled(true);
                }


            }

            @Override
            public void onError(VolleyError volleyError) {
                if (runing) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    listViewQuray.setEnabled(true);
                }


            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("newText", newText);
        ArrayList<Qura> qurasFiltred = new ArrayList<>();
        if (newText.length() > 0) {

            for (Qura qura : data) {
                if (qura.getTitle().contains(newText)) {

                    qurasFiltred.add(qura);

                }
            }
            quraAdapter.setQuras(qurasFiltred);
            quraAdapter.notifyDataSetChanged();

        } else {
            quraAdapter.setQuras(data);
            quraAdapter.notifyDataSetChanged();

        }

        return false;
    }
}
