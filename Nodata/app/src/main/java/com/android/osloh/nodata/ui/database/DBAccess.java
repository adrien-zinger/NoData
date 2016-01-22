package com.android.osloh.nodata.ui.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.osloh.nodata.ui.nodataUtils.StringCryptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                realm.where(SMSRealmObject.class).equalTo("isSent", false).findAllSorted("date"),
                "inbox", false
        );
        update(
                realm,
                realm.where(SMSRealmObject.class).equalTo("isSent", true).findAllSorted("date"),
                "sent", true
        );
    }

    private void update(Realm realm, RealmResults<SMSRealmObject> smsList,
                        String box, boolean sent) {
        String[] reqCols = new String[]{"_id", "address", "read", "date", "body", "read"};
        Cursor smsExtCursor = null;
        smsList.sort("date", false);
        SMSRealmObject lastSmsSent = (smsList.isEmpty()) ? null : smsList.first();
        String type = (sent)?"sended":"received";
        if (smsList.size() == 0) {//Local database empty, first use
            Log.d("DBAccess", "first use");
            smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/" + box),
                    reqCols, null, null, null);
            if (smsExtCursor != null) {
                if (lastSmsSent == null) {
                    for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext(); ) {
                        pushSMS(smsExtCursor, realm, sent);
                    }
                } else {
                    for (smsExtCursor.moveToFirst();
                         (lastSmsSent.getBody().equals(smsExtCursor.getString(4))
                                 && lastSmsSent.getFrom().equals(smsExtCursor.getString(1)))
                                 || smsExtCursor.moveToNext(); ) {
                        pushSMS(smsExtCursor, realm, sent);
                    }
                }
            }
            smsExtCursor.close();
        } else {
            Log.d("DBAccess", "second use");
            if (lastSmsSent != null) {
                boolean run = true;
                for (int i = 1; run&&i<100; i++) {
                    smsExtCursor = context.getContentResolver().query(Uri.parse("content://sms/" + box)
                            .buildUpon().appendQueryParameter("limit", String.valueOf(i))
                            .build(), reqCols, null, null, null);
                    for (smsExtCursor.moveToFirst(); smsExtCursor.moveToNext(); ) {
                        pushSMS(smsExtCursor, realm, sent);
                        if (lastSmsSent.getBody().equals(smsExtCursor.getString(4))
                                && lastSmsSent.getFrom().equals(smsExtCursor.getString(1))) {
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
     *
     * @return
     */
    public List<SMSRealmObject> getFirstOfConversation() {
        List<SMSRealmObject> r = new ArrayList<>();
        RealmResults<SMSRealmObject> buff = Realm.getInstance(context).where(SMSRealmObject.class).findAllSorted("date", false);
        for (SMSRealmObject smsLocalData : buff) {
            if (r.size() == 0)
                r.add(smsLocalData);
            else {
                Boolean exist = false;
                for (SMSRealmObject smsUniqueData : r) {
                    if (smsUniqueData.getFrom().equals(smsLocalData.getFrom())) {
                        exist = true;
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
     *
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
     *
     * @param sms
     * @param real
     */
    private void pushSMS(final Cursor sms, Realm real, final boolean sent) {
        if (sms != null) {
            real.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Date d;
                    try {
                        d = mFormatter.parse(mFormatter.format(new Date(Long.parseLong(sms.getString(3)))));
                    } catch (ParseException e) {
                        d = new Date(Long.parseLong(sms.getString(3)));
                    }
                    String cont = (sent) ? sms.getString(4) : sms.getString(4);
                    //SMSRealmObject push = realm.createObject(SMSRealmObject.class);
                    SMSRealmObject push = new SMSRealmObject();
                    push.setId(sms.getString(0));
                    String fr = sms.getString(1);
                    if (!sms.getString(1).contains("+33") && sms.getString(1).length() == 10) {
                        fr = "+33" + sms.getString(1).substring(1);
                    }
                    push.setFrom(fr);
                    push.setRead((sms.getString(2).equals("")));
                    push.setDate(d);
                    /*try {
                        String encryptedPassword = StringCryptor.encrypt(new String(PASSWORD), cont);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
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
