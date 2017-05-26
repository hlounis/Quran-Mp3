package malek.com.quranmp3.tools;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static RetrofitSingleton ourInstance;
    private final ApiService apiService;

    private RetrofitSingleton() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        apiService = retrofit.create(ApiService.class);

    }

    public static RetrofitSingleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitSingleton();
        }
        return ourInstance;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
