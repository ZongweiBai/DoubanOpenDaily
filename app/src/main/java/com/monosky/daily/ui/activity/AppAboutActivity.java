package com.monosky.daily.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.monosky.daily.R;

public class AppAboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_about);

        TextView mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mTopTitle.setText(getResources().getString(R.string.app_name));
        mTopTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppAboutActivity.this.finish();
            }
        });
    }

    public void downDaily(View view) {
        Uri uri = Uri.parse("http://moment.douban.com/app/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
