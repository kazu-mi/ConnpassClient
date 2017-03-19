package app.kazucon.connpassclient.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.*;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kazuumi on 17/03/18.
 */

public class HttpClient {

    public final GetMethod getMethod;

    public HttpClient() {
        Retrofit retrofit
                = new Retrofit.Builder()
                .baseUrl("https://connpass.com/api/v1/event/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        this.getMethod = retrofit.create(GetMethod.class);
    }

    public <T extends Serializable> void enqueue(Call<T> call, final HttpResponseCallback httpResponseCallback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    httpResponseCallback.onSuccess(response.body());
                } else {
                    httpResponseCallback.onFailed(new Throwable(response.errorBody().toString()));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                httpResponseCallback.onFailed(t);
            }
        });
    }

    public interface HttpResponseCallback {
        void onSuccess(Object responseBody);
        void onFailed(Throwable t);
    }

}
