package malek.com.quranmp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import malek.com.quranmp3.models.Recitation;
import malek.com.quranmp3.models.RecitationsContainer;
import malek.com.quranmp3.tools.RetrofitSingleton;

public class RecitationActivity extends ListViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setData() {
        getId();
        if (id != null) {
            RetrofitSingleton.getInstance().getApiService().getRecitationsContainer(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<RecitationsContainer, List<Recitation>>() {
                        @Override
                        public List<Recitation> apply(@NonNull RecitationsContainer recitationsContainer) throws Exception {
                            return recitationsContainer.getRecitations();
                        }
                    })
                    .flatMap(new Function<List<Recitation>, ObservableSource<Recitation>>() {
                        @Override
                        public ObservableSource<Recitation> apply(@NonNull List<Recitation> recitations) throws Exception {
                            return io.reactivex.Observable.fromIterable(recitations);
                        }
                    }).safeSubscribe(new Observer<Recitation>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Recitation recitation) {
                    progressBar.setVisibility(View.GONE);
                    quraAdapter.addItem(recitation);
                }

                @Override
                public void onError(Throwable e) {
                    progressBar.setVisibility(View.GONE);

                    Log.e("Error", e.toString());
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onComplete() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void onClickItem(int position) {
        super.onClickItem(position);
        Intent intent = new Intent(RecitationActivity.this, SwarActivity.class);
        Recitation recitation = (Recitation) quraAdapter.getApiModels().get(position);
        intent.putExtra("id", recitation.getId());
        startActivityForResult(intent, SwarActivity.REQUEST_CODE_SWAR);
        listViewQuray.setEnabled(true);
    }
}
