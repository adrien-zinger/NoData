package com.android.osloh.nodata.ui.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.osloh.nodata.ui.bean.MessageItemBean;

import java.util.ArrayList;
import java.util.Calendar;
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

    public List<MessageItemBean> getAllSms(ContentResolver contentResolver) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"address", "body", "read", "date", "type", "thread_id"},
                null,
                null,
                null,
                null
        ));
    }

    public MessageItemBean getMessageById(ContentResolver contentResolver, int id) {
        List<MessageItemBean> list = manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type", "thread_id"},
                "_id = " + id,
                null,
                null
        ));
        return (list == null) ? null : list.get(0);
    }

    public List<MessageItemBean> getMessagesUnRedById(ContentResolver contentResolver, int id) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type", "thread_id"},
                "address = (select address from sms where _id = " + id + ") and read <> 1 or _id = " + id,
                null,
                null
        ));
    }

    public List<MessageItemBean> getMessagesByAddress(ContentResolver contentResolver, String address) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type", "thread_id"},
                "address = " + address,
                null,
                null
        ));
    }

    public List<MessageItemBean> getMessagesByThreadId(ContentResolver contentResolver, String threadId) {
        Calendar dateLimit = Calendar.getInstance();
        dateLimit.add(Calendar.DAY_OF_MONTH, -7);
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"_id, address", "body", "read", "date", "type", "thread_id"},
                "thread_id = " + threadId + " and date > " + dateLimit.getTime().getTime(),
                null,
                null
        ));
    }

    public List<MessageItemBean> getLastWeak(ContentResolver contentResolver) {
        String whereClause = "date IN (SELECT MAX(date) date FROM sms GROUP BY address)";
        return manageCursor(contentResolver.query(
                Uri.parse("content://sms"),
                new String[]{"address", "body", "read", "date", "type", "thread_id"},
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
