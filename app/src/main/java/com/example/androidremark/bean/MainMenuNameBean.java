package com.example.androidremark.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MQ on 2017/3/12.
 */

public class MainMenuNameBean implements Parcelable {
    private String title;
    private String btn_one;
    private String btn_two;
    private String btn_three;
    private String btn_four;
    private int color;

    public String getTitle() {
        return title;
    }

    public String getBtn_one() {
        return btn_one;
    }

    public String getBtn_two() {
        return btn_two;
    }

    public String getBtn_three() {
        return btn_three;
    }

    public String getBtn_four() {
        return btn_four;
    }

    public MainMenuNameBean(String title, String btn_one, String btn_two, String btn_three, String btn_four) {
        this.title = title;
        this.btn_one = btn_one;
        this.btn_two = btn_two;
        this.btn_three = btn_three;
        this.btn_four = btn_four;
//        this.color = color;
    }

    protected MainMenuNameBean(Parcel in) {
        title = in.readString();
        btn_one = in.readString();
        btn_two = in.readString();
        btn_three = in.readString();
        btn_four = in.readString();
        color = in.readInt();
    }

    public static final Creator<MainMenuNameBean> CREATOR = new Creator<MainMenuNameBean>() {
        @Override
        public MainMenuNameBean createFromParcel(Parcel in) {
            return new MainMenuNameBean(in);
        }

        @Override
        public MainMenuNameBean[] newArray(int size) {
            return new MainMenuNameBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(btn_one);
        dest.writeString(btn_two);
        dest.writeString(btn_three);
        dest.writeString(btn_four);
        dest.writeInt(color);
    }
}
