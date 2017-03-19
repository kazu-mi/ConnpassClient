package app.kazucon.connpassclient.fragment;

import android.content.Context;
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
import app.kazucon.connpassclient.databinding.FragmentJoinEventsBinding;
import app.kazucon.connpassclient.listener.EndlessScrollListener;
import app.kazucon.connpassclient.model.EventResponse;
import app.kazucon.connpassclient.viewmodel.JoinViewModel;

/**
 * Created by kazuumi on 17/03/18.
 */

public class JoinEventsFragment extends Fragment {

    private final String NICKNAME_KEY = "nickname_key";
    private FragmentJoinEventsBinding joinEventsBind;
    private EventListAdapter eventListAdapter;
    private Request request = new Request();
    private String nickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.joinEventsBind = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_join_events,
                null,
                false
        );

        this.eventListAdapter = new EventListAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        this.joinEventsBind.recyclerView.setLayoutManager(layoutManager);
        this.joinEventsBind.recyclerView.setAdapter(this.eventListAdapter);

        this.joinEventsBind.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventListAdapter.clear();
                requestEventData(nickname, 0);
            }
        });
        this.joinEventsBind.recyclerView.addOnScrollListener(
                new EndlessScrollListener(10, layoutManager) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        requestEventData(nickname, currentPage);
                    }
                }
        );

        this.nickname
                = getActivity().getPreferences(Context.MODE_PRIVATE).getString(NICKNAME_KEY, "");
        this.joinEventsBind.setJoinViewModel(new JoinViewModel(nickname));
        this.joinEventsBind.setActionSetNickName(actionSetNickName);

        if (!this.joinEventsBind.getJoinViewModel().isEmptyNickname()) {
            this.requestEventData(nickname, 0);
        }

        return this.joinEventsBind.getRoot();
    }

    private void requestEventData(String nickname, int page) {
        this.joinEventsBind.refresh.setRefreshing(true);
        request.getJoinEvents(nickname, page, new Request.ResponseCallback() {
            @Override
            public void onSuccess(EventResponse response) {
                joinEventsBind.refresh.setRefreshing(false);
                eventListAdapter.addEvents(response.events);
            }

            @Override
            public void onError(Throwable t) {
                joinEventsBind.refresh.setRefreshing(false);
                t.printStackTrace();
            }
        });

    }

    private View.OnClickListener actionSetNickName = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nickname = joinEventsBind.textNickname.getText().toString();
            getActivity()
                    .getPreferences(Context.MODE_PRIVATE)
                    .edit()
                    .putString(NICKNAME_KEY, nickname)
                    .apply();
            joinEventsBind.getJoinViewModel().setNickname(nickname);

            requestEventData(nickname, 0);
        }
    };

}
