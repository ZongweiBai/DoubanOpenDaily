package com.monosky.daily;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by jonez_000 on 2015/8/16.
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplicationInstance;
    public static int screenWidht = 0;  //dp
    public static int screenHeight = 0; //dp
    public static int densityDpi = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplicationInstance = this;

        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }

    public static BaseApplication getInstance() {
        return baseApplicationInstance;
    }
}
