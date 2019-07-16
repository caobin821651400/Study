package com.example.androidremark.watermask

import android.graphics.*
import com.example.androidremark.watermask.impl.WaterMarkSticker
import java.util.*
import kotlin.math.min

/**
 * 底层画布
 */
class WatermarkCanvs {

    //一个默认的bitmap,相当于java的static静态
    companion object {
        private val mDefaultBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    //背景原图
    private var mImageBitmap: Bitmap? = null
    //背景原图的矩阵(完整的边框)
    private var mImageFrame = RectF()
    //水印后的整体矩阵
    private var mFinallyFrame = RectF()
    //可见区域（屏幕可见区域）
    private var mWindow = RectF()
    //是否在初始位置
    private var isInitialHoming = false
    //当前选中的水印
    private var mSticker: WaterMarkSticker? = null
    //水印集合
    private var mStickers = ArrayList<WaterMarkSticker>()
    //
    private var matrix = Matrix()

    /**
     * 构造方法
     */
    init {
        mImageBitmap = mDefaultBitmap
    }

    /**
     *
     */
    fun setBitmap(bitmap: Bitmap) {
        if (bitmap == null || bitmap.isRecycled) return
        this.mImageBitmap = bitmap
    }

    /**
     *
     */
    private fun onImageChanged() {
        isInitialHoming = false
        onWindowChanged(mWindow.width(), mWindow.height())
    }

    /**
     *
     */
    fun onWindowChanged(width: Float, height: Float) {
        if (width == 0F || height == 0F) return
        mWindow.set(0F, 0F, width, height)

        if (!isInitialHoming) {
            onInitialHoming()
        } else {
            matrix.setTranslate(mWindow.centerX() - mFinallyFrame.centerX(), mWindow.centerY() - mFinallyFrame.centerY())
            matrix.mapRect(mImageFrame)
            matrix.mapRect(mFinallyFrame)
        }
    }

    /**
     * 初始化位置
     */
    private fun onInitialHoming() {
        mImageFrame.set(0F, 0F, mImageBitmap!!.width.toFloat(), mImageBitmap!!.height.toFloat())
        mFinallyFrame.set(mImageFrame)
        if (mFinallyFrame.isEmpty) return
        toBaseHoming()
        isInitialHoming = true;
    }

    /**
     *
     */
    private fun toBaseHoming() {
        if (mFinallyFrame.isEmpty) return

        val scale = min(mWindow.width() / mFinallyFrame.width(), mWindow.height() / mFinallyFrame.height())
        matrix.setScale(scale, scale, mFinallyFrame.centerX(), mFinallyFrame.centerY())
        matrix.postTranslate(mWindow.width() - mFinallyFrame.width(), mWindow.height() - mFinallyFrame.height())
        matrix.mapRect(mImageFrame)
        matrix.mapRect(mFinallyFrame)
    }

    /**
     * 画背景
     */
    fun onDrawImage(canvas: Canvas) {
        canvas.drawBitmap(mImageBitmap, null, mImageFrame, null)
    }

    fun onDrawImage(canvas: Canvas, bitmap: Bitmap) {
        canvas.save()
        val paint = Paint()
        paint.isAntiAlias = true
        canvas.drawBitmap(bitmap, null, mImageFrame, null)
    }

    /**
     * 画水印
     */
    fun onDrawStickers(canvas: Canvas) {
        if (mStickers.isEmpty()) return
        canvas.save()
        for (sticker in mStickers) {
            if (!sticker.isShowing()) {
                val tPivotX = sticker.getX() + sticker.getPivotX()
                val tPivotY = sticker.getY() + sticker.getPivotY()
                canvas.save()
                matrix.setTranslate(sticker.getX(), sticker.getY())
                matrix.postScale(sticker.getScale(), sticker.getScale(), tPivotX, tPivotY)
                matrix.postRotate(sticker.getRotation(), tPivotX, tPivotY)
                canvas.concat(matrix)
                sticker.onSticker(canvas)
                canvas.restore()
            }
        }
        canvas.restore()
    }

    /**
     *添加一个一个的水印
     */
    fun <S : WaterMarkSticker> addSticker(sticker: S) {
        moveToForeground(sticker)
    }

    /**
     *
     */
    private fun moveToForeground(sticker: WaterMarkSticker) {
        if (sticker == null) return
        moveToBackground(sticker)

        if (sticker.isShowing()) {
            mSticker = sticker
            mStickers.remove(sticker)
        } else {
            sticker.show()
        }
    }

    /**
     *
     */
    private fun moveToBackground(sticker: WaterMarkSticker) {
        if (sticker == null) return
        if (sticker.isShowing()) {
            if (!mStickers.contains(sticker))
                mStickers.add(sticker)
            if (mSticker == sticker)
                mSticker = null
        } else {
            sticker.show()
        }
    }

    /**
     *
     */
    fun stickAll() {
        mSticker?.let { moveToBackground(it) }
    }

    /**
     *
     */
    fun onDismiss(sticker: WaterMarkSticker) {
        moveToBackground(sticker)
    }

    /**
     *显示
     */
    fun onShowng(sticker: WaterMarkSticker) {
        if (mSticker != sticker) moveToForeground(sticker)
    }

    /**
     * 返回缩放比
     */
    fun getScale(): Float {
        return 1F * mImageFrame.width() / mImageBitmap!!.width
    }

    /**
     * 释放BITMAP
     */
    fun release() {
        if (mImageBitmap != null && mImageBitmap!!.isRecycled) {
            mImageBitmap!!.recycle()
        }
        if (mDefaultBitmap != null && !mDefaultBitmap.isRecycled) {
            mDefaultBitmap.recycle()
        }
    }

    /**
     * 移除水印
     */
    fun onRemoveSticker(sticker: WaterMarkSticker) {
        if (mSticker == sticker) {
            mSticker = null
        } else {
            mStickers.remove(sticker)
        }
    }
}