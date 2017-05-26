package malek.com.quranmp3.tools;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import malek.com.quranmp3.adapter.QuraAdapter;
import malek.com.quranmp3.models.ApiModel;

public class SearchUtils {
    public final static String TAG = SearchUtils.class.getSimpleName();


    public static void doShearch(final String query, final QuraAdapter adapter) {
        Log.d(TAG, adapter.getApiModels().toString());
        Observable.fromIterable(adapter.getApiModels())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<ApiModel>() {
                    @Override
                    public boolean test(@NonNull ApiModel apiModel) throws Exception {
                        Log.d(TAG, query);
                        String testString = apiModel.getTitle().substring(0, query.length());
                        if (testString.contains("أ")) {
                            testString = testString.replace("أ", "ا");
                        }
//                        if (testString.contains("سورة ")) {
//                            testString = testString.replace("سورة ", "");
//                        }
                        return testString.equals(query);
                    }
                }).subscribe(new Observer<ApiModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                adapter.clearAdapter();
            }

            @Override
            public void onNext(ApiModel apiModel) {
                adapter.addItem(apiModel);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
