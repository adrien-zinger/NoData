package com.android.osloh.nodata.ui.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.osloh.nodata.ui.bean.ConversationItemBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
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

    public List<ConversationItemBean> getAllConversation(ContentResolver contentResolver) {
        return manageCursor(contentResolver.query(
                Uri.parse("content://mms-sms/conversations/"),
                new String[]{"_id", "body ", "ct_t", "thread_id"},
                null,
                null,
                null
        ), contentResolver);
    }

    public List<ConversationItemBean> getConversationUnread(ContentResolver contentResolver) {
        List<ConversationItemBean>  conversations = getAllConversation(contentResolver);
        List<ConversationItemBean>  r = new ArrayList<>();
        for (ConversationItemBean conversation : conversations) {
            if (!conversation.getLastMessagesItemBean().isEmpty()
                    && !conversation.getLastMessagesItemBean().get(0).getReadState().equals("1")) {
                r.add(conversation);
            }
        }
        return r;
    }

    public List<ConversationItemBean> getConversationLastWeak(ContentResolver contentResolver) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);

        return manageCursor(contentResolver.query(
                Uri.parse("content://mms-sms/conversations/"),
                new String[]{"*"},
                "date >= " + cal.getTime().getTime(),
                null,
                null
        ), contentResolver);
    }

    private List<ConversationItemBean> manageCursor(Cursor smss, ContentResolver contentResolver) {
        List<ConversationItemBean> r = new ArrayList<>();
        if (smss != null) {
            int totalSMS = smss.getCount();
            if (smss.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {
                    ConversationItemBean bean = new ConversationItemBean();
                    String type = smss.getString(smss.getColumnIndex("ct_t"));
                    //if ("application/vnd.wap.multipart.related".equals(type)) {
                        // todo it's MMS
                    //} else {
                        bean.setLastMessagesItemBean(
                                SmsReader.getInstance().getMessagesUnRedById(
                                        contentResolver, smss.getInt(smss.getColumnIndexOrThrow("_id"))));
                    //}
                    bean.setThreadId(smss.getString(smss.getColumnIndex("thread_id")));
                    r.add(bean);
                    smss.moveToNext();
                }
            }
            smss.close();
        }
        return r;
    }
}
