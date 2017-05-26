package malek.com.quranmp3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import malek.com.quranmp3.adapter.QuraAdapter;
import malek.com.quranmp3.tools.RecylerViewClickItem;

public class ListViewActivity extends AppCompatActivity implements RecylerViewClickItem {
    protected ProgressDialog progressDialog;
    protected boolean runing = true;
    protected RecyclerView listViewQuray;
    protected boolean downloaded = false;
    protected QuraAdapter quraAdapter;
    protected Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qura);
        initRecylerView();
        setData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        runing = true;
        if (downloaded) {
            listViewQuray.setEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        runing = false;
    }

    public void initRecylerView() {
        listViewQuray = (RecyclerView) findViewById(R.id.recycler);
        listViewQuray.setLayoutManager(new LinearLayoutManager(this));
        quraAdapter = new QuraAdapter(this);
        listViewQuray.setAdapter(quraAdapter);

    }

    public void setData() {
    }


    @Override
    public void onClickItem(int position) {
        Log.d("onclikItem", position + "");


    }

    public void getId() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getInt("id");
            }
        }
    }
}
