package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;
import com.android.osloh.nodata.ui.bean.ConversationItemBean;
import com.android.osloh.nodata.ui.constant.ExtraConstants;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.utils.ConversationReader;
import com.android.osloh.nodata.ui.viewNoData.utils.DividerItemConversation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Show every conversations in the phone (ended or not)
 */
public abstract class GalleryFragmentParent extends MainFragment implements ConversationSwipeAdapter.OnItemClickListener {

    @Bind(R.id.list_of_conversations)
    RecyclerView mConversationList;
    protected ConversationSwipeAdapter mDataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        return (container == null) ? null : view;
    }

    abstract protected void displayListView();

    @Override
    public void onResume() {
        super.onResume();
        displayListView();
    }

    @Override
    protected String getTitle() {
        return "noda";
    }

    @Override
    public void onClick(ConversationItemBean bean) {
        if (bean.getLastMessagesItemBean() == null || bean.getLastMessagesItemBean().isEmpty()) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ExtraConstants.ADDRESS_ID, bean.getLastMessagesItemBean().get(0).getAddress());
        bundle.putString(ExtraConstants.THREAD_ID, bean.getThreadId());
        ((MainActivity) getActivity()).addFragment(FragmentConstants.Goto.CONVERSATION, bundle);
    }
}