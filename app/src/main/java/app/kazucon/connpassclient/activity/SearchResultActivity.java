package app.kazucon.connpassclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import app.kazucon.connpassclient.R;
import app.kazucon.connpassclient.adapter.EventListAdapter;
import app.kazucon.connpassclient.api.Request;
import app.kazucon.connpassclient.databinding.ActivitySearchResultBinding;
import app.kazucon.connpassclient.listener.EndlessScrollListener;
import app.kazucon.connpassclient.model.EventResponse;

import static app.kazucon.connpassclient.R.attr.layoutManager;

public class SearchResultActivity extends AppCompatActivity {

    public static void start(Activity from, String keyword) {
        Intent intent = new Intent(from, SearchResultActivity.class);
        intent.putExtra("keyword", keyword);
        from.startActivity(intent);
    }

    private ActivitySearchResultBinding searchResultBind;
    private EventListAdapter eventListAdapter;
    private Request request = new Request();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.searchResultBind = DataBindingUtil.setContentView(this, R.layout.activity_search_result);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.eventListAdapter = new EventListAdapter(this);
        this.searchResultBind.recyclerView.setLayoutManager(layoutManager);
        this.searchResultBind.recyclerView.setAdapter(this.eventListAdapter);

        this.searchResultBind.refresh.setEnabled(false);

        final String keyword = getIntent().getStringExtra("keyword");
        requestEventData(keyword, 0);

        this.searchResultBind.recyclerView.addOnScrollListener(
                new EndlessScrollListener(10, layoutManager) {
                    @Override
                    public void onLoadMore(int currentPage) {
                        requestEventData(keyword, currentPage);
                    }
                }
        );
    }

    private void requestEventData(String keyword, int page) {
        this.searchResultBind.refresh.setRefreshing(true);
        request.searchEvent(keyword, page, new Request.ResponseCallback() {
            @Override
            public void onSuccess(EventResponse response) {
                searchResultBind.refresh.setRefreshing(false);
                eventListAdapter.addEvents(response.events);
            }

            @Override
            public void onError(Throwable t) {
                searchResultBind.refresh.setRefreshing(false);
                t.printStackTrace();
            }
        });

    }

}
