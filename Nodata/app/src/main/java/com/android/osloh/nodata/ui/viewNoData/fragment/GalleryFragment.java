package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.adapter.SmsGalleryCustomArrayAdapter;
import com.android.osloh.nodata.ui.constant.FragmentConstants;
import com.android.osloh.nodata.ui.database.DBAccess;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class GalleryFragment extends MainFragment {

    @Bind(R.id.listofSMS)
    public ListView mListOfSMS;
    //private String from, content;

    public static GalleryFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        displayListeView("inbox");
        return (container == null) ? null : view;
    }

    private void displayListeView(String type) {
        SmsGalleryCustomArrayAdapter dataAdapter = null;
        //DBAccess.getInstance(getActivity()). todo get last message of each conversation
        mListOfSMS.setAdapter(dataAdapter);
        mListOfSMS.setTextFilterEnabled(true);
    }

    /*** Butterknife Listeners
    /***********************************************/
    /***********************************************/

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