package com.android.osloh.nodata.ui.viewNoData.fragment;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
                .addFragment(fragment, new Bundle());
    }

    public void loadFragment(FragmentConstants.Goto fragment, Bundle bundle) {
        ((MainActivity) getActivity())
                .addFragment(fragment, bundle);
    }

    protected String getTitle() {
        return "Noda";
    }

    public String getContactName(String address) {
        ContentResolver cr = getActivity().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(address));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }
}
