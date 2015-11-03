package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.database.SMSRealmObject;
import com.android.osloh.nodata.ui.viewNoData.dialog.ReportConversationDialog;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrien on 10/10/2015.
 *
 * List of conversations
 */
public class ConversationSwipeAdapter extends SwipeAdapter {

    private List<SMSRealmObject> mMessages;
    private Context mContext;

    public ConversationSwipeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<SMSRealmObject> messages) {
        mMessages = messages;
        notifyDataSetChanged();
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int position) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.color_delete)
                .setRightBackgroundColorResource(R.color.color_mark)
                .setLeftUndoDescription(R.string.action_deleted)
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_message, viewGroup, true);
        return new ConversationHolder(view);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConversationHolder)holder).configure(mMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return (mMessages == null) ? 0 : mMessages.size();
    }

    @Override
    public void onSwipe(final int position, int direction) {
        if (direction == SWIPE_LEFT) {
            final SMSRealmObject messageItemBean = mMessages.remove(position);
            notifyItemRemoved(position);
            Snackbar snackbar = Snackbar.with(mContext)
                    .text("Message deleted")
                    .actionLabel("undo")
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            mMessages.add(position, messageItemBean);
                            notifyItemInserted(position);
                        }
                    });
            SnackbarManager.show(snackbar);
        } else {
            ReportConversationDialog.newInstance(this, mMessages, position)
                    .show(((MainActivity) mContext).getFragmentManager(), "aaaaa");
        }
    }

    class ConversationHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.contenttext)
        TextView mContent;
        @Bind(R.id.datetext)
        TextView mDate;

        public ConversationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void configure(SMSRealmObject messageItemBean) {
            mContent.setText(messageItemBean.getDate().toString());
            mDate.setText(messageItemBean.getBody());
        }
    }
}