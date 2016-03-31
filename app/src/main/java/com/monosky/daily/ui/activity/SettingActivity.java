package com.monosky.daily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.R;
import com.monosky.daily.util.SharedPreferencesUtil;

/**
 * 设置界面
 */
public class SettingActivity extends BaseActivity {

    private TextView mTopTitle;
    private RelativeLayout mFontSizeRl;
    private TextView mFontSizeValue;
    private RelativeLayout mInfoPushRl;
    private CheckBox mInfoPushCheck;
    private TextView mInfoPushValue;
    private RelativeLayout mOfflineRl;
    private CheckBox mOfflineCheck;
    private TextView mOfflineValue;
    private RelativeLayout mAppUpdateRl;
    private TextView mAppUpdateValue;
    private RelativeLayout mFeedbackRl;
    private RelativeLayout mShareAccountRl;
    private RelativeLayout mOpenSrcRl;
    private RelativeLayout mAppAboutRl;
    private TextView mAppAboutValue;

    private int mCheckedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // 获取控件
        getViews();

        // 设置控件
        setViews();
    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mFontSizeRl = (RelativeLayout) findViewById(R.id.font_size_rl);
        mFontSizeValue = (TextView) findViewById(R.id.font_size_value);
        mInfoPushRl = (RelativeLayout) findViewById(R.id.info_push_rl);
        mInfoPushCheck = (CheckBox) findViewById(R.id.info_push_check);
        mInfoPushValue = (TextView) findViewById(R.id.info_push_value);
        mOfflineRl = (RelativeLayout) findViewById(R.id.offline_rl);
        mOfflineCheck = (CheckBox) findViewById(R.id.offline_check);
        mOfflineValue = (TextView) findViewById(R.id.offline_value);
        mAppUpdateRl = (RelativeLayout) findViewById(R.id.app_update_rl);
        mAppUpdateValue = (TextView) findViewById(R.id.app_update_value);
        mFeedbackRl = (RelativeLayout) findViewById(R.id.feedback_rl);
        mShareAccountRl = (RelativeLayout) findViewById(R.id.share_setting_rl);
        mOpenSrcRl = (RelativeLayout) findViewById(R.id.open_src_rl);
        mAppAboutRl = (RelativeLayout) findViewById(R.id.app_about_rl);
        mAppAboutValue = (TextView) findViewById(R.id.app_about_value);
    }

    private void setViews() {
        mTopTitle.setText(getResources().getString(R.string.settings));

        mInfoPushCheck.setChecked(true);
        mOfflineCheck.setChecked(true);

        mTopTitle.setOnClickListener(onClickListener);
        mFontSizeRl.setOnClickListener(onClickListener);
        mInfoPushRl.setOnClickListener(onClickListener);
        mInfoPushCheck.setOnClickListener(onClickListener);
        mOfflineRl.setOnClickListener(onClickListener);
        mOfflineCheck.setOnClickListener(onClickListener);
        mAppUpdateRl.setOnClickListener(onClickListener);
        mFeedbackRl.setOnClickListener(onClickListener);
        mShareAccountRl.setOnClickListener(onClickListener);
        mOpenSrcRl.setOnClickListener(onClickListener);
        mAppAboutRl.setOnClickListener(onClickListener);
    }

    /**
     * 自定义点击事件
     */
    public View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.actionbar_title:
                    SettingActivity.this.finish();
                    break;
                case R.id.font_size_rl:
                    showFontSizeSettingDialog();
                    break;
                case R.id.info_push_rl:
                    if(mInfoPushCheck.isChecked()) {
                        mInfoPushCheck.setChecked(false);
                        mInfoPushValue.setText(getResources().getString(R.string.info_push_no));
                    } else {
                        mInfoPushCheck.setChecked(true);
                        mInfoPushValue.setText(getResources().getString(R.string.info_push_yes));
                    }
                    break;
                case R.id.info_push_check:
                    if(mInfoPushCheck.isChecked()) {
                        mInfoPushCheck.setChecked(true);
                        mInfoPushValue.setText(getResources().getString(R.string.info_push_yes));
                    } else {
                        mInfoPushCheck.setChecked(false);
                        mInfoPushValue.setText(getResources().getString(R.string.info_push_no));
                    }
                    break;
                case R.id.offline_rl:
                    if(mOfflineCheck.isChecked()) {
                        mOfflineCheck.setChecked(false);
                        mOfflineValue.setText(getResources().getString(R.string.offline_setting_no));
                    } else {
                        mOfflineCheck.setChecked(true);
                        mOfflineValue.setText(getResources().getString(R.string.offline_setting_yes));
                    }
                    break;
                case R.id.offline_check:
                    if(mOfflineCheck.isChecked()) {
                        mOfflineCheck.setChecked(true);
                        mOfflineValue.setText(getResources().getString(R.string.offline_setting_yes));
                    } else {
                        mOfflineCheck.setChecked(false);
                        mOfflineValue.setText(getResources().getString(R.string.offline_setting_no));
                    }
                    break;
                case R.id.app_about_rl:
                    Intent intent = new Intent(SettingActivity.this, AppAboutActivity.class);
                    SettingActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 弹出正文字号dialog
     */
    private void showFontSizeSettingDialog() {
        String checkedIdStr = SharedPreferencesUtil.getInstance().getValue(ConstData.FONT_SIZE_SETTING);
        int checkedId = TextUtils.isEmpty(checkedIdStr) ? 0 : Integer.parseInt(checkedIdStr);
        new MaterialDialog.Builder(this)
                .title(R.string.font_size)
                .items(R.array.font_size)
                .itemsCallbackSingleChoice(checkedId, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        SharedPreferencesUtil.getInstance().setValue(ConstData.FONT_SIZE_SETTING, String.valueOf(which));
                        mFontSizeValue.setText(text);
                        return true;
                    }
                })
                .cancelable(false)
                .positiveText(R.string.sure)
                .show();
    }

}
