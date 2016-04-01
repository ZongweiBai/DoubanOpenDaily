package com.monosky.daily.presenter.rxvolley;

/**
 * 异步请求结束回调
 */
public interface RequestListener {

    void requestSuccess(Object... params);

    void requestError(int errorCode, String errorDesc);

}
