package com.android.osloh.nodata.ui.constant;

import android.os.Bundle;

import com.android.osloh.nodata.ui.viewNoData.fragment.ConversationFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.HomeFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;

/**
 * Created by adrienzinger on 29/09/15.
 * Contain all fragment ids
 */
public class FragmentConstants {

    public final static String HOME_ID = "homeId";
    public final static String CONVERSATION_ID = "conversationId";

    public enum Goto {
        CONVERSATION {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return ConversationFragment.newInstance(bundle);
            }

            @Override
            public String getId() {
                return CONVERSATION_ID;
            }
        },
        HOME {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return HomeFragment.newInstance(bundle);
            }

            @Override
            public String getId() {
                return HOME_ID;
            }
        };

        public abstract MainFragment getInstance(Bundle bundle);
        public abstract String getId();
    }
}
