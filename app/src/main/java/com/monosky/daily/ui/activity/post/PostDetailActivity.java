package com.monosky.daily.ui.activity.post;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.presenter.cache.ACache;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.view.actionItemBadge.ActionItemBadge;
import com.monosky.daily.util.MD5Util;
import com.monosky.daily.util.ToastUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.Bind;

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

    @Override
    protected int getLayout() {
        return R.layout.activity_post_detail;
    }

    @Override
    protected void initData() {
        mPostEntity = (PostEntity) getIntent().getSerializableExtra("post");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle("");
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // 设置webview能执行javaScript脚本
        mContentWebView.getSettings().setJavaScriptEnabled(true);
        mContentWebView.setWebViewClient(new contentWebViewClient());
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
                Intent intent = new Intent(PostDetailActivity.this, PostReplyActivity.class);
                PostDetailActivity.this.startActivity(intent);
                break;
            case R.id.action_fav:
                break;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mPostEntity.getShare_pic_url());
                sendIntent.setType("image/*");
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

    private class contentWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSwipeRefresh.setRefreshing(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
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
