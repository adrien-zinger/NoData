package com.android.osloh.nodata.ui.viewNoData.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.adapter.ConversationSwipeAdapter;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.database.SMSRealmObject;
import com.android.osloh.nodata.ui.handler.RecallHandler;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Charles on 20/10/2015.
 */
public class ReportConversationDialog extends MainDialog {

    private ConversationSwipeAdapter mConversationSwipeAdapter;
    private List<SMSRealmObject> mMessages;
    private int mPosition;

    @Bind(R.id.dialog_report_conversation_manage_date_button)
    TextView mManageDateButton;
    @Bind(R.id.dialog_report_conversation_listview)
    ListView mListView;
    @Bind(R.id.dialog_report_conversation_add_date_button)
    TextView mAddDateButton;

    public static ReportConversationDialog newInstance(ConversationSwipeAdapter conversationSwipeAdapter, List<SMSRealmObject> messages, int position) {
        ReportConversationDialog reportConversationDialog = new ReportConversationDialog();
        reportConversationDialog.mConversationSwipeAdapter = conversationSwipeAdapter;
        reportConversationDialog.mMessages = messages;
        reportConversationDialog.mPosition = position;
        return reportConversationDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_report_conversation, container);
        ButterKnife.bind(this, v);
        if (RecallHandler.getInstance().getDefinedDates().isEmpty()) {
            mManageDateButton.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mAddDateButton.setVisibility(View.GONE);
        } else {
            mManageDateButton.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mAddDateButton.setVisibility(View.VISIBLE);
        }
        return v;
    }

    @OnClick(R.id.dialog_report_conversation_option_button)
    public void onCLickOptionButton() {

    }
}
