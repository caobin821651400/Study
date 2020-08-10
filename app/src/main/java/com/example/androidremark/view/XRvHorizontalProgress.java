package com.example.androidremark.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.androidremark.R;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/8/7 11:49
 * @Desc :Rv水平进度条
 * ====================================================
 */
public class XRvHorizontalProgress extends FrameLayout {

    private View mProgressView;//进度view
    private int mProgressWidth;//进度宽
    private Drawable mBackgroundDrawable;//整体背景
    private Drawable mProgressDrawable;//进度背景
    private RecyclerView mRecyclerView;
    private boolean isMaxFull;
    private int rowItemCount;//一行显示几个

    private int transMaxRange;//可以滑动的最大范围

    public XRvHorizontalProgress(Context context) {
        this(context, null);
    }

    public XRvHorizontalProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRvHorizontalProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XRvHorizontalProgress);
        mProgressWidth = array.getDimensionPixelSize(R.styleable.XRvHorizontalProgress_xrv_progress_width, dp2px(20));
        mBackgroundDrawable = array.getDrawable(R.styleable.XRvHorizontalProgress_xrv_background);
        mProgressDrawable = array.getDrawable(R.styleable.XRvHorizontalProgress_xrv_progress_drawable);
        isMaxFull = array.getBoolean(R.styleable.XRvHorizontalProgress_xrv_max_full, false);
        array.recycle();
        init();
    }

    /**
     *
     */
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rv_horizontal_progrss, this, true);
        mProgressView = findViewById(R.id.progress_view);

        if (mBackgroundDrawable != null) {
            setBackground(mBackgroundDrawable);
        }

        if (mProgressDrawable != null) {
            mProgressView.setBackground(mProgressDrawable);
        }

        FrameLayout.LayoutParams layoutParams = (LayoutParams) mProgressView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(mProgressWidth, LayoutParams.MATCH_PARENT);
            mProgressView.setLayoutParams(layoutParams);
        } else {
            layoutParams.width = mProgressWidth;
            layoutParams.height = LayoutParams.MATCH_PARENT;
        }

        mProgressView.post(new Runnable() {
            @Override
            public void run() {
                transMaxRange = getMeasuredWidth() - mProgressView.getMeasuredWidth();
            }
        });



    }

    /**
     * @param rv
     */
    public void setRecyclerView(RecyclerView rv) {
        this.mRecyclerView = rv;

        if (mRecyclerView != null) {
            //计算滑动偏移量
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //小于
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (isMaxFull && rowItemCount != 0 && adapter != null
                            && adapter.getItemCount() <= rowItemCount) {
                        return;
                    }

                    float offset = recyclerView.computeHorizontalScrollOffset() * 1.0f;
                    //可滑动的偏移距离
                    int rvWidthRang = recyclerView.computeHorizontalScrollRange() - recyclerView.computeHorizontalScrollExtent();
                    //滑出部分在剩余范围的比例
                    float proportion = offset / rvWidthRang;
                    //设置滚动条移动
                    if (!Float.isNaN(proportion)) {
                        mProgressView.setTranslationX(transMaxRange * proportion);
                    }
                }
            });
        }
    }

    /**
     * @param rowItemCount
     */
    public void setRowItemCount(int rowItemCount) {
        this.rowItemCount = rowItemCount;
    }

    /**
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }
}
