package com.android.osloh.nodata.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import butterknife.ButterKnife;

public class DisplayFragment extends MainFragment {

    private Button btnSent, btnInbox, btnDraft;
    //private String from, content;

    public static DisplayFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new DisplayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayListeView("inbox");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sms_displayer, container, false);
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
        listView.setAdapter(dataAdapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String text = (String) ((TextView)view).getText();
                String[] buff = buff = text.split(":");
                text = buff[2].toString();
                String msg = buff[3].toString();
                buff = text.split(" ");
                Bundle bundle = new Bundle();
                bundle.putString("from", buff[0].toString());
                bundle.putString("content", msg);
                ((MainActivity) getActivity()).loadFragment(FragmentConstants.Goto.CONVERSATION, bundle);
            }
        });
    }
}