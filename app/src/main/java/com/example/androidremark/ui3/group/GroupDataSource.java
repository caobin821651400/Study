package com.example.androidremark.ui3.group;

import java.util.ArrayList;

public class GroupDataSource {
    private static ArrayList<BaseGroupBean> BaseGroupBeanList;

    //测试数据
    public static ArrayList<BaseGroupBean> getBaseGroupBeanList() {
        if (null == BaseGroupBeanList || BaseGroupBeanList.isEmpty()) {
            BaseGroupBeanList = new ArrayList<>();
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeChild, "智能手机行业正处于"));
            BaseGroupBeanList.add(new BaseGroupBean(BaseGroupBean.typeGroup, "智能手机行业正处于"));
        }
        return BaseGroupBeanList;
    }
}
