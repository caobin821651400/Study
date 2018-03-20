/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidremark.ui.interfaceloading;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.androidremark.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 界面loading
 */
public class XLoadingLayout extends FrameLayout {


    OnClickListener mRetryListener;
    private int mEmptyViewResId = NO_ID;
    private int mErrorViewResId = NO_ID;
    private int mLoadingViewResId = NO_ID;
    private int mNoNetworkViewResId = NO_ID;
    private int mContentViewResId = NO_ID;
    public static XLoadingViewConfig config = new XLoadingViewConfig();
    LayoutInflater mInflater;
    Map<Integer, View> mLayouts = new HashMap<>();

    public static XLoadingLayout wrap(Activity activity) {
        return wrap(((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static XLoadingLayout wrap(Fragment fragment) {
        return wrap(fragment.getView());
    }

    public static XLoadingLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (view == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        XLoadingLayout layout = new XLoadingLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }

    public XLoadingLayout(Context context) {
        this(context, null, 0);
    }

    public XLoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XLoadingView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(R.styleable.XLoadingView_emptyView, config.getEmptyViewResId());
        mErrorViewResId = a.getResourceId(R.styleable.XLoadingView_errorView, config.getErrorViewResId());
        mLoadingViewResId = a.getResourceId(R.styleable.XLoadingView_loadingView, config.getLoadingViewResId());
        mNoNetworkViewResId = a.getResourceId(R.styleable.XLoadingView_noNetworkView, config.getNoNetworkViewResId());
        a.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            return;
        }
        if (getChildCount() > 1) {
            removeViews(1, getChildCount() - 1);
        }
        View view = getChildAt(0);
        setContentView(view);
        showLoading();
    }

    private void setContentView(View view) {
        mContentViewResId = view.getId();
        mLayouts.put(mContentViewResId, view);
    }

    public void showLoading() {
        show(mLoadingViewResId);
    }

    public void showEmpty() {
        show(mEmptyViewResId);
    }

    public void showError() {
        show(mErrorViewResId);
    }

    public void showContent() {
        show(mContentViewResId);
    }

    public final void showNoNetwork() {
        show(mNoNetworkViewResId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }

    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);

        if (layoutId == mErrorViewResId || layoutId == mNoNetworkViewResId) {
            View v = layout.findViewById(R.id.xloading_retry);
            if (mRetryListener != null) {
                if (v != null)
                    v.setOnClickListener(mRetryListener);
                else
                    layout.setOnClickListener(mRetryListener);
            }
        }
        return layout;
    }

    /**
     * 设置重试点击事件
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mRetryListener = onRetryClickListener;
    }
}
