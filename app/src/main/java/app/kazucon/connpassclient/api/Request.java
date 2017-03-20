package app.kazucon.connpassclient.api;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

import app.kazucon.connpassclient.model.EventResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * Created by kazuumi on 17/03/18.
 */

public class Request {

    private final int MAX_RESPONSE_EVENT_COUNT = 10;
    private HttpClient httpClient = new HttpClient();

    /**
     * 新着イベント取得
     */
    public void getNewEvents(int page, final ResponseCallback callback) {
        Call<EventResponse> call = httpClient.getMethod.getNewEvents(page * MAX_RESPONSE_EVENT_COUNT);
        runRequest(call, callback);
    }

    /**
     * 今月開催イベント取得
     */
    public void getSoonEvents(int page, final ResponseCallback callback) {
        Calendar today = Calendar.getInstance();
        String query = String.format("%04d%02d",
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH) + 1
                );
        Call<EventResponse> call = httpClient.getMethod.getSoonEvents(query, page * MAX_RESPONSE_EVENT_COUNT);
        runRequest(call, callback);
    }

    /**
     * 参加イベント取得
     */
    public void getJoinEvents(String nickname, int page, final ResponseCallback callback) {
        Call<EventResponse> call = httpClient.getMethod.getJoinEvents(nickname, page * MAX_RESPONSE_EVENT_COUNT);
        runRequest(call, callback);
    }

    /**
     * イベント検索
     */
    public void searchEvent(String keyword, int page, final ResponseCallback callback) {
        Call<EventResponse> call = httpClient.getMethod.searchEventsWithKeyword(keyword, page * MAX_RESPONSE_EVENT_COUNT);
        runRequest(call, callback);
    }

    /**
     * リクエスト実行共通処理
     * @param call Retrofit Call
     * @param callback レスポンス取得コールバック
     * @param <T> 取得データモデル
     */
    private <T extends Serializable> void runRequest(final Call<T> call, final ResponseCallback callback) {
        Observable.create(new ObservableOnSubscribe<EventResponse>() {
            @Override
            public void subscribe(ObservableEmitter<EventResponse> e) throws Exception {
                httpClient.enqueue(call, new HttpClient.HttpResponseCallback() {
                    @Override
                    public void onSuccess(Object responseBody) {
                        if (responseBody instanceof EventResponse) {
                            callback.onSuccess((EventResponse) responseBody);

                        } else {
                            callback.onError(new Throwable(String.valueOf(responseBody)));
                        }
                    }

                    @Override
                    public void onFailed(Throwable t) {
                        callback.onError(t);
                    }
                });
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<EventResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(EventResponse eventResponse) {
                callback.onSuccess(eventResponse);
            }

            @Override
            public void onError(Throwable e) {
                callback.onError(e);
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public interface ResponseCallback {
        void onSuccess(EventResponse response);
        void onError(Throwable t);
    }

}
