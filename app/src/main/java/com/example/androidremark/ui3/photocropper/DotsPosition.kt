package com.example.androidremark.ui3.photocropper

/**
 * @author: cb
 * @date: 2024/3/29
 * @desc: 点的位置标记
 */
annotation class DotsPosition {

    companion object {
        const val TOP_LEFT = 1
        const val TOP_RIGHT = 2
        const val BOTTOM_LEFT = 3
        const val BOTTOM_RIGHT = 4
        const val TOP_CENTER = 6
        const val BOTTOM_CENTER = 7
        const val UNKNOWN = -1
    }
}
