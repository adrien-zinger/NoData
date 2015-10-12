package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.SmsBunny;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by adrienzinger on 29/09/15.
 * Fragment for the conversation
 */
public class ConversationFragment extends MainFragment {

    @Bind(R.id.date_name)
    public TextView mDate;
    @Bind(R.id.content_sms)
    public TextView mContent;
    @Bind(R.id.send_content)
    public EditText mSendContent;

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
        mContent.setText(getArguments().getString("content"));
        mDate.setText(getArguments().getString("date"));
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
}
