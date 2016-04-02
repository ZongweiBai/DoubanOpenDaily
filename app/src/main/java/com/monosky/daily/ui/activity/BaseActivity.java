package com.monosky.daily.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.util.SystemBarTintManager;

/**
 * Activity基类
 * Created by jonez_000 on 2015/8/15.
 */
public class BaseActivity extends AppCompatActivity {

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

    /**
     * 关闭软键盘
     *
     * @param view
     */
    protected void closeInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
