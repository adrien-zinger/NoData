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
public class FullGalleryFragment extends GalleryFragmentParent {

    public static FullGalleryFragment newInstance(Bundle bundle) {
        return new FullGalleryFragment();
    }

    protected void displayListView() {
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
}