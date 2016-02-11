package com.android.osloh.nodata.ui.cache;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 *
 * Created by Charles on 03/11/2015.
 */
public class CacheAccess {
    private static CacheAccess ourInstance = new CacheAccess();

    private Context context;

    public static CacheAccess getInstance(Context context) {
        ourInstance.context = context;
        return ourInstance;
    }

}
