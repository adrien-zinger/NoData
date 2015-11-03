package com.android.osloh.nodata.ui.handler;

import java.util.ArrayList;
import java.util.List;

public class RecallHandler {
    static List<RecallBean> recalls;
    static List<DefinedDateBean> DefinedDates;

    static RecallHandler recallHandler;

    public void RecallHandler() {
        // todo get in database recalls ans dates
    }

    static public RecallHandler getInstance() {
        return (recallHandler == null) ? recallHandler = new RecallHandler() : recallHandler;
    }


    public List<RecallBean> getRecalls() {
        return (recalls == null) ? recalls = new ArrayList<>() : recalls;
    }

    public void addRecalls() {

    }

    public void modifyRecalls() {

    }


    public List<DefinedDateBean> getDefinedDates() {
        return (DefinedDates == null) ? DefinedDates = new ArrayList<>() : DefinedDates;
    }

    public void addDefinedDate() {

    }

    public void modifyDefinedDate() {

    }

    public class RecallBean {
        private int jour;
        private int heure;
        private int frequence;
        private String description;
        private String messageId;
    }

    public class DefinedDateBean {
        private int jour;
        private int heure;
        private int frequence;
    }
}
