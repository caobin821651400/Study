package com.example.androidremark.watermask

import android.graphics.Matrix
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.sqrt


/**
 * 选装帮助类
 */

class StickerRotateHelper(private val mContainer: WaterMarkStickerView, private val mView: View) : View.OnTouchListener {

    private var mCenterX: Float = 0F
    private var mCenterY: Float = 0F

    private var mRadius: Double = 0.toDouble()
    private var mDegrees: Double = 0.toDouble()

    private val M = Matrix()

    init {
        mView.setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                mCenterY = 0f
                mCenterX = mCenterY
                var pointX = mView.x + x - mContainer.pivotX
                var pointY = mView.y + y - mContainer.pivotY
                mRadius = toLength(0f, 0f, pointX, pointY)
                mDegrees = toDegrees(pointY, pointX)
                M.setTranslate(pointX - x, pointY - y)
                M.postRotate((-toDegrees(pointY, pointX)).toFloat(), mCenterX, mCenterY)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val xy = floatArrayOf(event.x, event.y)
                var pointX = mView.x + xy[0] - mContainer.pivotX
                var pointY = mView.y + xy[1] - mContainer.pivotY
                val radius = toLength(0f, 0f, pointX, pointY)
                val degrees = toDegrees(pointY, pointX)
                val scale = (radius / mRadius).toFloat()
                mContainer.addScale(scale)
                mContainer.rotation = (mContainer.rotation + degrees - mDegrees).toFloat()
                mRadius = radius
                return true
            }
        }
        return false
    }

    companion object {
        private fun toDegrees(v: Float, v1: Float): Double {
            return Math.toDegrees(atan2(v.toDouble(), v1.toDouble()))
        }

        private fun toLength(x1: Float, y1: Float, x2: Float, y2: Float): Double {
            return sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toDouble())
        }
    }
}
