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
import com.android.osloh.nodata.ui.cache.MessageAndConversationAccess;
import com.android.osloh.nodata.ui.constant.ExtraConstants;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.utils.ConversationReader;
import com.android.osloh.nodata.ui.utils.IPredicate;
import com.android.osloh.nodata.ui.utils.Predicate;
import com.android.osloh.nodata.ui.viewNoData.utils.DividerItemConversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrien Zinger
 * Fragment for the conversation show only the ones who are not marked as ended
 * and the news.
 */
public class HomeGalleryFragment extends GalleryFragmentParent {

    public static HomeGalleryFragment newInstance(Bundle bundle) {
        return new HomeGalleryFragment();
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
        Collection<ConversationItemBean> conversations = ConversationReader.getInstance()
                .getAllConversation(getActivity().getContentResolver());
        final Collection<Integer> terminateIds = MessageAndConversationAccess.getInstance()
                .getTerminateConversations(mConversationList.getContext());
        conversations = Predicate.filter(conversations, new IPredicate<ConversationItemBean>() {
            @Override
            public boolean apply(ConversationItemBean type) {
                return !terminateIds.contains(type.getId());
            }
        });
        List<ConversationItemBean> conversationItemBeanList = new ArrayList<>(conversations);
        mDataAdapter.update(conversationItemBeanList);
        Log.d("Gallery NoDa", "List displayed");
    }
}
