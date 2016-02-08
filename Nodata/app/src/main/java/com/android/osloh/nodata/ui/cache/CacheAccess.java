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

    /**
     * Return a converstation send and receive merge order by date
     *
     * @param from from
     * @return converstation send and receive merge order by date in RealmResults<SMSRealmObject>
     */
    public RealmResults<SMSRealmObject> getConversationForExample(String from) {
        long day = 1000 * 60 * 60 * 24;
        Date today = new Date();
        return Realm.getInstance(context)
                .where(SMSRealmObject.class)
                .equalTo("from", from)
                .findAllSorted("date", false);
    }

    /**
     * Push sms in local database
     *
     * @param sms
     * @param real
     */
    private void pushSMSForExample(final Cursor sms, Realm real, final boolean sent) {
        if (sms != null) {
            real.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Date d = new Date(Long.parseLong(sms.getString(3)));
                    String cont = (sent) ? sms.getString(4) : sms.getString(4);

                    SMSRealmObject push = new SMSRealmObject();
                    push.setId(sms.getString(0));
                    String fr = sms.getString(1);
                    if (!sms.getString(1).contains("+33") && sms.getString(1).length() == 10) {
                        fr = "+33" + sms.getString(1).substring(1);
                    }
                    push.setFrom(fr);
                    push.setRead((sms.getString(2).equals("")));
                    push.setDate(d);
                    push.setBody(cont);
                    push.setDraft(false);
                    push.setSent(sent);
                    push.setReported(null);
                    realm.copyToRealmOrUpdate(push);
                }
            });
        }
    }
}
