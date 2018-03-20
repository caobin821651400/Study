package com.example.androidremark.permission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.androidremark.R;
import com.example.androidremark.base.BaseActivity;

public class PermissionActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission2);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "权限管理", true);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                doCamera();
                break;
            case R.id.btn2:
                doCallPhone();
                break;
            case R.id.btn3:
                sendPermission();
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                break;
        }
    }

    /**
     * 相机
     */
    private void doCamera() {
        XPermission.requestPermissions(this, 101, new String[]{Manifest.permission
                .CAMERA}, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }

            @Override
            public void onPermissionDenied() {
                XPermission.showMyHintDialog(PermissionActivity.this);
            }
        });
    }

    /**
     * 拨打电话
     */
    private void doCallPhone() {
        XPermission.requestPermissions(this, 100, new String[]{Manifest.permission.CALL_PHONE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:15108460749"));
                startActivity(intent);
            }

            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                //给出友好提示，并且提示启动当前应用设置页面打开权限
                XPermission.showMyHintDialog(PermissionActivity.this);
            }
        });
    }

    /**
     * 多个权限
     */
    private void sendPermission() {
        XPermission.requestPermissions(this, 102, new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.WRITE_CONTACTS
        }, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplication(), "申请成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied() {
                XPermission.showMyHintDialog(PermissionActivity.this);
            }
        });
    }
}
