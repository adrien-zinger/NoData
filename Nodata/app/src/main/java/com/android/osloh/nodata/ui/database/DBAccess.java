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

    private void update(Realm realm, RealmResults<SMSRealmObject> smsList,
                        String box, boolean sended) {
        String[] reqCols = new String[]{"_id", "address", "read", "date", "body", "read"};
        Cursor smsExtCursor = null;
        smsList.sort("date", false);
        SMSRealmObject lastSmsSended =
                (smsList == null || smsList.isEmpty()) ? null : smsList.first();
        if (smsList.size() == 0)//Local database empty, first use
        {
            smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/"+box),
                    reqCols, null, null, null);
            if (smsExtCursor != null) {
                if (lastSmsSended == null) {
                    for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext(); )
                        pushSMS(smsExtCursor, realm, sended);
                } else {
                    for (smsExtCursor.moveToFirst();
                         lastSmsSended.equals(smsExtCursor) || smsExtCursor.moveToNext(); ) {
                        pushSMS(smsExtCursor, realm, sended);
                    }
                }
            }
            smsExtCursor.close();
        }
        else
        {
            if (lastSmsSended != null) {
                boolean run = true;
                for (int i = 1 ; !run ; i++) {
                    smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/" + box)
                            .buildUpon().encodedQuery("limit=2," + i).build(), reqCols, null, null, null);
                    for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext(); ) {
                        if (lastSmsSended.equals(smsExtCursor))
                        {
                            pushSMS(smsExtCursor, realm, sended);
                            run = false;
                            break;
                        }
                    }
                    smsExtCursor.close();
                }
            }

        }
    }

    /**
     * Get the last message of each contact in database.
     * @return
     */
    public List<SMSRealmObject> getFirstOfConversation(){
        List<SMSRealmObject> r = new ArrayList<>();
        RealmResults<SMSRealmObject> buff = Realm.getInstance(context).where(SMSRealmObject.class).findAllSorted("date", false);
        for (SMSRealmObject smsLocalData : buff) {
            if(r.size() == 0)
                r.add(smsLocalData);
            else {
                Boolean exist = false;
                for (SMSRealmObject smsUniqueData : r) {
                    if (smsUniqueData.getFrom().equals(smsLocalData.getFrom()))
                    {
                        exist =true;
                        break;
                    }
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
                .findAllSorted("date", false);
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
                        d = mFormatter.parse(mFormatter.format(new Date(Long.parseLong(sms.getString(3)))));
                    } catch (ParseException e) {
                        d = new Date(Long.parseLong(sms.getString(3)));
                    }
                    String fr = sms.getString(1);
                    if (!sms.getString(1).contains("+33") && sms.getString(1).length() == 10)
                        fr = "+33" + sms.getString(1).substring(1);
                    String cont =(sended) ? "You : " + sms.getString(4):sms.getString(4);
                    //SMSRealmObject push = realm.createObject(SMSRealmObject.class);
                    SMSRealmObject push = new SMSRealmObject();
                    push.setId(sms.getString(0));
                    push.setFrom(fr);
                    push.setRead((sms.getString(2).equals("")));
                    push.setDate(d);
                    push.setBody(cont);
                    push.setDraft(false);
                    push.setIssended(sended);
                    push.setReported(null);
                    realm.copyToRealmOrUpdate(push);
                }
            });
        }
    }
}
