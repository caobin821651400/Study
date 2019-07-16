package com.example.androidremark.watermask

import android.graphics.Matrix
import android.view.MotionEvent
import android.view.View

class StickerMoveHelper(view: View) {

    private var mView: View? = null
    private var mX: Float = 0F
    private var mY: Float = 0F
    private val M = Matrix()

    init {
        this.mView = view
    }

    fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mX = event.x
                mY = event.y
                M.reset()
                M.setRotate(v.rotation)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dxy = floatArrayOf(event.x - mX, event.y - mY)
                M.mapPoints(dxy)
                v.translationX = mView!!.translationX + dxy[0]
                v.translationY = mView!!.translationY + dxy[1]
                return true
            }
        }
        return false
    }

}