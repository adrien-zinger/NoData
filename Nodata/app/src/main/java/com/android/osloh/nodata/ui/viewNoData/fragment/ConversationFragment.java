package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.database.DBAccess;
import com.android.osloh.nodata.ui.database.SMSRealmObject;
import com.android.osloh.nodata.ui.nodataUtils.Filter;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.realm.RealmResults;

/**
 * Created by adrienzinger on 29/09/15.
 * Fragment for the conversation
 */
public class ConversationFragment extends MainFragment {

    @Bind(R.id.send_content)
    public EditText mSendContent;

    @Bind(R.id.listofConvers)
    public RecyclerView mListOfConvers;

    private ConversationSwipeAdapter mConversationSwipeAdapter;

    private RealmResults<SMSRealmObject> mConversationFromDB;
    private String from;

    private int offset;

    public static ConversationFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
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

        Filter.Predicate<MessageItemBean> validPersonPredicate = new Filter.Predicate<MessageItemBean>() {
            public boolean apply(MessageItemBean item) {
                return item.getAddress().endsWith(from);
            }
        };
        mConversationSwipeAdapter = new ConversationSwipeAdapter(getActivity());
        mListOfConvers.setAdapter(mConversationSwipeAdapter);
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

    @OnTouch(R.id.send_content)
    public boolean onTouchSendContent(View view, MotionEvent motionEvent) {
        ((EditText) view).setText("");
        return false;
    }

    @OnClick(R.id.send_button)
    public void onClickSendButton(View view) {
        //SmsBunny.getBunny().sendSmsForGroup(getActivity(), from, mSendContent.getText().toString());
    }

    @Override
    protected String getTitle() {
        return from;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mConversationSwipeAdapter.getItemCount() == 0) {
            mListOfConvers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mConversationSwipeAdapter.update(getAPeaceOfConversation());
        }
    }
}
