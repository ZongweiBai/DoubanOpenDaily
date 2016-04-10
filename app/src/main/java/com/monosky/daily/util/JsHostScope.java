package com.monosky.daily.util;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.ui.activity.author.AuthorMainPageActivity;
import com.monosky.daily.ui.activity.post.PostImageActivity;

/**
 * JsHostScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
 */
public class JsHostScope {
    /**
     * 跳转用户主页
     *
     * @param webView 浏览器
     * @param userId  用户ID
     */
    public static void gotoAuthor(WebView webView, String userId) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setUid(userId);
        AuthorMainPageActivity.gotoAuthorMain(authorEntity);
    }

    /**
     * 打开大图预览
     *
     * @param webView  浏览器
     * @param imageUrl 图片URL
     */
    public static void gotoLargeImg(WebView webView, String imageUrl) {
        PostImageActivity.gotoPostImage(imageUrl);
    }

    /**
     * 打开系统浏览器
     *
     * @param webView 浏览器
     * @param url     要打开的链接
     */
    public static void openBrowser(WebView webView, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        webView.getContext().startActivity(intent);
    }

}