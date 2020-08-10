package com.example.androidremark.ui3.rv

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.AbsListView
import com.example.androidremark.R
import com.example.androidremark.base.BaseActivity
import com.example.androidremark.ui2.baserecycler.XRecyclerViewAdapter
import com.example.androidremark.ui2.baserecycler.XViewHolder
import com.example.androidremark.utils.MyUtils
import kotlinx.android.synthetic.main.activity_rv_progress.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/7 11:33
 * @Desc :横向rv进度条
 * ====================================================
 */
class RvProgressActivity : BaseActivity() {

    private lateinit var mAdapter:DataStatisticsModuleAdapter
    private var mRvItemWidth = 0F
    private var dp15 = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_progress)
        initView()
    }

    private fun initView() {


        dp15 = MyUtils.dp2px(this, 15f);

        //测量要等view加载完成
        mRecyclerView.post {
            //计算每个item宽度
            mRvItemWidth = (mRecyclerView.getMeasuredWidth() - dp15 * 6) * 1.0f / 3;

            mRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
            mAdapter = DataStatisticsModuleAdapter(mRecyclerView)
            mRecyclerView.adapter = mAdapter

            mAdapter.dataList= listOf("","","","","","","","","","")
        }


        mRvProgress.setRecyclerView(mRecyclerView)
        mRvProgress.setRowItemCount(3)
    }


    /**
     *
     */
    inner class DataStatisticsModuleAdapter(@NonNull mRecyclerView: RecyclerView)
        : XRecyclerViewAdapter<String>(mRecyclerView, R.layout.item_list_rv_progress) {

        protected override fun bindData(holder: XViewHolder, data: String, position: Int) {
            val params = ViewGroup.MarginLayoutParams(mRvItemWidth.toInt(), AbsListView.LayoutParams.WRAP_CONTENT).apply {
                setMargins(dp15, dp15, dp15, dp15)
            }
            holder.itemView.setLayoutParams(params)

        }
    }
}
