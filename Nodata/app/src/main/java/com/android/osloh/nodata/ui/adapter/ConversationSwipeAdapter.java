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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Adrien on 10/10/2015.
 * <p/>
 * List of conversations
 */
public class ConversationSwipeAdapter extends SwipeAdapter {

    private List<SMSRealmObject> mConversation;
    private Context mContext;

    public ConversationSwipeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<SMSRealmObject> messages) {
        mConversation = messages;
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
                .inflate(R.layout.row_gallery, viewGroup, true);
        return new ConversationHolder(view);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConversationHolder) holder).configure(mConversation.get(position));
    }

    @Override
    public int getItemCount() {
        return (mConversation == null) ? 0 : mConversation.size();
    }

    @Override
    public void onSwipe(final int position, int direction) {
        if (direction == SWIPE_LEFT) {
            final SMSRealmObject messageItemBean = mConversation.remove(position);
            notifyItemRemoved(position);
            Snackbar snackbar = Snackbar.with(mContext)
                    .text("Message deleted")
                    .actionLabel("undo")
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            mConversation.add(position, messageItemBean);
                            notifyItemInserted(position);
                        }
                    });
            SnackbarManager.show(snackbar);
        } else {
            ReportConversationDialog.newInstance(this, mConversation, position)
                    .show(((MainActivity) mContext).getFragmentManager(), "aaaaa");
        }
    }

    public class ConversationHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row_gallery_date)
        TextView date;
        @Bind(R.id.row_gallery_content)
        TextView content;
        @Bind(R.id.row_gallery_contact)
        TextView contact;

        public ConversationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void configure(SMSRealmObject conversation) {
            // Set date todo : create our date format or find good library
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            if (conversation.getDate().before(c.getTime())) {
                c.setTime(conversation.getDate());
                SimpleDateFormat format = new SimpleDateFormat("dd MM");
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
    }

}