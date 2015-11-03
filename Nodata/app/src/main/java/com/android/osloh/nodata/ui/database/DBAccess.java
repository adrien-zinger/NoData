package com.android.osloh.nodata.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String[] reqCols = new String[]{"_id", "address", "read", "date", "body", "read"};
        Realm realm = Realm.getInstance(context);
        update(
                realm,
                realm.where(SMSRealmObject.class).equalTo("sended", "false").findAllSorted("date"),
                context.getContentResolver().query(Uri.parse("content://sms/inbox"), reqCols, null, null, null),
                false
        );
        update(
                realm,
                realm.where(SMSRealmObject.class).equalTo("sended", "true").findAllSorted("date"),
                context.getContentResolver().query(Uri.parse("content://sms/send"), reqCols, null, null, null),
                true
        );
    }

    private void update(Realm realm, RealmResults<SMSRealmObject> smsList, Cursor smsExtCursor, boolean sended) {
        SMSRealmObject lastSmsSended = (smsList == null || smsList.isEmpty()) ? null : smsList.first();
        if (lastSmsSended == null) {
            for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext();) pushSMS(smsExtCursor, realm, sended);
        } else {
            for (smsExtCursor.moveToFirst();lastSmsSended.equals(smsExtCursor) || smsExtCursor.moveToNext();) {
                pushSMS(smsExtCursor, realm, sended);
            }
        }
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
                        d = new Date();
                    }
                    SMSRealmObject push = realm.createObject(SMSRealmObject.class);
                    push.setId(sms.getString(0));
                    push.setFrom(sms.getString(1));
                    push.setDate(d);
                    push.setBody(sms.getString(3));
                    push.setDraft(false);
                    push.setRead((sms.getString(4).equals("")));
                    push.setIssended(sended);
                    push.setReported(null);
                }
            });
        }
    }
}
