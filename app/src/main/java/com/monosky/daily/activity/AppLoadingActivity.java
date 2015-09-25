package com.monosky.daily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.monosky.daily.R;

/**
 * APP loading页
 */
public class AppLoadingActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_loading);
        ((TextView) findViewById(R.id.app_copyright)).setText("@2014 Douban.Inc.");
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
                if (SPLASH_DISPLAY_LENGHT - costTime > 0) {
                    try {
                        Thread.sleep(SPLASH_DISPLAY_LENGHT - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //进入主页面
                Intent intent = new Intent(AppLoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }).start();

    }

}
