package com.android.osloh.nodata.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.Item;
import java.util.ArrayList;

/**
 * Created by Charles on 10/10/2015.
 */
public class ConversArrayAdapter  extends ArrayAdapter<Item> {
    private ArrayList<Item> objects;
    public ConversArrayAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_convers, null);
        }
        Item i = objects.get(position);
        if (i != null) {
            TextView content = (TextView) v.findViewById(R.id.contenttext);
            TextView date = (TextView) v.findViewById(R.id.datetext);

            if (content != null){
                content.setText(i.getDate());
            }
            if (date != null){
                date.setText(i.getContent());
            }
        }
        return v;
    }

}