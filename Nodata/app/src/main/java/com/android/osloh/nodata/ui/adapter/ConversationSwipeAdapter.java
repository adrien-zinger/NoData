package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.nodataUtils.Item;
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

    private List<Item> mConversations;
    private Context mContext;

    public ConversationSwipeAdapter(Context context) {
        mContext = context;
    }

    public void update(List<Item> conversations) {
        mConversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int position) {
        return new SwipeConfiguration.Builder(context)
                .setRightBackgroundColor(R.color.error_color)
                .setLeftBackgroundColor(Color.GREEN)
                .setRightUndoable(true)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        return new ConversationHolder(((LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_conversation, null));
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ConversationHolder)holder).configure(mConversations.get(position));
    }

    @Override
    public int getItemCount() {
        return (mConversations == null) ? 0 : mConversations.size();
    }

    @Override
    public void onSwipe(int position, int direction) {
        if (direction == SWIPE_LEFT) {

        } else {

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

        public void configure(Item item) {
            mContent.setText(item.getDate().toString());
            mDate.setText(item.getContent());
        }
    }
}