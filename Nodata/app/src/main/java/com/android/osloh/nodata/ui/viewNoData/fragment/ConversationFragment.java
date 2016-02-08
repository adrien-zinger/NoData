package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.adapter.MessageAdapter;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.cache.DBAccess;
import com.android.osloh.nodata.ui.cache.SMSRealmObject;
import com.android.osloh.nodata.ui.utils.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

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
    private RealmResults<SMSRealmObject> mConversationFromDB;
    private String from;
    private int offset;

    private final Filter.Predicate<MessageItemBean> validPersonPredicate = new Filter.Predicate<MessageItemBean>() {
        public boolean apply(MessageItemBean item) {
            return item.getAddress().endsWith(from);
        }
    };

    public static ConversationFragment newInstance(Bundle bundle) {
        ConversationFragment cf = new ConversationFragment();
        cf.setArguments(bundle);
        cf.from = bundle.getString("from");
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        ButterKnife.bind(this, view);
        offset = 0;
        return view;
    }

    private List<SMSRealmObject> getAPeaceOfConversation() {
        List<SMSRealmObject> r = new ArrayList<>();
        mConversationFromDB = DBAccess.getInstance(getActivity()).getConversation(from);
        for (int i = 20 * offset; i < 20 * (offset + 1); ++i) {
            if (mConversationFromDB.size() <= i) {
                break;
            }
            r.add(mConversationFromDB.get(i));
        }
        return r;
    }

    @Override
    protected String getTitle() {
        return from;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter(getActivity());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            mMessage.setLayoutManager(linearLayoutManager);
            mMessage.setAdapter(mMessageAdapter);
        }
        mMessageAdapter.update(getAPeaceOfConversation());
    }
}
