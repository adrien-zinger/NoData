package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.SmsBunny;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adrienzinger on 29/09/15.
 * Fragment for the conversation
 */
public class ConversationFragment extends MainFragment {
    private String from;
    private View main_view;
    public static ConversationFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        ConversationFragment cf = new ConversationFragment();
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        from = getArguments().getString("from");
        String content = getArguments().getString("content");
        TextView From = (TextView) view.findViewById(R.id.from_name);
        TextView Content = (TextView) view.findViewById(R.id.content_sms);
        EditText ans = (EditText) view.findViewById(R.id.send_content);
        Button send = (Button) view.findViewById(R.id.send_button);
        main_view = view;
        From.setText(from);
        Content.setText(content);
        ans.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((EditText) view).setText("");
                return false;
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsBunny.getBunny().sendSmsForGroup(getActivity(), from, getMessage());
            }
        });
        Button cancel = (Button) view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(FragmentConstants.Goto.INBOX, new Bundle());
            }
        });
        //ButterKnife.bind(this, view); //todo Cela ne fonctionne pas, erreur relou à débugger
        return view;
    }

    public String getMessage() {
        EditText ans = (EditText) main_view.findViewById(R.id.send_content);
        return ans.getText().toString();
    }
    /**********************************************************************************/
    /**********************************************************************************/
    /* ON CLICK
    /**********************************************************************************/
    /**********************************************************************************/

    /*@OnClick(R.id.text)       //boutton utilisés à la place
    public void onClickText() {
        SmsBunny.getBunny().sendSmsForGroup(getActivity());
    }*/
}
