package app.kazucon.connpassclient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.adapter.EventListAdapter;
import app.kazucon.connpassclient.databinding.FragmentFavoriteEventsBinding;
import app.kazucon.connpassclient.helper.FavoriteEventHelper;
import app.kazucon.connpassclient.listener.EndlessScrollListener;
import app.kazucon.connpassclient.model.EventResponse;

/**
 * Created by kazuumi on 17/03/18.
 */

public class FavoriteEventsFragment extends Fragment {

    private FragmentFavoriteEventsBinding favoriteEventsBind;
    private EventListAdapter eventListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.favoriteEventsBind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_favorite_events,
                null,
                false
        );

        this.eventListAdapter = new EventListAdapter(getActivity());
        this.eventListAdapter.setFavoriteEnable(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.favoriteEventsBind.recyclerView.setLayoutManager(layoutManager);
        this.favoriteEventsBind.recyclerView.setAdapter(this.eventListAdapter);

        FavoriteEventHelper.getInstance().addFavoriteChangeListener(new FavoriteEventHelper.FavoriteChangeListener() {
            @Override
            public void onChange(List<EventResponse.Event> events) {
                eventListAdapter.clear();
                eventListAdapter.addEvents(events);
            }
        });

        return this.favoriteEventsBind.getRoot();
    }

}
