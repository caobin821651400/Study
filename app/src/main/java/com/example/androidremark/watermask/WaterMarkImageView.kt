package com.example.androidremark.watermask

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.example.androidremark.R

open class WaterMarkImageView : WaterMarkStickerView {

    private var mImageView: ImageView? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onCreateContentView(context: Context): View {
        mImageView = ImageView(context)
        mImageView!!.setImageResource(R.mipmap.ic_launcher)
        return mImageView!!
    }
}