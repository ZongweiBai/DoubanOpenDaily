package com.monosky.daily.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.util.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Activity可刷新基类
 */
public abstract class BaseRefreshActivity extends AppCompatActivity {

    protected String mRequestType = ConstData.REQUEST_REFRESH;
    protected Boolean mRefreshing = false;
    protected Boolean mLoading = false;
    protected Boolean mHasNext = true;
    protected int mPositionStart = 0;
    protected int mItemCount = 0;

    @Bind(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取手机屏幕宽高
        getScreenParams();

        // 设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.blue);//设置颜色

        setContentView(getLayout());
        ButterKnife.bind(this);

        initData();
        initViews();
        initSwipeLayout();
    }


    private void getScreenParams() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        BaseApplication.screenWidth = dm.widthPixels;
        BaseApplication.screenHeight = dm.heightPixels;
        BaseApplication.densityDpi = dm.densityDpi;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void initViews();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
