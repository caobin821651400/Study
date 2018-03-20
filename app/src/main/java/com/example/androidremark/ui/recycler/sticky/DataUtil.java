package com.example.androidremark.ui.recycler.sticky;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sadhu on 2016/9/23.
 * Email static.sadhu@gmail.com
 */
public class DataUtil {


    private static List<String> mUrls;
    private static List<String> mNamse;

    static {
        mUrls = new ArrayList<>();
        mUrls.add("http://192.168.1.123:8080/icon/aap2.png");
        mUrls.add("http://192.168.1.123:8080/icon/app.png");
        mUrls.add("http://192.168.1.123:8080/icon/github.png");
        mUrls.add("http://192.168.1.123:8080/icon/images.png");
        mUrls.add("http://192.168.1.123:8080/icon/line.png");
        mUrls.add("http://192.168.1.123:8080/icon/qq.png");
        mUrls.add("http://192.168.1.123:8080/icon/skype.png");
        mUrls.add("http://192.168.1.123:8080/icon/twitter.png");
        mUrls.add("http://192.168.1.123:8080/icon/skype.png");

        mNamse = new ArrayList<>();
        mNamse.add("QQ");
        mNamse.add("迅雷");
        mNamse.add("github");
        mNamse.add("推特");
        mNamse.add("skype");
        mNamse.add("line");
        mNamse.add("医生站");
        mNamse.add("微信");
        mNamse.add("什么垃圾APP");

    }


    public static ItemInfoBean generateAppInfo(String tag) {
        Random random = new Random();
        int i1 = random.nextInt(9);
        int i2 = random.nextInt(9);
        ItemInfoBean info = new ItemInfoBean(mNamse.get(i1), mUrls.get(i2));
        info.tag = tag;
        return info;
    }
}
