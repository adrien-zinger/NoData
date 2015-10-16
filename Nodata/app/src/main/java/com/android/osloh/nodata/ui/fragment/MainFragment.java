package com.android.osloh.nodata.ui.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

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
            final android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null)
                actionBar.setTitle(getTitle());
        }
//        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
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
