package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.nodataUtils.Filter;
import com.android.osloh.nodata.ui.nodataUtils.Item;
import com.android.osloh.nodata.ui.nodataUtils.SmsBunny;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversArrayAdapter;

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
    public ListView mListOfConvers;

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
        View view = inflater.inflate(R.layout.convers_fragment, container, false);
        ButterKnife.bind(this, view);

        Filter.Predicate<Item> validPersonPredicate = new Filter.Predicate<Item>() {
            public boolean apply(Item item) {
                return item.getAddress().endsWith(from);
            }
        };
        received.addAll(Filter.filter(((MainActivity) getActivity()).getConversContent("inbox"), validPersonPredicate));
        sended.addAll(Filter.filter(((MainActivity) getActivity()).getConversContent("sent"), validPersonPredicate));

        ConversArrayAdapter caa = new ConversArrayAdapter(getActivity(), R.layout.list_convers, getAPeaceOfConversation());
        mListOfConvers.setAdapter(caa);
        return view;
    }

    private List<Item> getAPeaceOfConversation() {
        List<Item> r = new ArrayList<>();
        for (int i = received.size(); i >= received.size()-30; ++i) {
            if (received.isEmpty()) {
                if (!sended.isEmpty())
                    r.add(sended.pop());
                else
                    return r;
            }
            else if(sended.isEmpty()) {
                if (!received.isEmpty())
                    r.add(received.pop());
                else
                    return r;
            }
            else if (received.peek().getDate().before(sended.peek().getDate())) {
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
}
