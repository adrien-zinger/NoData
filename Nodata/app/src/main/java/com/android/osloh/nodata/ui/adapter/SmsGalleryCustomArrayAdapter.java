package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.bean.MessageItemBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Charles on 10/10/2015.
 */
public class SmsGalleryCustomArrayAdapter extends ArrayAdapter<MessageItemBean> {
    private List<MessageItemBean> mSmsList;

    public SmsGalleryCustomArrayAdapter(Context context, int textViewResourceId, List<MessageItemBean> objects) {
        super(context, textViewResourceId, objects);
        this.mSmsList = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_gallery, null);
            convertView.setTag(new Holder(convertView));
        }
        Holder holder = (Holder) convertView.getTag();
        MessageItemBean i = mSmsList.get(position);
        if (i != null) {
            holder.initHolder(i.getDate().toString(), i.getAddress(), i.getContent());
        }
        return convertView;
    }

    public class Holder {
        @Bind(R.id.toptext) TextView tt;
        @Bind(R.id.toptextdata) TextView ttd;
        @Bind(R.id.middletext) TextView mt;
        @Bind(R.id.middletextdata) TextView mtd;
        @Bind(R.id.bottomtext) TextView bt;
        @Bind(R.id.desctext) TextView btd;

        public Holder(View view) {
            ButterKnife.bind(this, view);
        }

        public void initHolder(String ttd, String mtd, String btd) {
            this.tt.setText("Date: ");
            this.mt.setText("Adress: ");
            this.bt.setText("Content: ");
            this.ttd.setText(ttd);
            this.mtd.setText(mtd);
            this.btd.setText(btd);
        }
    }
}