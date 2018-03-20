package com.example.androidremark.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.androidremark.R;
import com.example.androidremark.permission.XPermission;
import com.example.androidremark.utils.StatusBarUtil;
import com.example.androidremark.utils.XOutdatedUtils;

/**
 * Created by caobin on 2017/1/4.
 */
public class BaseActivity extends AppCompatActivity {
    private boolean isShowRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        setStatusBar();
        super.setContentView(layoutResID);
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    /**
     * 跳转activity
     *
     * @param cls    跳转的类
     * @param bundle 携带的数据
     */
    public void launchActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Toast任何类型的数据
     *
     * @param object
     */
    public void toast(Object object) {
        Toast.makeText(BaseActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp) {
        initToolBar(toolbar, name, showHomeAsUp, false);
    }

    public void initToolBar(Toolbar toolbar, String name, boolean showHomeAsUp, boolean isShowRight) {
        this.isShowRight = isShowRight;
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(XOutdatedUtils.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isShowRight) {
            getMenuInflater().inflate(R.menu.toolbar_right, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_icon:
                //add();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 简化findviewbyid
     * 此处用不到，此基类已使用ButterKnife注解
     * 在不使用注解的时候可以使用
     *
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T mFindViewById(int resId) {
        return (T) findViewById(resId);
    }


    /**
     * 弹出AlertDialog
     *
     * @param msg
     */

    protected void showAlert(String msg) {
        new AlertDialog.Builder(this).setIcon(R.drawable.app_logo).setTitle("温馨提示").setMessage(msg)
                .setPositiveButton("确定", null).create().show();
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
