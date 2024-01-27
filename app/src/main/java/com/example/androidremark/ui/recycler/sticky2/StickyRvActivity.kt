package com.example.androidremark.ui.recycler.sticky2

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.androidremark.R
import com.example.androidremark.base.BaseActivity

/**
 * @author: cb
 * @date: 2024/1/27 16:33
 * @desc: 描述
 */
class StickyRvActivity : BaseActivity() {


    private val mAdapter = StickyRvAdapter()
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky2)

        mRecyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        initToolBar(toolbar, "通讯录", true, true)

        val stickyItemTypes = intArrayOf(
            ItemType.VIEW_TYPE_GROUP  //此类型需要吸顶
        )
        mRecyclerView.layoutManager = StickyGridLayoutManager(this, stickyItemTypes, 1)
        mRecyclerView.adapter = mAdapter

        val list = ArrayList<MData>()

        for (i in 0..5) {
            list.add(MData(ItemType.VIEW_TYPE_NORMAL, "我是第${i}个"))
        }
        list.add(MData(ItemType.VIEW_TYPE_GROUP, "悬浮组1"))
        for (i in 0..5) {
            list.add(MData(ItemType.VIEW_TYPE_NORMAL, "我是第${i*2}个"))
        }
        list.add(MData(ItemType.VIEW_TYPE_GROUP, "悬浮组2"))
        for (i in 0..50) {
            list.add(MData(ItemType.VIEW_TYPE_NORMAL, "我是第${i}个"))
        }
        mAdapter.addAll(list)
    }


    class ItemType {
        companion object {
            const val VIEW_TYPE_GROUP = 1
            const val VIEW_TYPE_NORMAL = 2
        }
    }
}