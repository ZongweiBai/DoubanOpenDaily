package com.monosky.daily.ui.activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.BaseActivity;

import butterknife.Bind;

public class AppAboutActivity extends BaseActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.official_btn)
    Button mOfficialBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_app_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolBar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mOfficialBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    public void downDaily(View view) {
        Uri uri = Uri.parse("http://moment.douban.com/app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
