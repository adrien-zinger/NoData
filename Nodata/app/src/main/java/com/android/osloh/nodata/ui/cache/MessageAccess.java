package com.android.osloh.nodata.ui.cache;

import com.android.osloh.nodata.ui.bean.ConversationItemBean;
import com.android.osloh.nodata.ui.bean.MessageItemBean;

import io.realm.Realm;

/**
 * Created by ADRIEN on 11/02/2016.
 */
public class MessageAccess {
    private static final MessageAccess mInstance = new MessageAccess();

    public static MessageAccess getInstance() {
        return mInstance;
    }

    /**
     * Push sms in local database
     *
     * @param bean
     * @param real
     */
    private void terminateConversation(final ConversationItemBean bean, Realm real, final boolean sent) {
        real.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        });
    }
}
