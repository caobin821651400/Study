package com.example.androidremark.watermask

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.ImageView
import com.example.androidremark.R
import com.example.androidremark.watermask.impl.WaterMarkStickerImpl
import com.example.androidremark.watermask.impl.WatermarkCallback
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * 水印基view
 */
abstract class WaterMarkStickerView : ViewGroup, WaterMarkStickerImpl, View.OnClickListener {
    private val MARK_SIZE = 48//默认大小
    private val MARK_SIZE_HALF = MARK_SIZE shr 1
    private val STROKE_WIDTH = 3f//线宽
    private var mContentView: View? = null//
    private var mDownShowing = 0
    private var mScale = 1F//
    private val mFrame = RectF()
    private val mMatrix = Matrix()
    private val mTempFrame = Rect()
    private var mPaint: Paint? = null
    private var mRemoveView: ImageView? = null//
    private var mAdjustView: ImageView? = null//旋转的
    private var mMoveHelper: StickerMoveHelper? = null
    private var mStickerHelper: StickerHelper<WaterMarkStickerView>? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    /**
     * 初始化操作
     */
    private fun init(context: Context) {
        setBackgroundColor(Color.TRANSPARENT)

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = Color.WHITE
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = STROKE_WIDTH

        mContentView = onCreateContentView(context)
        addView(mContentView, getContentLayoutParams())

        mRemoveView = ImageView(context)
        mRemoveView!!.scaleType = ImageView.ScaleType.FIT_XY
        mRemoveView!!.setImageResource(R.mipmap.image_ic_delete)
        addView(mRemoveView, getMarkLayoutParams())
        mRemoveView!!.setOnClickListener(this)

        mAdjustView = ImageView(context)
        mAdjustView!!.scaleType = ImageView.ScaleType.FIT_XY
        mAdjustView!!.setImageResource(R.mipmap.image_ic_adjust)
        addView(mAdjustView, getMarkLayoutParams())

        val ratateHelper = StickerRotateHelper(this, mAdjustView!!)
        mStickerHelper = StickerHelper(this)
        mMoveHelper = StickerMoveHelper(this)
    }

    /**
     * 具体的view留给子View去实现
     */
    abstract fun onCreateContentView(context: Context): View


    override fun getScale(): Float {
        return mScale
    }

    override fun setScale(scale: Float) {
        mScale = scale
        mContentView?.scaleX = mScale
        mContentView?.scaleY = mScale

        val pivotX = (left + right) shr 1
        val pivotY = (top + bottom) shr 1

        mFrame.set(pivotX.toFloat(), pivotY.toFloat(), pivotX.toFloat(), pivotY.toFloat())
        mFrame.inset(-(mContentView?.measuredWidth!!.shr(1)).toFloat(), -(mContentView?.measuredHeight!!.shr(1)).toFloat())
        mMatrix.setScale(mScale, mScale, mFrame.centerX(), mFrame.centerY())
        mMatrix.mapRect(mFrame)
        mFrame.round(mTempFrame)
        layout(mTempFrame.left, mTempFrame.top, mTempFrame.right, mTempFrame.bottom)
    }

    override fun addScale(scale: Float) {
        setScale(getScale() * scale)
    }

    private fun getContentLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    private fun getMarkLayoutParams(): LayoutParams {
        return LayoutParams(MARK_SIZE, MARK_SIZE)
    }

    override fun onDraw(canvas: Canvas) {
        if (isShowing()) {
            canvas.drawRect(MARK_SIZE_HALF.toFloat(),
                    MARK_SIZE_HALF.toFloat(), (width - MARK_SIZE_HALF).toFloat(), (height - MARK_SIZE_HALF).toFloat(), mPaint)
        }
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val count = childCount
        var maxHeight = 0
        var maxWidth = 0
        var childState = 0
        for (i in 0 until count) {
            var child = getChildAt(i)
            if (child.visibility != View.GONE) {
                child.measure(widthMeasureSpec, heightMeasureSpec)
                maxWidth = max(maxWidth.toFloat(), child.measuredWidth * child.scaleX).roundToInt()
                maxHeight = max(maxHeight.toFloat(), child.measuredHeight * child.scaleY).roundToInt()
                childState = View.combineMeasuredStates(childState, child.measuredState)
            }
        }
        //是否设置了最小宽高
        maxWidth = max(maxWidth, suggestedMinimumWidth)
        maxHeight = max(maxHeight, suggestedMinimumHeight)
        //保存测量值
        setMeasuredDimension(View.resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                View.resolveSizeAndState(maxHeight, heightMeasureSpec, childState shl View.MEASURED_HEIGHT_STATE_SHIFT))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mFrame.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        val count = childCount
        if (count == 0) return

        mRemoveView!!.layout(0, 0, mRemoveView!!.measuredWidth, mRemoveView!!.measuredHeight)
        mAdjustView!!.layout(
                right - left - mAdjustView!!.measuredWidth,
                bottom - top - mAdjustView!!.measuredHeight,
                right - left, bottom - top
        )

        val centerX = (right - left) shr 1
        val centerY = (bottom - top) shr 1
        val hw = mContentView!!.measuredWidth.shr(1)
        val hh = mContentView!!.measuredHeight.shr(1)
        mContentView!!.layout(centerX - hw, centerY - hh, centerX + hw, centerY + hh)
    }

    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        return isShowing() && super.drawChild(canvas, child, drawingTime)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (!isShowing() && ev.action == MotionEvent.ACTION_DOWN) {
            mDownShowing = 0
            show()
            return true
        }
        return isShowing() && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var handled = mMoveHelper!!.onTouch(this, event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> mDownShowing++
            MotionEvent.ACTION_UP -> {
                if (mDownShowing > 1 && event.eventTime - event.downTime < ViewConfiguration.getTapTimeout()) {
                    onContentTap()
                    return true;
                }
            }
        }
        return handled or super.onTouchEvent(event)
    }

    override fun onClick(v: View) {
        if (v === mRemoveView) {
            onRemove()
        }
    }

    fun onRemove() {
        mStickerHelper?.remove()
    }

    fun onContentTap() {

    }

    override fun show(): Boolean {
        return mStickerHelper != null && mStickerHelper!!.show()
    }

    override fun remove(): Boolean {
        return mStickerHelper != null && mStickerHelper!!.remove()
    }

    override fun dismiss(): Boolean {
        return mStickerHelper != null && mStickerHelper!!.dismiss()
    }

    override fun isShowing(): Boolean {
        return mStickerHelper != null && mStickerHelper!!.isShowing()
    }

    override fun getFrame(): RectF {
        return mStickerHelper!!.getFrame()
    }

    override fun onSticker(canvas: Canvas) {
        canvas.translate(mContentView!!.x, mContentView!!.y)
        mContentView!!.draw(canvas)
    }

    override fun registerCallback(callback: WatermarkCallback) {
        mStickerHelper!!.registerCallback(callback)
    }

    override fun unregisterCallback(callback: WatermarkCallback) {
        mStickerHelper!!.unregisterCallback(callback)
    }

}