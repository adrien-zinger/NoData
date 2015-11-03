package com.android.osloh.nodata.ui.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Charles on 10/10/2015.
 */
public class MessageItemBean {
    private final SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd/HH/mm", Locale.FRANCE);

    private String date;
    private String address;
    private String content;

    public MessageItemBean(String i, String d, String p){
        this.address = i;
        this.date = d;
        this.content = p;
    }

    public Date getDate() {
        long date = Long.parseLong(this.date);
        return new Date(date);
    }

    public void setDate(String details) {
        this.date = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String name) {
        this.address = name.replace("+33", "0");;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String price) {
        this.content = price;
    }

}