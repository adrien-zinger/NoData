package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.osloh.nodata.R;

import butterknife.ButterKnife;

/**
 * Created by adrien zinger on 29/09/15.
 * List of conversation
 */
public class GalleryFragment extends MainFragment{

    public static GalleryFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test, container, false); // todo
        ButterKnife.bind(this, view);
        return view;
    }
    //*/
}
