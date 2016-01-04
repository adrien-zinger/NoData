package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.database.SMSRealmObject;
import com.android.osloh.nodata.ui.viewNoData.dialog.ReportConversationDialog;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Adrien on 10/10/2015.
 * <p/>
 * List of messages in the conversation fragment
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<SMSRealmObject> mMessages;
    private Context mContext;

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static interface OnItemClickListener {
        void onClick(SMSRealmObject conversation);
    }
    private OnItemClickListener mOnItemClickListener;

    public MessageAdapter(Context context) {
        mContext = context;
    }

    public void update(List<SMSRealmObject> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (mMessages.get(position).getFrom().equals("from_me")) ? TYPE_MESSAGE_SENT : TYPE_MESSAGE_RECEIVED;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row_gallery_date)
        TextView date;
        @Bind(R.id.row_gallery_content)
        TextView content;
        @Bind(R.id.row_gallery_contact)
        TextView contact;

        private int mPosition;

        public MessageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void configure(int position) {
            mPosition = position;
            SMSRealmObject conversation = mMessages.get(mPosition);
            // Set date todo : create our date format or find good library
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            if (conversation.getDate().before(c.getTime())) {
                c.setTime(conversation.getDate());
                SimpleDateFormat format = new SimpleDateFormat("dd MM", Locale.getDefault());
                date.setText(format.format(conversation.getDate()));
            } else {
                c.setTime(conversation.getDate());
                date.setText(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE));
            }

            // todo Set content
            content.setText(conversation.getBody());

            // todo Set contact
            contact.setText(conversation.getFrom());
        }

        @OnClick(R.id.row_gallery_container)
        public void onClick() {
            if (mOnItemClickListener != null) mOnItemClickListener.onClick(mMessages.get(mPosition));
        }
    }
}