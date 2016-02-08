package com.android.osloh.nodata.ui.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.osloh.nodata.ui.bean.MessageItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Return sms
 * Created by ADRIEN on 07/02/2016.
 */
public class SmsReader {

    private static SmsReader sSmsReader;

    public static SmsReader getInstance() {
        if (sSmsReader == null) {
            sSmsReader = new SmsReader();
        }
        return sSmsReader;
    }

    public List getAllSms(ContentResolver contentResolver) {
        /*
        String[] tableColumns = new String[] {
                "column1",
                "(SELECT max(column1) FROM table2) AS max"
        };
        String whereClause = "column1 = ? OR column1 = ?";
        String[] whereArgs = new String[] {
                "value1",
                "value2"
        };
        String orderBy = "column1";
        Cursor c = sqLiteDatabase.query("table1", tableColumns, whereClause, whereArgs,
                null, null, orderBy);

        // since we have a named column we can do
        int idx = c.getColumnIndex("max");
        */

        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"address", "body", "read", "date", "type"},
                null,
                null,
                null
        ));
    }

    public MessageItemBean getMessageById(ContentResolver contentResolver, int id) {
        List<MessageItemBean> list = manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type"},
                "_id = " + id,
                null,
                null
        ));
        return (list == null) ? null : list.get(0);
    }

    public List<MessageItemBean> getMessagesUnRedById(ContentResolver contentResolver, int id) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type"},
                "address = (select address from sms where _id = " + id + ") and read <> 1",
                null,
                null
        ));
    }

    public List<MessageItemBean> getMessagesByAddress(ContentResolver contentResolver, String address) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type"},
                "address = " + address,
                null,
                null
        ));
    }

    public List<MessageItemBean> getLastWeak(ContentResolver contentResolver) {
        String whereClause = "date IN (SELECT MAX(date) date FROM sms GROUP BY address)";
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"address", "body", "read", "date", "type"},
                whereClause,
                null,
                null
        ));
    }

    private List<MessageItemBean> manageCursor(Cursor sms) {
        List<MessageItemBean> r = new ArrayList<>();
        if (sms != null) {
            int totalSMS = sms.getCount();
            if (sms.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {
                    MessageItemBean messageItemBean = new MessageItemBean();
                    messageItemBean.setAddress(sms.getString(sms.getColumnIndexOrThrow("address")));
                    messageItemBean.setBody(sms.getString(sms.getColumnIndexOrThrow("body")));
                    messageItemBean.setReadState(sms.getString(sms.getColumnIndex("read")));
                    messageItemBean.setDate(sms.getString(sms.getColumnIndexOrThrow("date")));
                    messageItemBean.setSent(!sms.getString(sms.getColumnIndexOrThrow("type")).contains("1"));
                    r.add(messageItemBean);
                    sms.moveToNext();
                }
            }
            sms.close();
        }
        return r;
    }
}
