package com.android.osloh.nodata.ui.Utils;

/**
 * Created by Charles on 10/10/2015.
 */
public class Item {
    private String date;
    private String address;
    private String content;

    public Item(){

    }

    public Item(String i, String d, String p){
        this.date = d;
        this.address = i;
        this.content = p;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String details) {
        this.date = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAdress(String name) {
        this.address = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String price) {
        this.content = price;
    }

}