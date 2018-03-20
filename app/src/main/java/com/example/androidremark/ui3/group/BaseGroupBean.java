package com.example.androidremark.ui3.group;

import java.io.Serializable;

/**
 * Created by cb on 2017/12/1.
 */

public class BaseGroupBean implements Serializable {

    public static final int typeGroup = 0;
    public static final int typeChild = 1;

    private int type;
    private String title;

    public BaseGroupBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
