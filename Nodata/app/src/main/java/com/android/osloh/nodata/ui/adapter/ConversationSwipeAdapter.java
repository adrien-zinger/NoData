package com.android.osloh.nodata.ui.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.bean.ConversationItemBean;
import com.android.osloh.nodata.ui.bean.MessageItemBean;
import com.android.osloh.nodata.ui.utils.ContactReader;
import com.android.osloh.nodata.ui.utils.DateUtils;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm", Locale.getDefault());
    private Context mContext;

    private List<ConversationItemBean> mConversations;

    public interface OnItemClickListener {
        void onClick(ConversationItemBean conversation);
    }

    private OnItemClickListener mOnItemClickListener;

    public ConversationSwipeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<ConversationItemBean> conversations) {
        Collections.sort(conversations, new Comparator<ConversationItemBean>() {
            @Override
            public int compare(ConversationItemBean lhs, ConversationItemBean rhs) {
                // todo fix when mms
                if (lhs.getLastMessagesItemBean().isEmpty() || rhs.getLastMessagesItemBean().isEmpty()) {
                    return 0;
                }
                Date lhsDate = lhs.getLastMessagesItemBean().get(0).getDate();
                Date rhsDate = rhs.getLastMessagesItemBean().get(0).getDate();
                return rhsDate.compareTo(lhsDate);
            }
        });

        // Insert the separators
        conversations.add(0, new ConversationItemBean("today"));
        boolean yesterday = false;
        boolean older = false;
        int pYesterday = 0;
        int pOlder = 0;
        for (int i = 1; i < conversations.size(); ++i) {
            // todo fix for mms
            if (conversations.get(i).getLastMessagesItemBean().isEmpty()) continue;
            Date d = conversations.get(i).getLastMessagesItemBean().get(0).getDate();
            if (!yesterday && !DateUtils.isToday(d)) {
                pYesterday = i;
                yesterday = true;
            }
            if (yesterday && !older && !DateUtils.isYesterday(d)) {
                pOlder = i + 1;
                older = true;
            }
        }
        conversations.add(pYesterday, new ConversationItemBean("yesterday"));
        conversations.add(pOlder, new ConversationItemBean("older"));

        mConversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int position) {
        return new ConversationHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_gallery, viewGroup, true));
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConversationHolder) holder).configure(position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int position) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.color_delete)
                .setRightBackgroundColorResource(R.color.color_mark)
                .setLeftUndoDescription(R.string.action_deleted)
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NO_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(final int position, int direction) {
        if (direction == SWIPE_LEFT) {
            /*final ConversationItemBean messageItemBean = mConversation.remove(position);
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
            SnackbarManager.show(snackbar);*/
        } else {
            /*ReportConversationDialog.newInstance(this, mConversation, position)
                    .show(((MainActivity) mContext).getFragmentManager(), "aaaaa");*/
        }
    }

    @Override
    public int getItemCount() {
        return (mConversations == null) ? 0 : mConversations.size();
    }


    private static final Rect bounds = new Rect();
    private static final Paint paint = new Paint();

    int width = (int) Math.ceil( bounds.width());
    public class ConversationHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.row_gallery_date_separator)
        TextView dateSeparator;
        @Bind(R.id.row_gallery_date)
        TextView date;
        @Bind(R.id.row_gallery_content)
        TextView content;
        @Bind(R.id.row_gallery_contact)
        TextView contact;
        @Bind(R.id.row_contact_name)
        TextView contactName;
        @Bind(R.id.row_gallery_message_container)
        LinearLayout rowMessageContainer;
        @Bind(R.id.row_gallery_container)
        RelativeLayout rowContainer;

        private int mPosition;

        public ConversationHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void configure(int position) {
            mPosition = position;
            ConversationItemBean bean = mConversations.get(mPosition);
            if (bean.getLastMessagesItemBean() != null) {
                configureMessageRow(bean);
            } else if (bean.getDateSeparator() != null) {
                configureDateSeparator(bean);
            }
        }

        private void configureMessageRow(ConversationItemBean bean) {
            rowMessageContainer.setVisibility(View.VISIBLE);
            dateSeparator.setVisibility(View.GONE);
            if (!bean.getLastMessagesItemBean().isEmpty()) {
                MessageItemBean msg = bean.getLastMessagesItemBean().get(0);
                date.setText(sdf.format(msg.getDate()));
                content.setText(msg.getBody());
                String name = ContactReader.getInstance().getContactName(mContext, msg.getAddress());
                if (name == null || name.isEmpty()) {
                    name = msg.getAddress();
                }
                contactName.setText(name);
                rowContainer.setBackgroundResource(0);

                // Init height
                int minimumHeight = (int) rowContainer.getContext().getResources()
                        .getDimension(R.dimen.row_conversation_minimum_height);

                // todo test size with the size of the screen
                //paint.setTextSize(msg.getBody().get);
                //paint.getTextBounds(testString, 0, testString.length(), bounds);

                if (msg.getBody().length() < 80) {
                    rowContainer.getLayoutParams().height = minimumHeight;
                } else {
                    rowContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
            }
        }

        private void configureDateSeparator(ConversationItemBean bean) {
            rowMessageContainer.setVisibility(View.GONE);
            dateSeparator.setVisibility(View.VISIBLE);
            // todo the test could be greater
            if ("today".equals(bean.getDateSeparator())) {
                dateSeparator.setText(contactName.getResources().getString(R.string.today));
            } else if ("yesterday".equals(bean.getDateSeparator())) {
                dateSeparator.setText(contactName.getResources().getString(R.string.yesterday));
            } else {
                dateSeparator.setText(contactName.getResources().getString(R.string.older));
            }
            rowContainer.setBackgroundResource(R.drawable.gradient_shape);
            rowContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }

        @OnClick(R.id.row_gallery_container)
        public void onClick() {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(mConversations.get(mPosition));
            }
        }
    }
}