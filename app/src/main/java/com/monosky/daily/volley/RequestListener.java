package com.monosky.daily.volley;

import com.android.volley.VolleyError;

/**
 * Volley请求结束后的监听
 */
public interface RequestListener  {

    /** 成功 */
    public void requestSuccess(String json);

    /** 错误 */
    public void requestError(VolleyError e);
}
