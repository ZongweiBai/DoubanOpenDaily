package com.monosky.daily.ui.activity.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.ui.activity.BaseActivity;
import com.monosky.daily.ui.activity.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * APP loading页
 */
public class AppSplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Bind(R.id.app_copyright)
    TextView mAppCopyright;
    @Bind(R.id.app_name)
    TextView mAppName;
    @Bind(R.id.app_desc)
    TextView mAppDesc;

    @Override
    protected int getLayout() {
        return R.layout.activity_app_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mAppCopyright.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.white));
        mAppName.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.white));
        mAppDesc.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.white));
        mAppCopyright.setText("@2014 Douban.Inc.");
        skip2NextAct();
    }

    /**
     * 等待2秒进入主页面
     */
    private void skip2NextAct() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AppSplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                supportFinishAfterTransition();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
