package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.cache.SMSRealmObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrien on 10/10/2015.
 * <p/>
 * List of messages in the conversation fragment
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private List<MessageItemBean> mMessages;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    public MessageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = (viewType == TYPE_MESSAGE_RECEIVED) ? R.layout.row_conversation_received : R.layout.row_conversation_sent;
        return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.configure(position);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public static interface OnItemClickListener {
        void onClick(SMSRealmObject conversation);
    }

    public void update(List<MessageItemBean> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (mMessages.get(position).isSent()) ? TYPE_MESSAGE_SENT : TYPE_MESSAGE_RECEIVED;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row_conversation_date)
        TextView date;
        @Bind(R.id.row_conversation_content)
        TextView content;

        private int mPosition;

        public MessageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void configure(int position) {
            mPosition = position;
            MessageItemBean conversation = mMessages.get(mPosition);
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
        }
    }
}