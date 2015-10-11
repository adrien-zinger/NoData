package com.android.osloh.nodata.ui.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.osloh.nodata.R;
import java.util.ArrayList;

/**
 * Created by Charles on 10/10/2015.
 */
public class CustomArrayAdapter  extends ArrayAdapter<Item> {
    private ArrayList<Item> objects;
    public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        Item i = objects.get(position);
        if (i != null) {
            TextView tt = (TextView) v.findViewById(R.id.toptext);
            TextView ttd = (TextView) v.findViewById(R.id.toptextdata);
            TextView mt = (TextView) v.findViewById(R.id.middletext);
            TextView mtd = (TextView) v.findViewById(R.id.middletextdata);
            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
            TextView btd = (TextView) v.findViewById(R.id.desctext);
            if (tt != null){
                tt.setText("Date: ");
            }
            if (ttd != null){
                ttd.setText(i.getDate());
            }
            if (mt != null){
                mt.setText("Adress: ");
            }
            if (mtd != null){
                mtd.setText(i.getAddress());
            }
            if (bt != null){
                bt.setText("Content: ");
            }
            if (btd != null){
                btd.setText(i.getContent());
            }
        }
        return v;
    }
}