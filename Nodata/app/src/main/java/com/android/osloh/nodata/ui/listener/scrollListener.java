package com.android.osloh.nodata.ui.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Charles on 22/01/2016.
 */
public class scrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean loading;
    private int previousTotal;
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItemIndex;

    public scrollListener(RecyclerView rv, LinearLayoutManager lm){
        mRecyclerView = rv;
        mLayoutManager = lm;
        loading = false;
        previousTotal = 0;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

            if (isLastItemDisplaying(mRecyclerView)) {
                // End of list has been reached
                // also triggered if not enough items to fill the screen
                Log.d("ScrollListener", "end");
            } else if (isLFirstItemDisplaying(mRecyclerView)){
                // top of list reached
                Log.d("ScrollListener", "top");
            }
    }
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }
    private boolean isLFirstItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleItemPosition != RecyclerView.NO_POSITION && firstVisibleItemPosition == 0)
                return true;
        }
        return false;
    }
}
