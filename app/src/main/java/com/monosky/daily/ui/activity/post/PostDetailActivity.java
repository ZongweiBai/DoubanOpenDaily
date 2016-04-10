package com.monosky.daily.ui.activity.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.presenter.cache.ACache;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.view.actionItemBadge.ActionItemBadge;
import com.monosky.daily.util.AssetsUtils;
import com.monosky.daily.util.JsHostScope;
import com.monosky.daily.util.LogUtils;
import com.monosky.daily.util.MD5Util;
import com.monosky.daily.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SafeWebViewBridge.InjectedChromeClient;

/**
 * 文章详细页，以webView加载
 */
public class PostDetailActivity extends BaseRefreshActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.content_webview)
    WebView mContentWebView;
    private PostEntity mPostEntity;
    private ACache mACache = ACache.get(BaseApplication.getContext());
    private String mPostHtml;
    private PostHandler mPostHandler = new PostHandler(this);
    private String wholeJS;

    @Override
    protected int getLayout() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void initData() {
        mPostEntity = (PostEntity) getIntent().getSerializableExtra("post");
        wholeJS = AssetsUtils.loadText(this, "post_func.js");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // 设置webView能执行javaScript脚本
        mContentWebView.getSettings().setJavaScriptEnabled(true);
        mContentWebView.setWebViewClient(new contentWebViewClient());
        mContentWebView.setWebChromeClient(
                new CustomChromeClient("HostApp", JsHostScope.class)
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_post_detail, menu);
        if (mPostEntity.getComments_count() > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_comment), R.mipmap.ic_ab_comment, String.valueOf(mPostEntity.getComments_count()));
        }
        if (mPostEntity.getLike_count() > 0) {
            ActionItemBadge.update(this, menu.findItem(R.id.action_fav), R.mipmap.ic_ab_fav_off, String.valueOf(mPostEntity.getLike_count()));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
            case R.id.action_comment:
                PostCommentsActivity.gotoPostComments(mPostEntity);
                break;
            case R.id.action_fav:
                break;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mPostEntity.getUrl());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "分享到"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRefreshStarted() {
        final String md5Key = MD5Util.encrypt(mPostEntity.getUrl());
        mPostHtml = mACache.getAsString(md5Key);
        if (TextUtils.isEmpty(mPostHtml)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = Jsoup.connect(mPostEntity.getUrl()).get();

                        // 新增自定义js
                        Element head = doc.getElementsByTag("title").first();
                        head.after("<script>" + wholeJS + "</script>");

                        // 去除不必要的div
                        Elements mastheads = doc.getElementsByClass("bs-header");
                        if (mastheads != null) {
                            for (Element element : mastheads) {
                                element.remove();
                            }
                        }
                        Elements copyRights = doc.getElementsByClass("copyright");
                        if (copyRights != null) {
                            for (Element copyRight : copyRights) {
                                copyRight.remove();
                            }
                        }
                        Element sideNav = doc.getElementById("sideNav");
                        if (sideNav != null) {
                            sideNav.remove();
                        }
                        Element share = doc.getElementById("share");
                        if (share != null) {
                            share.remove();
                        }

                        Elements footers = doc.getElementsByClass("footer");
                        if (footers != null) {
                            for (Element footer : footers) {
                                footer.remove();
                            }
                        }

                        // 遍历每个文章中的作者
                        Elements metas = doc.getElementsByClass("meta");
                        if (metas != null && !metas.isEmpty()) {
                            Element meta = metas.first();
                            Elements authorElements = meta.getElementsByTag("a");
                            if (authorElements != null && !authorElements.isEmpty()) {
                                Element authorElement = authorElements.first();
                                String userId = authorElement.attr("href"); // https://www.douban.com/people/tiamat/
                                if (!TextUtils.isEmpty(userId)) {
                                    userId = userId.replace("https://www.douban.com/people/", "").replace("/", "");
                                    authorElement.attr("href", "javascript:void(0);");
                                    authorElement.attr("onclick", "gotoAuthor('" + userId + "');");
                                }
                            }
                        }
                        Elements hds = doc.getElementsByClass("hd");
                        if (hds != null && !hds.isEmpty()) {
                            Element meta = hds.first();
                            Elements authorElements = meta.getElementsByTag("a");
                            if (authorElements != null && !authorElements.isEmpty()) {
                                Element authorElement = authorElements.first();
                                String userId = authorElement.attr("href");
                                if (!TextUtils.isEmpty(userId)) {
                                    userId = userId.replace("https://www.douban.com/people/", "").replace("/", "");
                                    authorElement.attr("href", "javascript:void(0);");
                                    authorElement.attr("onclick", "gotoAuthor('" + userId + "');");
                                }
                            }
                        }

                        // 遍历文章中的图片
                        Elements contentImgs = doc.getElementsByClass("content_img");
                        if (contentImgs != null && !contentImgs.isEmpty()) {
                            for (Element contentImg : contentImgs) {
                                Elements imgElements = contentImg.getElementsByTag("img");
                                if (imgElements != null && !imgElements.isEmpty()) {
                                    for (Element imgElement : imgElements) {
                                        String imgUrl = imgElement.attr("src");
                                        if (!TextUtils.isEmpty(imgUrl)) {
                                            imgElement.attr("onclick", "showLargeImg('" + imgUrl + "');");
                                        }
                                    }
                                }
                            }
                        }

                        Elements bds = doc.getElementsByClass("bd");
                        if (bds != null && !bds.isEmpty()) {
                            for (Element contentImg : bds) {
                                Elements imgElements = contentImg.getElementsByTag("img");
                                if (imgElements != null && !imgElements.isEmpty()) {
                                    for (Element imgElement : imgElements) {
                                        String imgUrl = imgElement.attr("src");
                                        if (!TextUtils.isEmpty(imgUrl)) {
                                            imgElement.attr("onclick", "showLargeImg('" + imgUrl + "');");
                                        }
                                    }
                                }
                            }
                        }

                        // 遍历文章中所有的a标签
                        Elements hrefTags = doc.getElementsByTag("a");
                        if (hrefTags != null && !hrefTags.isEmpty()) {
                            String hrefUrl;
                            for (Element hrefTag : hrefTags) {
                                hrefUrl = hrefTag.attr("href");
                                if (!TextUtils.isEmpty(hrefUrl) && !"javascript:void(0);".equals(hrefUrl)) {
                                    hrefTag.attr("href", "javascript:void(0);");
                                    hrefTag.attr("onclick", "openBrowser('" + hrefUrl + "');");
                                }
                            }
                        }

                        mPostHtml = doc.toString();
                        mACache.put(md5Key, mPostHtml);
                        Message msg = new Message();
                        msg.what = 1;
                        mPostHandler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Message msg = new Message();
                        msg.what = 0;
                        mPostHandler.sendMessage(msg);
                    }
                }
            }).start();
        } else {
            mContentWebView.loadData(mPostHtml, "text/html; charset=UTF-8", "null");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private class contentWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mContentWebView.loadUrl("javascript:" + wholeJS);
            mSwipeRefresh.setRefreshing(false);
        }
    }

    public class CustomChromeClient extends InjectedChromeClient {

        public CustomChromeClient(String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }
    }

    public static void gotoPostDetail(PostEntity postsEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), PostDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("post", postsEntity);
        BaseApplication.getContext().startActivity(intent);
    }

    /**
     * 自定义handler，用这种写法解决内存泄露的问题
     */
    static class PostHandler extends Handler {
        WeakReference<PostDetailActivity> mActivity;

        PostHandler(PostDetailActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final PostDetailActivity postDetailActivity = mActivity.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    postDetailActivity.mContentWebView.loadData(postDetailActivity.mPostHtml, "text/html; charset=UTF-8", "null");
                    Document doc = Jsoup.parse(postDetailActivity.mPostHtml);
                    Element head = doc.getElementsByTag("head").first();
                    LogUtils.e("head:" + head.toString());
                    LogUtils.e("all:" + postDetailActivity.mPostHtml);
                    ToastUtils.showShort(BaseApplication.getContext(), "请求成功");
                    break;
                case 0:
                    ToastUtils.showShort(BaseApplication.getContext(), "请求出错");
                    postDetailActivity.mSwipeRefresh.setRefreshing(false);
                    break;
            }
        }
    }

}
