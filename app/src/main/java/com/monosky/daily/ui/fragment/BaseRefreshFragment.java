package com.monosky.daily.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 可刷新的Fragment基类
 */
public abstract class BaseRefreshFragment extends Fragment {

    protected String mRequestType = ConstData.REQUEST_REFRESH;
    protected Boolean mRefreshing = false;
    protected Boolean mLoading = false;
    protected Boolean mHasNext = true;
    protected int mPositionStart = 0;
    protected int mItemCount = 0;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayout();

    protected void initSwipeLayout() {
        mSwipeRefresh.setColorSchemeResources(R.color.main_color);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRequestType = ConstData.REQUEST_REFRESH;
                onRefreshStarted();
            }
        });
        // 第一次进入时，显示刷新，必须这样调用，否则进度图片无法显示
        mSwipeRefresh.post(new Runnable() {

            @Override
            public void run() {
                mRefreshing = true;
                mSwipeRefresh.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRequestType = ConstData.REQUEST_REFRESH;
                        onRefreshStarted();
                    }
                }, 998);
            }
        });
    }

    protected abstract void onRefreshStarted();

}
