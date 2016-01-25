package com.android.osloh.nodata.ui.database;

import com.android.osloh.nodata.ui.nodataUtils.StringCryptor;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Charles on 02/11/2015.
 */
public class SMSRealmObject extends RealmObject {

    @PrimaryKey
    private String id;
    private String from;
    private String body;
    private Date date;
    private boolean isSent;
    private Date reported;
    private boolean read;
    private boolean draft;

    @Ignore
    private int sessionId;
    //private Object o;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        this.isSent = sent;
    }

    public Date getReported() {
        return reported;
    }

    public void setReported(Date reported) {
        this.reported = reported;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /*
    @Override
    public boolean equals(Object o) {
        this.o = o;
        if (o instanceof SMSRealmObject) {
            SMSRealmObject s = (SMSRealmObject) o;
            return id.equals(s.id) && from.equals(s.from) && body.equals(s.body) && date.equals(s.date);
        }
        return false;
    }*/
}