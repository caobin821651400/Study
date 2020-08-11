package com.example.androidremark.ui3.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.example.androidremark.R;

import javax.inject.Inject;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/7/26 11:19
 * @Desc :Dagger2测试Activity
 * ====================================================
 */
public class DaggerTestActivity extends AppCompatActivity {

    //    @Named("AAA")
    @Inject
    AObject aObject;

//    @Named("bbb")
//    @Inject
//    AObject aaObject;

    @Inject
    BObject bObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);
        initView();
    }

    /**
     *
     */
    private void initView() {
        //
        DaggerMainComponent.builder()
                .mainModule(new MainModule())
                .build()
                .inject(this);
        aObject.eat();
        bObject.eat();

//        aaObject.eat();
    }
}
