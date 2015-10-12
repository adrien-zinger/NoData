package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.Item;
import com.android.osloh.nodata.ui.Utils.SmsBunny;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.ConversArrayAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
        displayListContent(filterConvers(((MainActivity) getActivity()).displayBox("inbox", true),
                ((MainActivity) getActivity()).displayBox("sent", true)));
        return view;
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
    private ArrayList<Item> filterConvers(ArrayList<Item> allIncom, ArrayList<Item> allSend){
        ArrayList<Item> out = new ArrayList<Item>();
        for (Item it:allIncom)
            if(it.getAddress().endsWith(from))
                out.add(it);
        for (Item ic:allSend)
            if(ic.getAddress().endsWith(from))
            {
                String buff = ic.getContent();
                ic.setContent("You : "+buff);
                out.add(ic);
            }
        Collections.sort(out, new Comparator<Item>() {
            public int compare(Item emp1, Item emp2) {
                return emp1.getDate().compareToIgnoreCase(emp2.getDate());
            }
        });
        for (Item ip:out) {
            String date = ((MainActivity) getActivity()).convertDate(ip.getDate());
            ip.setDate(date);
        }
        return out;
    }
    private void displayListContent(ArrayList<Item> list){
        ConversArrayAdapter caa = new ConversArrayAdapter(getActivity(), R.layout.list_convers, list);
        mListOfConvers.setAdapter(caa);
    }
}
