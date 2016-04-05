package com.monosky.daily.ui.activity.author;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.ProfileData;
import com.monosky.daily.ui.activity.BaseActivity;

import butterknife.Bind;

/**
 * 作者介绍
 */
public class AuthorIntroActivity extends BaseActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.author_intro)
    TextView mAuthorIntro;
    private ProfileData mProfileData;

    @Override
    protected int getLayout() {
        return R.layout.activity_author_intro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProfileData = (ProfileData) getIntent().getSerializableExtra("profileData");
        initViews();
    }

    private void initViews() {
        mToolBar.setTitle(mProfileData.getName());
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mAuthorIntro.setText(mProfileData.getIntro());
    }

    public static void gotoAuthorIntro(ProfileData profileData) {
        Intent intent = new Intent(BaseApplication.getContext(), AuthorIntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("profileData", profileData);
        BaseApplication.getContext().startActivity(intent);

    }
}
