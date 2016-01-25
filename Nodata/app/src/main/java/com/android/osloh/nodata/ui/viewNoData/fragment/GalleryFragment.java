package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.database.DBAccess;
import com.android.osloh.nodata.ui.database.SMSRealmObject;
import com.android.osloh.nodata.ui.listener.scrollListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class GalleryFragment extends MainFragment implements ConversationSwipeAdapter.OnItemClickListener {

    @Bind(R.id.list_of_conversations)
    RecyclerView mConversationList;
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
        Log.d("Gallery NoData", "Get sms in db");
        DBAccess.getInstance(getActivity()).update();
        List<SMSRealmObject> smsRealmObjects = DBAccess.getInstance(getActivity()).getFirstOfConversation();
        Log.d("Gallery NoData", "Create adapter");
        if (mDataAdapter == null) mDataAdapter = new ConversationSwipeAdapter(getActivity());
        mDataAdapter.setOnItemClickListener(this);
        mConversationList.setAdapter(mDataAdapter);
        mConversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mConversationList.addOnScrollListener(new scrollListener(mConversationList, (LinearLayoutManager)mConversationList.getLayoutManager()));
        mDataAdapter.update(smsRealmObjects);
        Log.d("Gallery NoData", "List displayed");
        final android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getTitle());
    }

    @Override
    protected String getTitle() {
        return "Nodata";
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