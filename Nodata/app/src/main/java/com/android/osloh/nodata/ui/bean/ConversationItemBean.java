package com.android.osloh.nodata.ui.bean;

import java.util.List;

/**
 * Created by Charles on 08/02/2016.
 */
public class ConversationItemBean {
    public ConversationItemBean(){
    }
    private int id;
    private int nbrUnread;
    private List<Integer> messagesId;
    private String lastContent;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getNbrUnread() {
        return nbrUnread;
    }
    public void setNbrUnread(int nbrUnread) {
        this.nbrUnread = nbrUnread;
    }
    public List<Integer> getMessagesId() {
        return messagesId;
    }
    public void setMessagesId(List<Integer> messagesId) {
        this.messagesId = messagesId;
    }
    public String getLastContent() {
        return lastContent;
    }
    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }
}
