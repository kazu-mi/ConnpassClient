package app.kazucon.connpassclient.helper;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import app.kazucon.connpassclient.model.EventResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kazuumi on 17/03/20.
 */

public class FavoriteEventHelper {

    private LinkedHashMap<Integer, EventResponse.Event> events = new LinkedHashMap<>();
    private Observable<LinkedHashMap<Integer, EventResponse.Event>> eventsChanged;
    private ArrayList<FavoriteChangeListener> listeners = new ArrayList<>();

    private FavoriteEventHelper() {}
    private static FavoriteEventHelper instance;
    public static FavoriteEventHelper getInstance() {
        if (instance == null) {
            instance = new FavoriteEventHelper();
        }

        instance.eventsChanged = Observable.create(new ObservableOnSubscribe<LinkedHashMap<Integer, EventResponse.Event>>() {
            @Override
            public void subscribe(ObservableEmitter<LinkedHashMap<Integer, EventResponse.Event>> e) throws Exception {
                e.onNext(FavoriteEventHelper.getInstance().events);
                e.onComplete();
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());

        return instance;
    }

    public void add(EventResponse.Event event) {
        this.events.put(event.event_id, event);
        eventChange();
    }

    public void remove(int eventId) {
        this.events.remove(eventId);
        eventChange();
    }

    public void addFavoriteChangeListener(FavoriteChangeListener listener) {
        this.listeners.add(listener);
    }

    private void eventChange() {
        this.eventsChanged.subscribe(new Observer<LinkedHashMap<Integer, EventResponse.Event>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(LinkedHashMap<Integer, EventResponse.Event> integerEventLinkedHashMap) {
                for (FavoriteChangeListener listener : listeners) {
                    listener.onChange(new ArrayList<>(events.values()));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public interface FavoriteChangeListener {
        void onChange(List<EventResponse.Event> events);
    }

}
