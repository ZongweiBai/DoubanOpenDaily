package com.monosky.daily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * APP loading页
 */
public class AppLoadingActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Bind(R.id.app_copyright)
    TextView mAppCopyright;
    @Bind(R.id.app_name)
    TextView mAppName;
    @Bind(R.id.app_desc)
    TextView mAppDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_loading);
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

        new Thread(new Runnable() {
            public void run() {

                long start = System.currentTimeMillis();
                long costTime = System.currentTimeMillis() - start;
                if (SPLASH_DISPLAY_LENGTH - costTime > 0) {
                    try {
                        Thread.sleep(SPLASH_DISPLAY_LENGTH - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //进入主页面
                Intent intent = new Intent(AppLoadingActivity.this, MainActivity.class);
                startActivity(intent);
                supportFinishAfterTransition();

            }
        }).start();

    }

}
