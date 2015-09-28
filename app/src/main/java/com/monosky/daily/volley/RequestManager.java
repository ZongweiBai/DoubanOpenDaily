package com.monosky.daily.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.monosky.daily.BaseApplication;
import com.monosky.daily.util.LogUtils;

import java.io.UnsupportedEncodingException;

/**
 * Volley请求管理类
 */
public class RequestManager {
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(BaseApplication
            .getContext());

    private RequestManager() {
    }

    /**
     * 返回String
     *
     * @param url      连接
     * @param tag      上下文
     * @param listener 回调
     */
    public static void get(String url, Object tag, RequestListener listener) {
        ByteArrayRequest request = new ByteArrayRequest(Request.Method.GET,
                url, null, responseListener(listener),
                responseError(listener));
        addRequest(request, tag);
    }

    /**
     * 返回String
     *
     * @param url      接口
     * @param tag      上下文
     * @param params   post需要传的参数
     * @param listener 回调
     */
    public static void post(String url, Object tag, RequestParams params,
                            RequestListener listener) {
        ByteArrayRequest request = new ByteArrayRequest(Request.Method.POST,
                url, params, responseListener(listener),
                responseError(listener));
        addRequest(request, tag);
    }

    /**
     * 成功消息监听 返回String
     *
     * @param l String 接口
     * @return
     */
    protected static Response.Listener<byte[]> responseListener(
            final RequestListener l) {
        return new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] arg0) {
                String data = null;
                try {
                    data = new String(arg0, "UTF-8");
                    LogUtils.i("======================================================");
                    LogUtils.i("获得返回数据:"+data);
                    LogUtils.i("======================================================");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                l.requestSuccess(data);
            }
        };
    }

    /**
     * String 返回错误监听
     *
     * @param l String 接口
     * @return
     */
    protected static Response.ErrorListener responseError(
            final RequestListener l) {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                LogUtils.i("======================================================");
                LogUtils.i("请求错误信息："+e);
                LogUtils.i("======================================================");
                l.requestError(e);
            }
        };
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    /**
     * 在结束页面时调用此方法
     *
     * @param tag
     */
    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
