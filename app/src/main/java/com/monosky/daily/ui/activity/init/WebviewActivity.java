package com.monosky.daily.ui.activity.init;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.widget.Toolbar;

import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.ui.activity.BaseActivity;

import butterknife.Bind;

/**
 * webview
 */
public class WebviewActivity extends BaseActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.content_webivew)
    WebView mContentWebivew;
    private String type;

    @Override
    protected int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");

        initViews();
    }

    private void initViews() {
        // 设置webview能执行javaScript脚本
        mContentWebivew.getSettings().setJavaScriptEnabled(true);
        if ((ConstData.LOGON_TYPE_REGISTER).equals(type)) {
            mContentWebivew.loadUrl("https://accounts.douban.com/register");
            mToolBar.setTitle(getResources().getString(R.string.register_account));
        } else if ((ConstData.LOGON_TYPE_FORGET).equals(type)) {
            mContentWebivew.loadUrl("https://accounts.douban.com/resetpassword");
            mToolBar.setTitle(getResources().getString(R.string.pwd_forget));
        }
        mContentWebivew.setWebViewClient(new contentWebViewClient());

        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private class contentWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
