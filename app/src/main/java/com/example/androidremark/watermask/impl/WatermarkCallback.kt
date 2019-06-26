package com.example.androidremark.watermask.impl

import android.view.View

/**
 * 水印显示显示回调
 */
interface WatermarkCallback {

    fun <V : View> onDismiss(stickerView: V) where V : WatermarkSticker

    fun <V : View> onShowing(stickerView: V) where V : WatermarkSticker

    fun <V : View> onRemove(stickerView: V): Boolean where V : WatermarkSticker
}