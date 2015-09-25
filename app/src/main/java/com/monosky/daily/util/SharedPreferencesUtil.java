package com.monosky.daily.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.monosky.daily.BaseApplication;

/**
 * SharedPreferences类，存放APP设置信息
 * Created by jonez_000 on 2015/8/20.
 */
public class SharedPreferencesUtil {
    private static SharedPreferencesUtil ourInstance = new SharedPreferencesUtil();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static final String PREFERENCE_NAME = "APP_INFO";

    public static SharedPreferencesUtil getInstance() {
        if(sharedPreferences == null) {
            sharedPreferences = BaseApplication.getInstance().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            if(editor == null) {
                editor = sharedPreferences.edit();
            }
        }
        return ourInstance;
    }

    private SharedPreferencesUtil() {
    }

    /**
     * 保存信息
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(String key, String value) {
        return editor.putString(key, value).commit();
    }

    /**
     * 获取信息
     * @param key
     * @return
     */
    public String getValue(String key) {
        return sharedPreferences.getString(key, null);
    }

}
