package mdweb.com.quranmp3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

import mdweb.com.quranmp3.adapter.QuraAdapter;
import mdweb.com.quranmp3.models.ApiModel;
import mdweb.com.quranmp3.tools.RecylerViewClickItem;

public class ListViewActivity extends AppCompatActivity implements RecylerViewClickItem {
    protected List<ApiModel> data;
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
        setData();
        quraAdapter = new QuraAdapter(this, R.layout.item_list, data);
        listViewQuray = (RecyclerView) findViewById(R.id.recycler);
        listViewQuray.setLayoutManager(new LinearLayoutManager(this));
        if (listViewQuray != null) {
            listViewQuray.setAdapter(quraAdapter);
            quraAdapter.setRecylerViewClickItem(this);
        }
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

    public void setData() {
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
