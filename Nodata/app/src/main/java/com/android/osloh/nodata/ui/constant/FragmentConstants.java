package com.android.osloh.nodata.ui.constant;

import android.os.Bundle;

import com.android.osloh.nodata.ui.fragment.ConversationFragment;
import com.android.osloh.nodata.ui.fragment.Display_sms;
import com.android.osloh.nodata.ui.fragment.GalleryFragment;
import com.android.osloh.nodata.ui.fragment.MainFragment;

/**
 * Created by adrienzinger on 29/09/15.
 * Contain all fragment ids
 */
public class FragmentConstants {

    public enum Goto {
        GALLERY {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return GalleryFragment.newInstance(bundle);
            }
        },
        CONVERSATION {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                //return ConversationFragment.newInstance(bundle);
                return Display_sms.newInstance(bundle);
            }
        };
        public abstract MainFragment getInstance(Bundle bundle);
    }
}
