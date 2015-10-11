package com.android.osloh.nodata.ui.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.CustomArrayAdapter;
import com.android.osloh.nodata.ui.Utils.Item;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import butterknife.ButterKnife;

public class GalleryFragment extends MainFragment {

    private Button btnSent, btnInbox, btnDraft;
    //private String from, content;

    public static GalleryFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("NoData"); // set the top title
        displayListeView("inbox");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        ButterKnife.bind(this, view);
        btnInbox = (Button) view.findViewById(R.id.btnInbox);
        btnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayListeView("inbox");
            }
        });

        btnSent = (Button)view.findViewById(R.id.btnSentBox);
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayListeView("sent");
            }
        });
        btnDraft = (Button)view.findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayListeView("draft");
            }
        });
        if (container == null) {
            return null;
        }
        return view;
    }

    public ArrayList<Item> displayBox(String box) {
        ArrayList<Item> m_parts = new ArrayList<Item>();
        String[] reqCols = new String[]{"read", "date","address", "body"};
        Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://sms/"+box), reqCols, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                Item msgData = new Item();
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    if (cursor.getColumnName(idx).endsWith("date"))
                    {
                        long date = Long.parseLong(cursor.getString(idx));
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH/mm", Locale.FRANCE);
                        String dateString = formatter.format(new Date(date));
                        //msgData += " " + cursor.getColumnName(idx) + ":" + dateString;
                        msgData.setDate(dateString);
                    }
                    if (cursor.getColumnName(idx).endsWith("address"))
                    {
                        msgData.setAdress(cursor.getString(idx));
                    }
                    if (cursor.getColumnName(idx).endsWith("body"))
                    {
                        msgData.setContent(cursor.getString(idx));
                    }
                    //else
                        //msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
                m_parts.add(msgData);
            } while (cursor.moveToNext());
        }
        return m_parts;
    }
    private void displayListeView(String type) {
        CustomArrayAdapter dataAdapter = null;
        if (type == "inbox")
            dataAdapter = new CustomArrayAdapter(getActivity(), R.layout.list_item, displayBox("inbox"));
        else if(type=="sent")
            dataAdapter = new CustomArrayAdapter(getActivity(), R.layout.list_item, displayBox("sent"));
        else if(type == "draft")
            dataAdapter = new CustomArrayAdapter(getActivity(), R.layout.list_item, displayBox("draft"));
        ListView listView = (ListView) getView().findViewById(R.id.listofSMS);
        listView.setAdapter(dataAdapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String date = (String) ((TextView) view.findViewById(R.id.toptextdata)).getText();
                String add = (String) ((TextView) view.findViewById(R.id.middletextdata)).getText();
                String cont = (String) ((TextView) view.findViewById(R.id.desctext)).getText();
                Bundle bundle = new Bundle();
                bundle.putString("from", add);
                bundle.putString("content", cont);
                bundle.putString("date", date);
                ((MainActivity) getActivity()).loadFragment(FragmentConstants.Goto.CONVERSATION, bundle);
            }
        });
    }
}