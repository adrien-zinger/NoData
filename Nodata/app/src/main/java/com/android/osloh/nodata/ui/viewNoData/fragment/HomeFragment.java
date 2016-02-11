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
 * Created by Adrien Zinger
 * Fragment for the conversation
 */
public class HomeFragment extends MainFragment implements ConversationSwipeAdapter.OnItemClickListener {

    @Bind(R.id.list_of_conversations)
    RecyclerView mConversationList;
    private ConversationSwipeAdapter mDataAdapter;

    public static HomeFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        return (container == null) ? null : view;
    }

    private void displayListView() {
        Log.d("Gallery NoDa", "Create adapter");
        if (mDataAdapter == null) {
            mDataAdapter = new ConversationSwipeAdapter(getActivity());
            mConversationList.setAdapter(mDataAdapter);
            mConversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mConversationList.addItemDecoration(new DividerItemConversation(getActivity(), DividerItemConversation.VERTICAL_LIST));
        }
        mDataAdapter.setOnItemClickListener(this);
        mDataAdapter.update(ConversationReader.getInstance().getAllConversation(getActivity().getContentResolver()));
        Log.d("Gallery NoDa", "List displayed");
    }

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
