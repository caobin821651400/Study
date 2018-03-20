package com.example.androidremark.ui3.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * author : cb
 * e-mail : 821651400@qq.com
 * time   : 2017/12/12
 * desc   :
 */
public class ContactsTask extends AsyncTask<Void, Void, List<ContactsBean>> {

    private FileResultCallback<ContactsBean> resultCallback;
    private Context mContext;

    public ContactsTask(Context context, FileResultCallback<ContactsBean> fileResultCallback) {
        this.mContext = context;
        this.resultCallback = fileResultCallback;
    }

    @Override
    protected List<ContactsBean> doInBackground(Void... voids) {
        ArrayList<ContactsBean> contactsList = new ArrayList<>();
        //生成ContentResolver对象
        ContentResolver contentResolver = mContext.getContentResolver();

        // 获得所有的联系人
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        // 循环遍历
        if (cursor != null && cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                ContactsBean bean = new ContactsBean();
                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                String displayName = cursor.getString(displayNameColumn);
//                System.err.println("哈哈 " + "联系人姓名：" + displayName);
                bean.setName(displayName);

                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    List<ContactsBean.PhoneBean> callNumList = new ArrayList<>();
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = mContext.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + contactId, null, null);
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            ContactsBean.PhoneBean phoneBean = new ContactsBean.PhoneBean();
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //电话类型
                            int type = phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//                            System.err.println("哈哈 " + "联系人电话：" + phoneNumber);
//                            System.err.println("哈哈 " + "type：" + type);
//                            System.err.println("哈哈 " + "type：" + mContext.getResources().getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type)));
                            phoneBean.setNumber(phoneNumber);
                            phoneBean.setType(mContext.getResources().getString(ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(type)));
                            callNumList.add(phoneBean);
                        } while (phoneCursor.moveToNext());
                        bean.setCallNum(callNumList);
                        phoneCursor.close();
                    }
                }
                contactsList.add(bean);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return contactsList;
    }

    @Override
    protected void onPostExecute(List<ContactsBean> list) {
        super.onPostExecute(list);
        if (resultCallback != null) {
            resultCallback.onResultCallback(list);
        }
    }
}
