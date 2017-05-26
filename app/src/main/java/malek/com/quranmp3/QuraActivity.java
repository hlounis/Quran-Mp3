package malek.com.quranmp3;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import malek.com.quranmp3.models.Author;
import malek.com.quranmp3.models.QuarContainer;
import malek.com.quranmp3.tools.RetrofitSingleton;

public class QuraActivity extends ListViewActivity {


    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    private Author authorSelected;
    private List<Author> authors = new ArrayList<>();


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
//        mSearchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void setData() {
        RetrofitSingleton.getInstance().getApiService().getQuarContainer(364794)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<QuarContainer, List<Author>>() {
                    @Override
                    public List<Author> apply(@NonNull QuarContainer quarContainer) throws Exception {
                        return quarContainer.getAuthors();
                    }
                }).flatMap(new Function<List<Author>, ObservableSource<Author>>() {
            @Override
            public ObservableSource<Author> apply(@NonNull List<Author> authors) throws Exception {
                return io.reactivex.Observable.fromIterable(authors);
            }
        }).subscribe(new Observer<Author>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Author author) {

                quraAdapter.addItem(author);
                authors.add(author);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Throwable", e.toString());
            }

            @Override
            public void onComplete() {
                downloaded = true;
            }
        });
    }


    //    @Override
//    public void passage() {
//        super.passage();
//        progressDialog.dismiss();
//        Intent intent;
//        int requestCode = 9;
//        String key;
//        if (authorSelected.getCount() == 1) {
//            intent = new Intent(QuraActivity.this, SwarActivity.class);
//            requestCode = SwarActivity.REQUEST_CODE_SWAR;
//            key = SwarActivity.KEY_INTENT;
//        } else {
//            intent = new Intent(QuraActivity.this, RecitationActivity.class);
//            key = RecitationActivity.KEY_INTENT;
//        }
//        intent.putExtra(key, jsonDownload.toString());
//        startActivityForResult(intent, requestCode);
//    }
//
//
    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        authorSelected = authors.get(position);
        Log.d("clicked", authorSelected.toString());
        if (authorSelected.getRecitations_info().getCount() == 1) {
            Intent intent = new Intent(QuraActivity.this, SwarActivity.class);
            intent.putExtra("id", authorSelected.getRecitations_info().getRecitations_ids().get(0));
            startActivityForResult(intent, 9);
        } else {
            Intent intent = new Intent(QuraActivity.this, RecitationActivity.class);
            intent.putExtra("id", authorSelected.getId());
            startActivityForResult(intent, 9);
        }
    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }

//    @Override
//    public boolean onQueryTextChange(String newText) {
//        Log.d("newText", newText);
//        ArrayList<Author> qurasFiltred = new ArrayList<>();
//        if (newText.length() > 0) {
//
//            for (ApiModel apiModel : quraAdapter.getApiModels()) {
//                if (apiModel.getTitle().contains(newText)) {
//
//                    qurasFiltred.add((Author) apiModel);
//
//                }
//            }
//            quraAdapter.setApiModels(qurasFiltred);
//            quraAdapter.notifyDataSetChanged();
//
//        } else {
//            quraAdapter.setApiModels(data);
//            quraAdapter.notifyDataSetChanged();
//
//        }
//
//        return false;
//    }
}
