package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.database.DBAccess;
import com.android.osloh.nodata.ui.database.SMSRealmObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryFragment extends MainFragment implements ConversationSwipeAdapter.OnItemClickListener {

    @Bind(R.id.list_of_conversations) RecyclerView mConversationList;
    private ConversationSwipeAdapter mDataAdapter;

    public static GalleryFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        displayListView();
        return (container == null) ? null : view;
    }

    private void displayListView() {
        Log.d("Gallery NoDa", "Get sms in db");
        List<SMSRealmObject> smsRealmObjects = DBAccess.getInstance(getActivity()).getFirstOfConversation();
        Log.d("Gallery NoDa", "Create adapter");
        if (mDataAdapter == null) mDataAdapter = new ConversationSwipeAdapter(getActivity());
        mDataAdapter.setOnItemClickListener(this);
        mConversationList.setAdapter(mDataAdapter);
        mConversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataAdapter.update(smsRealmObjects);
        Log.d("Gallery NoDa", "List displayed");
    }

    @Override
    protected String getTitle() {
        return "noda";
    }

    @Override
    public void onClick(SMSRealmObject conversation) {
        Bundle bundle = new Bundle();
        bundle.putString("from", conversation.getFrom());
        bundle.putString("content", conversation.getBody());
        bundle.putString("date", conversation.getDate().toString());
        ((MainActivity) getActivity()).loadFragment(FragmentConstants.Goto.CONVERSATION, bundle);
    }
}