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

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.adapter.EventListAdapter;
import app.kazucon.connpassclient.api.Request;
import app.kazucon.connpassclient.databinding.FragmentSoonEventsBinding;
import app.kazucon.connpassclient.listener.EndlessScrollListener;
import app.kazucon.connpassclient.model.EventResponse;

/**
 * Created by kazuumi on 17/03/18.
 */

public class SoonEventsFragment extends Fragment {

    private FragmentSoonEventsBinding soonEventsBind;
    private EventListAdapter eventListAdapter;
    Request request = new Request();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.soonEventsBind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_soon_events,
                null,
                false
        );

        this.eventListAdapter = new EventListAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.soonEventsBind.recyclerView.setLayoutManager(layoutManager);
        this.soonEventsBind.recyclerView.setAdapter(this.eventListAdapter);

        this.soonEventsBind.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventListAdapter.clear();
                requestEventData(0);
            }
        });
        this.requestEventData(0);

        this.soonEventsBind.recyclerView.addOnScrollListener(
                new EndlessScrollListener(10, layoutManager) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        requestEventData(currentPage);
                    }
                }
        );

        return this.soonEventsBind.getRoot();
    }

    private void requestEventData(int page) {
        this.soonEventsBind.refresh.setRefreshing(true);
        request.getSoonEvents(page, new Request.ResponseCallback() {
            @Override
            public void onSuccess(EventResponse response) {
                soonEventsBind.refresh.setRefreshing(false);
                eventListAdapter.addEvents(response.events);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                soonEventsBind.refresh.setRefreshing(false);
            }
        });
    }

}
