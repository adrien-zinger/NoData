package com.android.osloh.nodata.ui.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.osloh.nodata.ui.bean.ConversationItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 08/02/2016.
 */
public class ConversationReader {
    private static ConversationReader cConversationReader;

    public static ConversationReader getInstance() {
        if (cConversationReader == null) {
            cConversationReader = new ConversationReader();
        }
        return cConversationReader;
    }

    public List<ConversationItemBean> getAllSms(ContentResolver contentResolver) {
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
                Uri.parse("content://mms-sms/conversations/"),
                new String[]{"_id", "body ", "ct_t"},
                null,
                null,
                null
        ));
    }

    public List<ConversationItemBean> getLastWeak(ContentResolver contentResolver) {
        String whereClause = "date IN (SELECT MAX(date) date FROM sms GROUP BY address)";
        return manageCursor(contentResolver.query(
                Uri.parse("content://mms-sms/conversations/"),
                new String[]{"*"},
                null,
                null,
                null
        ));
    }

    private List<ConversationItemBean> manageCursor(Cursor sms) {
        List<ConversationItemBean> r = new ArrayList<>();
        if (sms != null) {
            int totalSMS = sms.getCount();
            if (sms.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {
                    ConversationItemBean conversationItemBean = new ConversationItemBean();
                    conversationItemBean.setId(sms.getInt(sms.getColumnIndexOrThrow("_id")));
                    conversationItemBean.setLastContent(sms.getString(sms.getColumnIndexOrThrow("body")));
                    r.add(conversationItemBean);
                    sms.moveToNext();
                }
            }
        }
        return r;
    }
}
