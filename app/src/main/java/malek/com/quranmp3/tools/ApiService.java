package malek.com.quranmp3.tools;

import malek.com.quranmp3.models.QuarContainer;
import malek.com.quranmp3.models.RecitationsContainer;
import malek.com.quranmp3.models.SawrContainer;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("get-category/{id}/ar/json/")
    io.reactivex.Observable<QuarContainer> getQuarContainer(@Path("id") Integer id);

    @GET("get-author/{id}/ar/json/")
    io.reactivex.Observable<RecitationsContainer> getRecitationsContainer(@Path("id") Integer id);

    @GET("get-recitation/{id}/ar/json/")
    io.reactivex.Observable<SawrContainer> getSawrContainer(@Path("id") Integer id);
}
