package com.monosky.daily.volley;

import android.content.Context;

import com.monosky.daily.util.LogUtils;


/**
 * 自定义Volley请求
 */
public class IRequest {
    /**
     * 返回String get
     *
     * @param context
     * @param url
     * @param l
     */
    public static void get(Context context, String url, RequestListener l) {
        LogUtils.i("======================================================");
        LogUtils.i("发起Get请求，URL="+url);
        LogUtils.i("======================================================");
        RequestManager.get(url, context, l);
    }

    /**
     * 返回String post
     *
     * @param context
     * @param url
     * @param params
     * @param l
     */
    public static void post(Context context, String url, RequestParams params,
                            RequestListener l) {
        LogUtils.i("======================================================");
        LogUtils.i("发起Post请求，URL="+url+"/n参数="+params);
        LogUtils.i("======================================================");
        RequestManager.post(url, context, params, l);
    }
}
