package com.example.androidremark.watermask

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.view.View
import com.example.androidremark.watermask.impl.WaterMarkStickerImpl
import com.example.androidremark.watermask.impl.WatermarkAction
import com.example.androidremark.watermask.impl.WatermarkCallback

/**
 * 水印帮助类
 */
class StickerHelper<StickerView : View>(private var mView: StickerView)
    : WatermarkAction, WatermarkCallback where StickerView : WaterMarkStickerImpl {

    private var mFrame: RectF? = null
    private var mCallback: WatermarkCallback? = null
    private var isShowing = false

    override fun <V : View> onDismiss(stickerView: V) where V : WaterMarkStickerImpl {
        mFrame = null
        stickerView.invalidate()
        mCallback?.onDismiss(stickerView)
    }

    override fun <V : View> onShowing(stickerView: V) where V : WaterMarkStickerImpl {
        stickerView.invalidate()
        mCallback?.onShowing(stickerView)
    }

    override fun <V : View> onRemove(stickerView: V): Boolean where V : WaterMarkStickerImpl {
        return mCallback != null && mCallback!!.onRemove(stickerView)
    }

    override fun remove(): Boolean {
        return onRemove(mView)
    }

    override fun dismiss(): Boolean {
        if (isShowing) {
            isShowing = false
            onDismiss(mView)
            return true
        }
        return false
    }

    override fun isShowing(): Boolean {
        return isShowing
    }

    override fun getFrame(): RectF {
        if (mFrame == null) {
            mFrame = RectF(0F, 0F, mView.width.toFloat(), mView.height.toFloat())
            val pivotX = mView.x + mView.pivotX
            val pivotY = mView.y + mView.pivotY

            var matrix = Matrix()
            matrix.postTranslate(mView.x, mView.y)
            matrix.setScale(mView.scaleX, mView.scaleY, pivotX, pivotY)
            matrix.mapRect(mFrame)
        }
        return mFrame!!
    }

    override fun onSticker(canvas: Canvas) {

    }

    override fun registerCallback(callback: WatermarkCallback) {
        mCallback = callback
    }

    override fun unregisterCallback(callback: WatermarkCallback) {
        mCallback = null
    }

    override fun show(): Boolean {
        if (!isShowing) {
            isShowing = true
            onShowing(mView)
            return true
        }
        return false
    }
}