package com.android.osloh.nodata.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.osloh.nodata.R;

import butterknife.ButterKnife;

/**
 * Created by adrien zinger on 29/09/15.
 * List of conversation
 */
public class GalleryConversationFragment extends MainFragment{

    public static GalleryConversationFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryConversationFragment();
    }

    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_v3, container, false); // todo
        ButterKnife.bind(this, view);
        return view;

        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }
    //*/
}
