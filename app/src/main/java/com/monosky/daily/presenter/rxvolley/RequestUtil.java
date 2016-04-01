package com.monosky.daily.presenter.rxvolley;

import android.text.TextUtils;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

public class RequestUtil {

    private static RequestListener mRequestListener;

    public RequestListener getRequestListener() {
        return mRequestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        mRequestListener = requestListener;
    }

    public static void get(String requestUrl) {
        RxVolley.get(requestUrl, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                if (mRequestListener != null) {
                    if (TextUtils.isEmpty(result)) {
                        mRequestListener.requestError(-999, "请求数据为空");
                    } else {
                        mRequestListener.requestSuccess(result);
                    }
                }
                super.onSuccess(result);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (mRequestListener != null) {
                    mRequestListener.requestError(errorNo, strMsg);
                }
                super.onFailure(errorNo, strMsg);
            }
        });
    }

}
