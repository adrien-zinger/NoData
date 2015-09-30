package com.android.osloh.nodata.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.osloh.nodata.R;
import com.android.osloh.nodata.ui.Utils.SmsBunny;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adrienzinger on 29/09/15.
 * Fragment for the conversation
 */
public class ConversationFragment extends MainFragment {

    public static ConversationFragment newInstance(@SuppressWarnings("unused") Bundle bundle) {
        return new ConversationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test2, container, false); // todo
        ButterKnife.bind(this, view);
        return view;
    }



    /**********************************************************************************/
    /**********************************************************************************/
    /* ON CLICK
    /**********************************************************************************/
    /**********************************************************************************/

    @OnClick(R.id.text)
    public void onClickText() {
        SmsBunny.getBunny().sendSmsForGroup(getActivity());
    }
}
