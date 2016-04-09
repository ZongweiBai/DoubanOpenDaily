package com.monosky.daily.util;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * JsHostScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
 */
public class JsHostScope {
    /**
     * 短暂气泡提醒
     *
     * @param webView 浏览器
     * @param message 提示信息
     */
    public static void toast(WebView webView, String message) {
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开系统浏览器
     *
     * @param webView 浏览器
     * @param url     要打开的链接
     */
    public static void openBrowser(WebView webView, String url) {
        ToastUtils.showShort(webView.getContext(), url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        webView.getContext().startActivity(intent);
    }

}