package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.nodataUtils.Filter;
import com.android.osloh.nodata.ui.nodataUtils.Item;
import com.android.osloh.nodata.ui.nodataUtils.SmsBunny;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

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

    private String from;
    private Stack<Item> received = new Stack<>();
    private Stack<Item> sended = new Stack<>();

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

        Filter.Predicate<Item> validPersonPredicate = new Filter.Predicate<Item>() {
            public boolean apply(Item item) {
                return item.getAddress().endsWith(from);
            }
        };
        received.addAll(Filter.filter(((MainActivity) getActivity()).getConversContent("inbox"), validPersonPredicate));
        sended.addAll(Filter.filter(((MainActivity) getActivity()).getConversContent("sent"), validPersonPredicate));

        mConversationSwipeAdapter = new ConversationSwipeAdapter(getActivity());
        mListOfConvers.setAdapter(mConversationSwipeAdapter);
        return view;
    }

    private List<Item> getAPeaceOfConversation() {
        List<Item> r = new ArrayList<>();
        for (int i = 0; i <= 30; ++i) {
            if (received.size() == 0 && sended.size() == 0) {
                return r;
            } else if (received.size() == 0) {
                r.add(sended.pop());
            } else if (sended.size() == 0) {
                r.add(received.pop());
            } else if (received.peek().getDate().before(sended.peek().getDate())) {
                r.add(received.pop());
            } else {
                r.add(sended.pop());
            }
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
        SmsBunny.getBunny().sendSmsForGroup(getActivity(), from, mSendContent.getText().toString());
    }

    @Override
    protected String getTitle() {
        return from;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mConversationSwipeAdapter.getItemCount() == 0) {
            mConversationSwipeAdapter.update(getAPeaceOfConversation());
        }
    }
}
