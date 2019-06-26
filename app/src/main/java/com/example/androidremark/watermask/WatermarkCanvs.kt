package com.example.androidremark.watermask

import android.graphics.Bitmap
import android.graphics.RectF

/**
 * 底层画布
 */
class WatermarkCanvs {
    //背景原图
    private val mImageBitmap: Bitmap? = null
    //背景原图的矩阵
    private var mImageFrame = RectF()
    //水印后的整体矩阵
    private var mAllFrame = RectF()
    //可见区域（屏幕可见区域）
    private var mWindow = RectF()
    //是否在初始位置
    private var isInitialHoming = false


}