package com.example.androidremark.ui3.column;

import java.io.Serializable;

/**
 * 服务满意度属性图值实体类
 * Created by cb on 2017/11/20.
 */

public class ColumnValueBean implements Serializable {
    private static final long serialVersionUID = 5025826632618719529L;

    private String bottomName;//底下的服务名称
    private int scale;//比例
    private String topValue;//上面显示的比例

    public ColumnValueBean(String bottomName, int scale, String topValue) {
        this.bottomName = bottomName;
        this.scale = scale;
        this.topValue = topValue;
    }

    public ColumnValueBean(String bottomName, int scale) {
        this.bottomName = bottomName;
        this.scale = scale;
    }

    public String getBottomName() {
        return bottomName;
    }

    public void setBottomName(String bottomName) {
        this.bottomName = bottomName;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getTopValue() {
        return topValue;
    }

    public void setTopValue(String topValue) {
        this.topValue = topValue;
    }
}
