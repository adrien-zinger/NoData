package com.android.osloh.nodata.ui.constant;

import android.os.Bundle;

import com.android.osloh.nodata.ui.viewNoData.fragment.ConversationFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.GalleryFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;

/**
 * Created by adrienzinger on 29/09/15.
 * Contain all fragment ids
 */
public class FragmentConstants {

    public enum Goto {
        CONVERSATION {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return ConversationFragment.newInstance(bundle);
            }
        },
        INBOX {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return GalleryFragment.newInstance(bundle);
            }
        };
        public abstract MainFragment getInstance(Bundle bundle);
    }
}
