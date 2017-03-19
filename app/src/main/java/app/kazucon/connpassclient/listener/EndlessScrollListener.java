package app.kazucon.connpassclient.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by kazuumi on 17/03/19.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int scrollThresholdCount;
    private int currentPage     = 0;
    private int prevTotalCount  = 0;
    private boolean isLoading   = true;
    private LinearLayoutManager linearLayoutManager;

    public EndlessScrollListener(int scrollThresholdCount, LinearLayoutManager linearLayoutManager) {
        this.scrollThresholdCount   = scrollThresholdCount;
        this.linearLayoutManager    = linearLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int totalItemCount              = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition    = linearLayoutManager.findFirstVisibleItemPosition();
        int visibleItemCount            = recyclerView.getChildCount();

        if (isLoading) {
            // 新たなデータの読み込みが終了した
            if (totalItemCount > prevTotalCount) {
                isLoading = false;
                prevTotalCount = totalItemCount;
            }
        }
        // しきい値箇所以上スクロールで見えた場合に再読み込みする
        else if ((totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + scrollThresholdCount)) {
            isLoading = true;
            this.currentPage++;

            onLoadMore(this.currentPage);
        }
    }

    public abstract void onLoadMore(int currentPage);
}
