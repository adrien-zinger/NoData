package com.android.osloh.nodata.ui.bean;

import java.util.List;

/**
 *
 * Created by Charles on 08/02/2016.
 */
public class ConversationItemBean {

    private List<MessageItemBean> lastMessagesItemBean;
    private String dateSeparator;
    private String threadId;

    public ConversationItemBean(){
    }

    public ConversationItemBean(List<MessageItemBean> lastMessagesItemBean) {
        this.lastMessagesItemBean = lastMessagesItemBean;
    }

    public ConversationItemBean(String dateSeparator){
        this.dateSeparator = dateSeparator;
    }

    public List<MessageItemBean> getLastMessagesItemBean() {
        return lastMessagesItemBean;
    }

    public void setLastMessagesItemBean(List<MessageItemBean> lastMessagesItemBean) {
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
}
