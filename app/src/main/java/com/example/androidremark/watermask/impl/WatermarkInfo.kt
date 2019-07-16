package com.example.androidremark.watermask.impl

/**
 * 水印的信息
 */
interface WatermarkInfo {

    fun getWidth(): Int

    fun getHeight(): Int

    fun getRotation(): Float

    fun getPivotX(): Float

    fun getPivotY(): Float

    fun getX(): Float

    fun getY(): Float

    fun getScale(): Float

    fun setScale(scale: Float)

    fun addScale(scale: Float)
}