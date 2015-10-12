package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.SmsGalleryCustomArrayAdapter;
import com.android.osloh.nodata.ui.constant.FragmentConstants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class GalleryFragment extends MainFragment {

    @Bind(R.id.btnSentBox)
    public Button btnSent;
    @Bind(R.id.btnInbox)
    public Button btnInbox;
    @Bind(R.id.btnDraft)
    public Button btnDraft;

    @Bind(R.id.listofSMS)
    public ListView mListOfSMS;
    //private String from, content;

    public static GalleryFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_fragment, container, false);
        ButterKnife.bind(this, view);
        displayListeView("inbox");
        return (container == null) ? null : view;
    }


    private void displayListeView(String type) {
        SmsGalleryCustomArrayAdapter dataAdapter = null;
        if (type == "inbox")
            dataAdapter = new SmsGalleryCustomArrayAdapter(getActivity(), R.layout.list_item, ((MainActivity) getActivity()).displayBox("inbox"));
        else if(type=="sent")
            dataAdapter = new SmsGalleryCustomArrayAdapter(getActivity(), R.layout.list_item, ((MainActivity) getActivity()).displayBox("sent"));
        else if(type == "draft")
            dataAdapter = new SmsGalleryCustomArrayAdapter(getActivity(), R.layout.list_item, ((MainActivity) getActivity()).displayBox("draft"));

        mListOfSMS.setAdapter(dataAdapter);
        mListOfSMS.setTextFilterEnabled(true);
    }

    /*** Butterknife Listeners
    /***********************************************/
    /***********************************************/

    @OnClick(R.id.btnInbox)
    public void onClickInboxButton(View view) {
        displayListeView("inbox");
    }

    @OnClick(R.id.btnSentBox)
    public void onClickSentBoxButton(View view) {
        displayListeView("sent");
    }

    @OnClick(R.id.btnDraft)
    public void onClickDraftButton(View view) {
        displayListeView("draft");
    }

    @OnItemClick(R.id.listofSMS)
    public void onListOfSMSItemClick(AdapterView<?> parent, View view, int position, long id) {
        String date = (String) ((TextView) view.findViewById(R.id.toptextdata)).getText();
        String add = (String) ((TextView) view.findViewById(R.id.middletextdata)).getText();
        String cont = (String) ((TextView) view.findViewById(R.id.desctext)).getText();
        Bundle bundle = new Bundle();
        bundle.putString("from", add);
        bundle.putString("content", cont);
        bundle.putString("date", date);
        ((MainActivity) getActivity()).loadFragment(FragmentConstants.Goto.CONVERSATION, bundle);
    }
}