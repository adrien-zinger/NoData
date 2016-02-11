package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toolbar;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.adapter.MessageAdapter;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.constant.ExtraConstants;
import com.android.osloh.nodata.ui.utils.ContactReader;
import com.android.osloh.nodata.ui.utils.Filter;
import com.android.osloh.nodata.ui.utils.SmsReader;
import com.android.osloh.nodata.ui.viewNoData.utils.DividerItemConversation;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrien Zinger
 * Fragment for the conversation
 */
public class ConversationFragment extends MainFragment {

    @Bind(R.id.send_content)
    public EditText mSendContent;
    @Bind(R.id.list_of_messages)
    public RecyclerView mMessage;

    private MessageAdapter mMessageAdapter;
    private String from;
    private String threadId;

    public static ConversationFragment newInstance(Bundle bundle) {
        ConversationFragment cf = new ConversationFragment();
        cf.setArguments(bundle);
        cf.from = bundle.getString(ExtraConstants.ADDRESS_ID);
        cf.threadId = bundle.getString(ExtraConstants.THREAD_ID);
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected String getTitle() {
        String name = ContactReader.getInstance().getContactName(getActivity(), from);
        if (name == null || name.isEmpty()) {
            name = from;
        }
        return name;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter(getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(false);
            mMessage.setLayoutManager(linearLayoutManager);
            mMessage.setAdapter(mMessageAdapter);
            mMessage.addItemDecoration(new DividerItemConversation(getActivity(),
                    DividerItemConversation.VERTICAL_LIST));
        }
        mMessageAdapter.update(SmsReader.getInstance()
                .getMessagesByThreadId(getActivity().getContentResolver(), threadId));
    }
}
