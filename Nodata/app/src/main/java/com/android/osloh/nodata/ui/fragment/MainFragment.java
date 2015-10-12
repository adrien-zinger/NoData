package com.android.osloh.nodata.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.android.osloh.nodata.ui.activity.MainActivity;
import com.android.osloh.nodata.ui.constant.FragmentConstants;

/**
 */
public class MainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // do the thing
        }
//        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getTitle());
    }

    public void loadFragment(FragmentConstants.Goto fragment) {
        ((MainActivity) getActivity())
                .loadFragment(fragment, new Bundle());
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        ((MainActivity) getActivity())
                .loadFragment(fragment, bundle);
    }

    protected String getTitle() {
        return "Nodata";
    }
}
