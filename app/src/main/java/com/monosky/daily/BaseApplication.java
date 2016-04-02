package com.monosky.daily;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.monosky.daily.constant.ConstData;
import com.monosky.daily.util.LogUtils;
import com.monosky.daily.util.SPUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.UUID;

public class BaseApplication extends Application {

    private static BaseApplication baseApplicationInstance;
    private static Context mContext;
    public static int screenWidth = 0;  //dp
    public static int screenHeight = 0; //dp
    public static int densityDpi = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplicationInstance = this;
        mContext = getApplicationContext();

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

    }

    public static BaseApplication getInstance() {
        return baseApplicationInstance;
    }

    public static Context getContext() {
        return mContext;
    }
}
