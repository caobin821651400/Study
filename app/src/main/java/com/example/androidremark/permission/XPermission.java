package com.example.androidremark.permission;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidremark.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限组列表：
 * Android6.0只用申请权限组中一个权限及获得全部权限
 * Android8.0需要全部申请权限组权限，但是只会申请第一个权限时提示，后面不会提示
 * <p>
 * // 读写日历。
 * Manifest.permission.READ_CALENDAR,
 * Manifest.permission.WRITE_CALENDAR
 * // 相机。
 * Manifest.permission.CAMERA
 * // 读写联系人。
 * Manifest.permission.READ_CONTACTS,
 * Manifest.permission.WRITE_CONTACTS,
 * Manifest.permission.GET_ACCOUNTS
 * // 读位置信息。
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.ACCESS_COARSE_LOCATION
 * // 使用麦克风。
 * Manifest.permission.RECORD_AUDIO
 * // 读电话状态、打电话、读写电话记录。
 * Manifest.permission.READ_PHONE_STATE,
 * Manifest.permission.CALL_PHONE,
 * Manifest.permission.READ_CALL_LOG,
 * Manifest.permission.WRITE_CALL_LOG,
 * Manifest.permission.ADD_VOICEMAIL,
 * Manifest.permission.USE_SIP,
 * Manifest.permission.PROCESS_OUTGOING_CALLS
 * // 传感器。
 * Manifest.permission.BODY_SENSORS
 * // 读写短信、收发短信。
 * Manifest.permission.SEND_SMS,
 * Manifest.permission.RECEIVE_SMS,
 * Manifest.permission.READ_SMS,
 * Manifest.permission.RECEIVE_WAP_PUSH,
 * Manifest.permission.RECEIVE_MMS,
 * Manifest.permission.READ_CELL_BROADCASTS
 * // 读写存储卡。
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE
 */
public class XPermission {

    private static int mRequestCode = -1;

    private static OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {

        void onPermissionGranted();

        void onPermissionDenied();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        mOnPermissionListener = listener;
        if (context instanceof Activity) {
            List<String> deniedPermissions = getDeniedPermissions(context, permissions);
            if (deniedPermissions.size() > 0) {
                mRequestCode = requestCode;
                ((Activity) context).requestPermissions(deniedPermissions
                        .toArray(new String[deniedPermissions.size()]), requestCode);

            } else {
                if (mOnPermissionListener != null)
                    mOnPermissionListener.onPermissionGranted();
            }
        } else {
            throw new RuntimeException("Context must be an Activity");
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                if (verifyPermissions(grantResults)) {
                    mOnPermissionListener.onPermissionGranted();
                } else {
                    mOnPermissionListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 自定义提示框
     */
    public static void showMyHintDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.hint_dialog, null);
        TextView titleTextView = (TextView) view.findViewById(R.id.tv_title);
        TextView msgTextView = (TextView) view.findViewById(R.id.tv_msg);
        Button buttonSure = (Button) view.findViewById(R.id.btn_sure);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        titleTextView.setText("提示信息");
        msgTextView.setText("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。");

        buttonSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startAppSettings(context);
            }
        });
        dialog.show();
    }

    /**
     * 启动当前应用设置页面
     */
    private static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
