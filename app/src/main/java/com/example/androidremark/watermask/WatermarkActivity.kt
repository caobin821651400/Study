package com.example.androidremark.watermask

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.example.androidremark.R
import com.example.androidremark.base.BaseActivity
import com.example.androidremark.permission.XPermission
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class WatermarkActivity : BaseActivity() {

    private var mImgView: WaterMarkView? = null
    private var bitmap: Bitmap? = null
    /***需要自己换成本地图片的path***/
    /***需要自己换成本地图片的path***/
    /***需要自己换成本地图片的path***/
    /***需要自己换成本地图片的path***/
    /***需要自己换成本地图片的path***/
    val imagePath = "/storage/sdcard0/1/下载.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        XPermission.requestPermissions(this, 1024, arrayOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ),
                object : XPermission.OnPermissionListener {
                    override fun onPermissionDenied() {

                    }

                    override fun onPermissionGranted() {
                    }
                })

        val file = File(imagePath)
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(imagePath)
            setContentView(R.layout.activity_water_mask)
            initView()
        } else {
            finish()
        }


    }

    private fun initView() {
        mImgView = findViewById(R.id.image_canvas)

        bitmap?.let {
            mImgView?.setImageBitmap(bitmap!!)
        }
    }

    fun onClick(view: View) {
        mImgView?.addStickerText()
    }

    fun complete(view: View) {
        val bitmap = mImgView?.saveBitmap()
        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/1/")
        if (!file.exists()) file.mkdirs()
        if (bitmap != null) {
            var fout: FileOutputStream? = null
            try {
                fout = FileOutputStream(Environment.getExternalStorageDirectory().absolutePath + "/1/test.jpg")
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fout)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } finally {
                if (fout != null) {
                    try {
                        fout.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            toast("保存成功")
            return
        }
    }
}
