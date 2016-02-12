package com.android.osloh.nodata.ui.cache;

import android.content.Context;

import com.android.osloh.nodata.ui.bean.ConversationItemBean;
import com.android.osloh.nodata.ui.bean.MessageItemBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ADRIEN on 11/02/2016.
 */
public class MessageAndConversationAccess {
    private static final MessageAndConversationAccess mInstance = new MessageAndConversationAccess();

    public static MessageAndConversationAccess getInstance() {
        return mInstance;
    }

    /**
     * Push sms in local database
     *
     * @param bean
     * @param realm
     */
    public void terminateConversation(final ConversationItemBean bean, Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                bean.setTerminate(true);
                realm.copyToRealmOrUpdate(bean);
            }
        });
    }

    public List<Integer> getTerminateConversations(Context context) {
        RealmResults<ConversationItemBean> beans = Realm.getInstance(context).where(ConversationItemBean.class)
                .equalTo("terminate", true).findAll();
        List<Integer> r = new ArrayList<>();
        for (ConversationItemBean bean : beans) {
            r.add(bean.getId());
        }
        return r;
    }
}
