package com.example.androidremark.watermask.impl

import android.graphics.Canvas
import android.graphics.RectF

/**
 * 抽象水印的行为
 */
interface WatermarkAction {

    fun show(): Boolean

    fun remove(): Boolean

    fun dismiss(): Boolean

    fun isShowing(): Boolean

    fun getFrame(): RectF

    fun onSticker(canvas: Canvas)

    fun registerCallback(callback: WatermarkCallback)

    fun unregisterCallback(callback: WatermarkCallback)
}