package app.kazucon.connpassclient.api;

import app.kazucon.connpassclient.model.EventResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kazuumi on 17/03/18.
 */

public interface GetMethod {

    @GET("?order=3")
    Call<EventResponse> getNewEvents(@Query("start") int startIndex);

    @GET("?order=2")
    Call<EventResponse> getSoonEvents(@Query("ym") String yyyymm, @Query("start") int startIndex);

    @GET("?order=3")
    Call<EventResponse> getJoinEvents(@Query("nickname") String nickname, @Query("start") int startIndex);

}
