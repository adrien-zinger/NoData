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
import com.android.osloh.nodata.ui.bean.ConversationItemBean;
import com.android.osloh.nodata.ui.cache.SMSRealmObject;
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
 * List of conversations
 */
public class ConversationSwipeAdapter extends SwipeAdapter {

    private List<ConversationItemBean> mConversation;
    private Context mContext;

    public interface OnItemClickListener {
        void onClick(ConversationItemBean conversation);
    }
    private OnItemClickListener mOnItemClickListener;

    public ConversationSwipeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<ConversationItemBean> messages) {
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
        return new ConversationHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_gallery, viewGroup, true));
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConversationHolder) holder).configure(position);
    }

    @Override
    public int getItemCount() {
        return (mConversation == null) ? 0 : mConversation.size();
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onSwipe(final int position, int direction) {
        if (direction == SWIPE_LEFT) {
            final ConversationItemBean messageItemBean = mConversation.remove(position);
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

        private int mPosition;

        public ConversationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void configure(int position) {
            mPosition = position;
            ConversationItemBean conversation = mConversation.get(mPosition);
            // Set date todo : create our date format or find good library
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            // todo Set content
            content.setText(conversation.getLastContent());

            // todo Set contact
           // contact.setText(conversation.getAddress());
        }

        @OnClick(R.id.row_gallery_container)
        public void onClick() {
            if (mOnItemClickListener != null) mOnItemClickListener.onClick(mConversation.get(mPosition));
        }
    }

}