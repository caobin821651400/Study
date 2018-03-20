package com.example.androidremark.ui3.contacts;

import java.io.Serializable;
import java.util.List;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/12
 * desc   :
 */
public class ContactsBean implements Serializable {

    private String name;
    private String headImg;
    private List<PhoneBean> phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public List<PhoneBean> getCallNum() {
        return phone;
    }

    public void setCallNum(List<PhoneBean> callNum) {
        this.phone = callNum;
    }

    public static class PhoneBean implements Serializable {
        private String type;
        private String number;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
