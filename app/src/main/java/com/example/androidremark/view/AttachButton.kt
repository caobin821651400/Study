package com.example.androidremark.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import com.example.androidremark.R
import java.lang.Math.sqrt

class AttachButton : View {
    private var mLastRawX: Float = 0F
    private var mLastRawY: Float = 0F
    private var isDrug = false
    private var mRootMeasuredWidth = 0
    private var mRootMeasuredHeight = 0
    private var mRootTopY = 0
    private var customIsAttach = false
    private var customIsDrag = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ) {
        isClickable = true
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val mTypedAttay = context.obtainStyledAttributes(it, R.styleable.AttachButton)
            customIsAttach =
                    mTypedAttay.getBoolean(R.styleable.AttachButton_customIsAttach, true)
            customIsDrag =
                    mTypedAttay.getBoolean(R.styleable.AttachButton_customIsDrag, true)
            mTypedAttay.recycle()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        super.dispatchTouchEvent(event)
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            //判断是否需要滑动
            if (customIsDrag) {
                //当前手指的坐标
                val mRawX = it.rawX
                val mRawY = it.rawY
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {//手指按下
                        isDrug = false
                        //记录按下的位置
                        mLastRawX = mRawX
                        mLastRawY = mRawY
                        if (parent is ViewGroup) {
                            val mViewGroup = parent as ViewGroup
                            val location = IntArray(2)
                            mViewGroup.getLocationInWindow(location)
                            //获取父布局的高度
                            mRootMeasuredHeight = mViewGroup.measuredHeight
                            mRootMeasuredWidth = mViewGroup.measuredWidth
                            //获取父布局顶点的坐标
                            mRootTopY = location[1]
                        }
                    }
                    MotionEvent.ACTION_MOVE -> {//手指滑动
                        if (mRawX >= 0 && mRawX <= mRootMeasuredWidth && mRawY >= mRootTopY && mRawY <= (mRootMeasuredHeight + mRootTopY)) {
                            //手指X轴滑动距离
                            val differenceValueX: Float = mRawX - mLastRawX
                            //手指Y轴滑动距离
                            val differenceValueY: Float = mRawY - mLastRawY
                            //判断是否为拖动操作
                            if (!isDrug) {
                                isDrug =
                                        sqrt(((differenceValueX * differenceValueX) + (differenceValueY * differenceValueY)).toDouble()) >= 2
                            }
                            //获取手指按下的距离与控件本身X轴的距离
                            val ownX = x
                            //获取手指按下的距离与控件本身Y轴的距离
                            val ownY = y
                            //理论中X轴拖动的距离
                            var endX: Float = ownX + differenceValueX
                            //理论中Y轴拖动的距离
                            var endY: Float = ownY + differenceValueY
                            //X轴可以拖动的最大距离
                            val maxX: Float = mRootMeasuredWidth - width.toFloat()
                            //Y轴可以拖动的最大距离
                            val maxY: Float = mRootMeasuredHeight - height.toFloat()
                            //X轴边界限制
                            endX = if (endX < 0) 0F else (if (endX > maxX) maxX else endX)
                            //Y轴边界限制
                            endY = if (endY < 0) 0F else (if (endY > maxY) maxY else endY)
                            //开始移动
                            x = endX
                            y = endY
                            //记录位置
                            mLastRawX = mRawX
                            mLastRawY = mRawY
                        }
                    }

                    MotionEvent.ACTION_UP -> {//手指离开
                        if (customIsAttach) {
                            //判断是否为点击事件
                            if (isDrug) {
                                val center = mRootMeasuredWidth / 2
                                //自动贴边
                                if (mLastRawX <= center) {
                                    //向左贴边
                                    animate()
                                            .setInterpolator(BounceInterpolator())
                                            .setDuration(500)
                                            .x(0F)
                                            .start()
                                } else {
                                    //向右贴边
                                    animate()
                                            .setInterpolator(BounceInterpolator())
                                            .setDuration(500)
                                            .x(mRootMeasuredWidth - width.toFloat())
                                            .start()
                                }
                            }
                        }
                    }
                }
            }
        }
        //是否拦截事件
        return if (isDrug) isDrug else super.onTouchEvent(event)
    }
}
