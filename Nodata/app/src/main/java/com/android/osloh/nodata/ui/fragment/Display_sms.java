package com.android.osloh.nodata.ui.fragment;

/**
 * Created by Charles on 06/10/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.android.osloh.nodata.R;

import butterknife.ButterKnife;

public class Display_sms extends MainFragment {

    OnURLSelectedListener mListener;
    private Button btnSent, btnInbox, btnDraft;
    public static Display_sms newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new Display_sms();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("ListFragment", "onCreate()");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("ListFragment", "onActivityCreated().");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");

        //Generate list View from ArrayList
        displayListeView("inbox");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("ListFragment", "onCreateView()");
        Log.v("ListContainer", container == null ? "true" : "false");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");
        View view = inflater.inflate(R.layout.sms_displayer, container, false);
        ButterKnife.bind(this, view);
        // Init GUI Widget
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

    // Container Activity must implement this interface
    public interface OnURLSelectedListener {
        public void onURLSelected(String URL);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnURLSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnURLSelectedListener");
        }
    }

    private List<String> displayBox(String box) {
        List<String> urlList = new ArrayList<String>();
        String[] reqCols = new String[]{"_id", "address", "body"};
        Cursor cursor = getActivity().getContentResolver().query(Uri.parse("content://sms/"+box), reqCols, null, null, null);
        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);
                }
                urlList.add(msgData);
            } while (cursor.moveToNext());
        }
        return urlList;
    }
        private void displayListeView(String type) {
            ArrayAdapter<String> dataAdapter = null;
            if (type == "inbox")
                 dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_sms, displayBox("inbox"));
            else if(type=="sent")
                dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_sms, displayBox("sent"));
            else if(type == "draft")
                dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.row_sms, displayBox("draft"));
        ListView listView = (ListView) getView().findViewById(R.id.listofSMS);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        //enables filtering for the contents of the given ListView
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Send the URL to the host activity
                mListener.onURLSelected(((TextView) view).getText().toString());
            }
        });
    }
}