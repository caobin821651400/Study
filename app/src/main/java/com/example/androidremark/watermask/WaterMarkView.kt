package com.example.androidremark.watermask

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.androidremark.watermask.impl.WaterMarkStickerImpl
import com.example.androidremark.watermask.impl.WatermarkCallback
import kotlin.math.roundToInt

open class WaterMarkView : FrameLayout, WatermarkCallback {

    private var mImage = WatermarkCanvs()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {

    }

    fun setImageBitmap(bitmap: Bitmap) {
        mImage.setBitmap(bitmap)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        onDrawImages(canvas)
    }

    fun onDrawImages(canvas: Canvas) {
        canvas.save()
        mImage.onDrawImage(canvas)
        mImage.onDrawStickers(canvas)
        canvas.restore()
    }

    fun saveBitmap(): Bitmap {
        mImage.stickAll()
        val scale = 1f / mImage.getScale()
        val frame = RectF(mImage.getFinallyFrame())
        // 旋转基画布
        val m = Matrix()
        m.setRotate(0f, frame.centerX(), frame.centerY())
        m.mapRect(frame)
        // 缩放基画布
        m.setScale(scale, scale, frame.left, frame.top)
        m.mapRect(frame)
        val bitmap = Bitmap.createBitmap(frame.width().roundToInt(),
                frame.height().roundToInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        // 平移到基画布原点&缩放到原尺寸
        canvas.translate(-frame.left, -frame.top)
        canvas.scale(scale, scale, frame.left, frame.top)
        onDrawImages(canvas)
        return bitmap
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            mImage.onWindowChanged((right - left).toFloat(), (bottom - top).toFloat())
        }
    }

    override fun <V : View> onShowing(stickerView: V) where V : WaterMarkStickerImpl {
        mImage.onShowing(stickerView)
        invalidate()
    }

    override fun <V : View> onRemove(stickerView: V): Boolean where V : WaterMarkStickerImpl {
        mImage?.onRemoveSticker(stickerView)
        stickerView.unregisterCallback(this)
        val parent = stickerView.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(stickerView)
        }
        return true
    }

    override fun <V : View> onDismiss(stickerView: V) where V : WaterMarkStickerImpl {
        mImage.onDismiss(stickerView)
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mImage.release()
    }

    fun <V : View> addStickerView(stickerView: V?, params: LayoutParams) where V : WaterMarkStickerImpl {
        if (stickerView != null) {
            addView(stickerView, params)
            stickerView.registerCallback(this)
            mImage.addSticker(stickerView)
        }
    }

    fun addStickerText() {
        val imageView = WaterMarkImageView(context)
        val layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        )
        // Center of the drawing window.
        layoutParams.gravity = Gravity.CENTER
        imageView.x = scrollX.toFloat()
        imageView.y = scrollY.toFloat()
        addStickerView(imageView, layoutParams)
    }
}