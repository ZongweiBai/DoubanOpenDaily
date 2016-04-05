package com.monosky.daily.ui.activity.init;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.R;
import com.monosky.daily.ui.activity.BaseActivity;
import com.monosky.daily.util.SharedPreferencesUtil;

public class LogonActivity extends BaseActivity {

    private EditText mLogonAccount;
    private EditText mLogonPwd;
    private Button mLogonBtn;
    private TextView mRegister;
    private TextView mForgetPwd;

    @Override
    protected int getLayout() {
        return R.layout.activity_logon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getViews();
        setViews();
    }

    private void getViews() {
        mLogonAccount = (EditText) findViewById(R.id.logon_account);
        mLogonPwd = (EditText) findViewById(R.id.logon_pwd);
        mLogonBtn = (Button) findViewById(R.id.logon_btn);
        mRegister = (TextView) findViewById(R.id.register_account);
        mForgetPwd = (TextView) findViewById(R.id.pwd_forget);
    }

    private void setViews() {
        mLogonBtn.setOnClickListener(logonClickListener);
        mRegister.setOnClickListener(logonClickListener);
        mForgetPwd.setOnClickListener(logonClickListener);
    }

    View.OnClickListener logonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeInputMethod(v);
            switch (v.getId()) {
                case R.id.logon_btn:
                    logonApp();
                    break;
                case R.id.register_account:
                    otherOperate(ConstData.LOGON_TYPE_REGISTER);
                    break;
                case R.id.pwd_forget:
                    otherOperate(ConstData.LOGON_TYPE_FORGET);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 其他操作
     */
    private void otherOperate(String type) {
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    /**
     * 登录
     */
    private void logonApp() {
        String account = String.valueOf(mLogonAccount.getText());
        String pwd = String.valueOf(mLogonPwd.getText());
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(account.trim())) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd.trim())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (account.equals(ConstData.ACCOUNT) && pwd.equals(ConstData.PASSWORD)) {
            final MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                    .content("正在登录，请稍后……")
                    .progress(true, 0)
                    .cancelable(false)
                    .progressIndeterminateStyle(false)
                    .show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    materialDialog.dismiss();
                    Toast.makeText(LogonActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConstData.BROADCAST_LOGON);
                    LogonActivity.this.sendBroadcast(intent);
                    LogonActivity.this.finish();
                }
            }, 5000);
            SharedPreferencesUtil.getInstance().setValue(ConstData.LOGON_ACCOUNT, account);
            SharedPreferencesUtil.getInstance().setValue(ConstData.LOGON_PWD, pwd);
        } else {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 关闭软键盘
     *
     * @param view
     */
    protected void closeInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
