package com.monosky.daily.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.monosky.daily.constant.ConstData;
import com.monosky.daily.R;

/**
 * webview
 */
public class WebviewActivity extends BaseActivity {

    private TextView mTopTitle;
    private WebView mContentWebview;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        type = getIntent().getStringExtra("type");

        getViews();
        setViews();
    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mContentWebview = (WebView) findViewById(R.id.content_webivew);
    }

    private void setViews() {
        // 设置webview能执行javaScript脚本
        mContentWebview.getSettings().setJavaScriptEnabled(true);
        if((ConstData.LOGON_TYPE_REGISTER).equals(type)) {
            mContentWebview.loadUrl("https://accounts.douban.com/register");
            mTopTitle.setText(getResources().getString(R.string.register_account));
        } else if((ConstData.LOGON_TYPE_FORGET).equals(type)) {
            mContentWebview.loadUrl("https://accounts.douban.com/resetpassword");
            mTopTitle.setText(getResources().getString(R.string.pwd_forget));
        }
        mContentWebview.setWebViewClient(new contentWebViewClient());

        mTopTitle.setOnClickListener(contentOnClick);
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
                    WebviewActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

}
