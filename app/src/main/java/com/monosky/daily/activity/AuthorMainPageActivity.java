package com.monosky.daily.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.monosky.daily.ConstData;
import com.monosky.daily.R;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

/**
 * 个人详情
 */
public class AuthorMainPageActivity extends BaseActivity {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private TextView mTopTitle;
    private ImageView mAuthorImg;
    private TextView mAuthorName;
    private TextView mAuthorLike;
    private Button mLogoffBtn;

    private AuthorData mAuthorData;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_author_main_page);

        mAuthorData = (AuthorData) getIntent().getSerializableExtra("authorData");
        type = getIntent().getStringExtra("type");

        getViews();
        setViews();

    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mAuthorImg = (ImageView) findViewById(R.id.author_main_img);
        mAuthorName = (TextView) findViewById(R.id.author_main_name);
        mAuthorLike = (TextView) findViewById(R.id.author_main_like);
        mLogoffBtn = (Button) findViewById(R.id.logoff_btn);
    }

    private void setViews() {
        mTopTitle.setText(getResources().getString(R.string.douban_title));
        imageLoader.displayImage(mAuthorData.getAuthorImg(), mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
        mAuthorName.setText(mAuthorData.getAuthorName());

        if(type.equals(ConstData.MAIN_PAGE_TYPE_OTHER)) {
            mAuthorLike.setVisibility(View.VISIBLE);
            mAuthorLike.setOnClickListener(myOnClick);
            mLogoffBtn.setVisibility(View.GONE);
        } else {
            mAuthorLike.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
                mLogoffBtn.setVisibility(View.VISIBLE);
                mLogoffBtn.setOnClickListener(myOnClick);
            }
        }
        mTopTitle.setOnClickListener(myOnClick);
    }

    View.OnClickListener myOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.actionbar_title:
                    AuthorMainPageActivity.this.finish();
                    break;
                case R.id.author_main_like:
                    Toast.makeText(AuthorMainPageActivity.this, "关注此人", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.logoff_btn:
                    final MaterialDialog materialDialog = new MaterialDialog.Builder(AuthorMainPageActivity.this)
                            .content("正在退出登录，请稍后……")
                            .progress(true, 0)
                            .cancelable(false)
                            .progressIndeterminateStyle(false)
                            .show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            materialDialog.dismiss();
                            SharedPreferencesUtil.getInstance().setValue(ConstData.LOGON_ACCOUNT, "");
                            SharedPreferencesUtil.getInstance().setValue(ConstData.LOGON_PWD, "");
                            Intent intent = new Intent(ConstData.BROADCAST_LOGOFF);
                            AuthorMainPageActivity.this.sendBroadcast(intent);
                            AuthorMainPageActivity.this.finish();
                        }
                    }, 5000);
                    break;
            }
        }
    };

}
