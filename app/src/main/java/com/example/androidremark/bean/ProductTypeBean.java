package com.example.androidremark.bean;

import java.util.List;

/**
 * Created by 小区商圈 on 2016/7/12 0012.
 * 小区商圈商品类型
 */
public class ProductTypeBean {

    private int id;
    private String type;
    private String createtime;
    private List<ShopProductBean> product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<ShopProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ShopProductBean> product) {
        this.product = product;
    }
}
