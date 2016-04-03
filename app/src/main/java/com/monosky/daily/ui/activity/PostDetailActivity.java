package com.monosky.daily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.PostsEntity;

/**
 * 文章详细页，以webview加载
 */
public class PostDetailActivity extends BaseActivity {

    private TextView mTopTitle;
    private WebView mContentWebview;
    private RelativeLayout mBottomReply;
    private RelativeLayout mBottomLike;
    private RelativeLayout mBottomShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        getViews();
        setViews();
    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mContentWebview = (WebView) findViewById(R.id.content_webivew);
        mBottomReply = (RelativeLayout) findViewById(R.id.bottom_reply);
        mBottomLike = (RelativeLayout) findViewById(R.id.bottom_like);
        mBottomShare = (RelativeLayout) findViewById(R.id.bottom_share);
    }

    private void setViews() {
        // 设置webview能执行javaScript脚本
        mContentWebview.getSettings().setJavaScriptEnabled(true);
        mContentWebview.loadUrl("http://moment.douban.com/post/123459/?douban_rec=1");
        mContentWebview.setWebViewClient(new contentWebViewClient());

        mTopTitle.setOnClickListener(contentOnClick);
        mBottomReply.setOnClickListener(contentOnClick);
        mBottomLike.setOnClickListener(contentOnClick);
        mBottomShare.setOnClickListener(contentOnClick);
    }

    private class contentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    View.OnClickListener contentOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.actionbar_title:
                    PostDetailActivity.this.finish();
                    break;
                case R.id.bottom_reply:
                    Intent intent = new Intent(PostDetailActivity.this, PostReplyActivity.class);
                    PostDetailActivity.this.startActivity(intent);
                    break;
                case R.id.bottom_like:
                    break;
                case R.id.bottom_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "测试系统分享");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "选择分享"));
                    break;
                default:
                    break;
            }
        }
    };

    public static void gotoPostDetail(PostsEntity postsEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), PostDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("post", postsEntity);
        BaseApplication.getContext().startActivity(intent);
    }

}
