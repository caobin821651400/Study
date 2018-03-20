package com.example.androidremark.ui2.meituan;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.bean.ProductTypeBean;
import com.example.androidremark.bean.ProductTypeBean;
import com.example.androidremark.bean.ShopProductBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 美团外卖左右联动
 */
public class MTOutFoodActivity extends BaseActivity {

    private ListView leftListView;
    private ListView rightListView;

    private List<String> leftTxtStrings;
    private List<ProductTypeBean> productCategorizes;
    private List<ShopProductBean> shopProductsAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtout_food);
        initView();
    }

    private void initView() {
        getData();
        leftListView = (ListView) findViewById(R.id.list_view_left);
        rightListView = (ListView) findViewById(R.id.list_view_right);

        leftTxtStrings = new ArrayList<>();
        for(ProductTypeBean type :productCategorizes){
            leftTxtStrings.add(type.getType());
        }
        //左边的listView
        leftListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.categorize_item, leftTxtStrings));
        rightListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.categorize_item, leftTxtStrings));
    }

    public List<ProductTypeBean> getData() {
        productCategorizes = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            ProductTypeBean productCategorize = new ProductTypeBean();
            productCategorize.setType("分类信息" + i);
            shopProductsAll = new ArrayList<>();
            for (int j = 1; j < 6; j++) {
                ShopProductBean product = new ShopProductBean();
                product.setId(154788 + i + j);
                product.setGoods("衬衫" + i);
                product.setPrice(18 + "");
                shopProductsAll.add(product);
            }
            productCategorize.setProduct(shopProductsAll);
            productCategorizes.add(productCategorize);
        }
        return productCategorizes;
    }
}
