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
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.utils.ConversationReader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class GalleryFragmentSample extends MainFragment implements ConversationSwipeAdapter.OnItemClickListener {

    @Bind(R.id.list_of_conversations)
    RecyclerView mConversationList;
    private ConversationSwipeAdapter mDataAdapter;

    public static GalleryFragmentSample newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragmentSample();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        displayListView();
        return (container == null) ? null : view;
    }

    private void displayListView() {
        Log.d("Gallery NoDa", "Create adapter");
        if (mDataAdapter == null) mDataAdapter = new ConversationSwipeAdapter(getActivity());
        mDataAdapter.setOnItemClickListener(this);
        mConversationList.setAdapter(mDataAdapter);
        mConversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataAdapter.update(ConversationReader.getInstance().getAllConversation(getActivity().getContentResolver()));
        Log.d("Gallery NoDa", "List displayed");
    }

    @Override
    protected String getTitle() {
        return "noda";
    }

    @Override
    public void onClick(ConversationItemBean conversation) {
        Bundle bundle = new Bundle();
        bundle.putString("from", "");
        bundle.putString("date", "");
        ((MainActivity) getActivity()).addFragment(FragmentConstants.Goto.CONVERSATION, bundle);
    }
}