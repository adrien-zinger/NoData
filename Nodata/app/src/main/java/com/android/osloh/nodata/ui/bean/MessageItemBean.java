package com.android.osloh.nodata.ui.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classic message
 *
 * Created by Charles on 10/10/2015.
 */
public class MessageItemBean {

    public MessageItemBean(){
    }

    private boolean sanded;
    private String date;
    private String address;
    private String body;
    private String readState;

    public Date getDate() {
        long date = Long.parseLong(this.date);
        return new Date(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String name) {
        this.address = name;
    }

    public void setSanded(boolean sanded) {
        this.sanded = sanded;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }
}