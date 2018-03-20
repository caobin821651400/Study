package com.example.androidremark.ui3.contacts;

import android.Manifest;
import android.os.Bundle;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;
import com.example.androidremark.permission.XPermission;
import com.google.gson.Gson;

import java.util.List;

public class XContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initView();
    }

    private void initView() {
        XPermission.requestPermissions(this, 101, new String[]{Manifest.permission
                .READ_CONTACTS}, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                new ContactsTask(XContactsActivity.this, new FileResultCallback<ContactsBean>() {
                    @Override
                    public void onResultCallback(List<ContactsBean> files) {
                        if (files != null) {
                            System.err.println("哈哈哈 " + new Gson().toJson(files));
                        }
                    }
                }).execute();
            }

            @Override
            public void onPermissionDenied() {
            }
        });
    }
}
