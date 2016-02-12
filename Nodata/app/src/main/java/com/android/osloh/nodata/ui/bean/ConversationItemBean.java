package com.android.osloh.nodata.ui.bean;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 *
 * Created by Charles on 08/02/2016.
 */
public class ConversationItemBean extends RealmObject {

    @PrimaryKey
    private int id;
    private String dateSeparator;
    private String threadId;
    private boolean terminate;

    private RealmList<MessageItemBean> lastMessagesItemBean;

    public ConversationItemBean(){
        terminate = false;
    }

    public boolean getTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public ConversationItemBean(String dateSeparator){
        this.dateSeparator = dateSeparator;
    }

    public RealmList<MessageItemBean> getLastMessagesItemBean() {
        return lastMessagesItemBean;
    }

    public void setLastMessagesItemBean(RealmList<MessageItemBean> lastMessagesItemBean) {
        this.lastMessagesItemBean = lastMessagesItemBean;
    }

    public String getDateSeparator() {
        return dateSeparator;
    }

    public void setDateSeparator(String dateSeparator) {
        this.dateSeparator = dateSeparator;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getThreadId() {
        return threadId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
