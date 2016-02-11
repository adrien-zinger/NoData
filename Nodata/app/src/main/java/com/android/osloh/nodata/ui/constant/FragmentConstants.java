package com.android.osloh.nodata.ui.constant;

import android.os.Bundle;

import com.android.osloh.nodata.ui.viewNoData.fragment.ConversationFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.FullGalleryFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.HomeGalleryFragment;
import com.android.osloh.nodata.ui.viewNoData.fragment.MainFragment;

/**
 * Created by adrienzinger on 29/09/15.
 * Contain all fragment ids
 */
public class FragmentConstants {

    public final static String HOME_GALLERY_ID = "homeId";
    public final static String CONVERSATION_ID = "conversationId";
    public final static String FULL_GALLERY_ID = "galleryId";

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
        FULL_GALLERY {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return FullGalleryFragment.newInstance(bundle);
            }

            @Override
            public String getId() {
                return FULL_GALLERY_ID;
            }
        },
        HOME_GALLERY {
            @Override
            public MainFragment getInstance(Bundle bundle) {
                return HomeGalleryFragment.newInstance(bundle);
            }

            @Override
            public String getId() {
                return HOME_GALLERY_ID;
            }
        };

        public abstract MainFragment getInstance(Bundle bundle);
        public abstract String getId();
    }
}
