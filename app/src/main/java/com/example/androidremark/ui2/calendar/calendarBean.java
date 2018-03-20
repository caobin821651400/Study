package com.example.androidremark.ui2.calendar;

import java.util.Date;

/**
 * Created by caobin on 2017/9/19.
 */

public class calendarBean {

    private Date date;
    private boolean isMark;

//    public calendarBean(Date date) {
//        this.date = date;
//    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
