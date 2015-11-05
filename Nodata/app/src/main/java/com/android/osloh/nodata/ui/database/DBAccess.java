package com.android.osloh.nodata.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Charles on 03/11/2015.
 */
public class DBAccess {
    private static DBAccess ourInstance = new DBAccess();

    private Context context;

    private final SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd/HH/mm",
            Locale.FRANCE);

    public static DBAccess getInstance(Context context) {
        ourInstance.context = context;
        return ourInstance;
    }

    public void update() {
        Realm realm = Realm.getInstance(context);

        update(
                realm,
                realm.where(SMSRealmObject.class).equalTo("issended", false).findAllSorted("date"),
                "inbox", false
        );
        update(
                realm,
                realm.where(SMSRealmObject.class).equalTo("issended", true).findAllSorted("date"),
                "sent", true
        );
    }

    private void update(Realm realm, RealmResults<SMSRealmObject> smsList, String box, boolean sended) {
        String[] reqCols = new String[]{"_id", "address", "read", "date", "body", "read"};
        Cursor smsExtCursor = null;
        SMSRealmObject lastSmsSended = (smsList == null || smsList.isEmpty()) ? null : smsList.first();
        //if (smsList.size() == 0)
            smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/"+box), reqCols, null, null, null);
        /*else //A tester
        {
            smsExtCursor = getNewSMS(SMSRealmObject lastSmsSended, Cursor smsExtCursor, String box, String[] reqCols);
            pushSMS(smsExtCursor, realm, sended);
        }*/
        if (smsExtCursor != null) {
            if (lastSmsSended == null) {
                for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext(); )
                    pushSMS(smsExtCursor, realm, sended);
            } else {
                for (smsExtCursor.moveToFirst(); lastSmsSended.equals(smsExtCursor) || smsExtCursor.moveToNext(); ) {
                    pushSMS(smsExtCursor, realm, sended);
                }
            }
            smsExtCursor.close();
        }
    }
    public Cursor getNewSMS(SMSRealmObject lastSmsSended, Cursor smsExtCursor, String box, String[] reqCols){
        if (lastSmsSended != null) {
            for (int i = 1; i < 10000; i++) {//trop fonsdé pour taffer
                smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/" + box), reqCols, null, null, "LIMIT " + i);
                for (smsExtCursor.moveToFirst(); lastSmsSended.equals(smsExtCursor) || smsExtCursor.moveToNext(); ) {
                    return smsExtCursor;
                }
            }
        }
        return null;
    }

    public List<SMSRealmObject> getFirstOfConvers(){
        List<SMSRealmObject> r = new ArrayList<>();
        RealmResults<SMSRealmObject> buff = Realm.getInstance(context).where(SMSRealmObject.class).findAll();
        for (SMSRealmObject smsLocalData : buff) {
            if(r.size() == 0)
                r.add(smsLocalData);
            else {
                Boolean exist = false;
                for (SMSRealmObject smsUniqueData : r) {
                    if (smsUniqueData.getFrom().endsWith(smsLocalData.getFrom()))
                        exist =true;break;
                }
                if (!exist)
                    r.add(smsLocalData);
            }
        }
        return r;
    }

    /**
     * Return a converstation send and receive merge order by date
     * @param from from
     * @return converstation send and receive merge order by date in RealmResults<SMSRealmObject>
     */
    public RealmResults<SMSRealmObject> getConversation(String from) {
        long day = 1000 * 60 * 60 * 24;
        Date today = new Date();
        return Realm.getInstance(context)
                .where(SMSRealmObject.class)
                .equalTo("from", from)
                .findAllSorted("date");
    }

    /**
     * Push sms in local database
     * @param sms
     * @param real
     */
    private void pushSMS(final Cursor sms, Realm real, final boolean sended){
        if(sms != null) {
            real.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Date d;
                    try {
                        d = mFormatter.parse(mFormatter.format(new Date(Long.parseLong(sms.getString(2)))));
                    } catch (ParseException e) {
                        d = new Date(Long.parseLong(sms.getString(2)));
                    }
                    //SMSRealmObject push = realm.createObject(SMSRealmObject.class);
                    SMSRealmObject push = new SMSRealmObject();
                    push.setId(sms.getString(0));
                    push.setFrom(sms.getString(1));
                    push.setDate(d);
                    push.setRead((sms.getString(3).equals("")));
                    push.setBody(sms.getString(4));
                    push.setDraft(false);
                    push.setIssended(sended);
                    push.setReported(null);
                    realm.copyToRealmOrUpdate(push);
                }
            });
        }
    }
}
