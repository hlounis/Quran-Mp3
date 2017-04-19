package mdweb.com.quranmp3;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;

import mdweb.com.quranmp3.models.ApiModel;
import mdweb.com.quranmp3.tools.DataParser;
import mdweb.com.quranmp3.tools.VolleySingleton;

public class QuraActivity extends ListViewActivity implements SearchView.OnQueryTextListener {


    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    private String url;
    private ApiModel apiModelCurent;

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
        data = DataParser.getInstance().getApiModels();
    }


    @Override
    public void passage() {
        super.passage();
        progressDialog.dismiss();
        Intent intent;
        int requestCode = 9;
        String key = "JsonSwar";
        if (apiModelCurent.getCount() == 1) {
            intent = new Intent(QuraActivity.this, SwarActivity.class);
            requestCode = SwarActivity.REQUEST_CODE_SWAR;
        } else {
            intent = new Intent(QuraActivity.this, RecitationActivity.class);
            key = "response";
        }
        intent.putExtra(key, jsonDownload.toString());
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        apiModelCurent = quraAdapter.getApiModels().get(position);
        if (quraAdapter.getApiModels().get(position).getCount() == 1) {
            url = apiModelCurent.getApi_url().substring(0, apiModelCurent.getApi_url().indexOf("get-author"));
            url = url + "get-recitation/" + apiModelCurent.getUniqueResitance() + "/ar/json/";
        } else {
            url = apiModelCurent.getApi_url();
        }
        VolleySingleton.getInstance(this).addToRequestQueue(new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response, Object o) {
                jsonDownload = response;
                downloaded = true;
                if (progressDialog.isShowing() && runing) {
                    passage();
                    listViewQuray.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                if (runing) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    listViewQuray.setEnabled(true);
                }
            }
        }));
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("newText", newText);
        ArrayList<ApiModel> qurasFiltred = new ArrayList<>();
        if (newText.length() > 0) {

            for (ApiModel apiModel : data) {
                if (apiModel.getTitle().contains(newText)) {

                    qurasFiltred.add(apiModel);

                }
            }
            quraAdapter.setApiModels(qurasFiltred);
            quraAdapter.notifyDataSetChanged();

        } else {
            quraAdapter.setApiModels(data);
            quraAdapter.notifyDataSetChanged();

        }

        return false;
    }
}
