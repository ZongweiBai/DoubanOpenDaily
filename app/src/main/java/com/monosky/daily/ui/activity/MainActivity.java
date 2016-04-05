package com.monosky.daily.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.test.GenerateTestDatas;
import com.monosky.daily.ui.activity.author.AuthorMainPageActivity;
import com.monosky.daily.ui.activity.init.LogonActivity;
import com.monosky.daily.ui.activity.setting.SettingActivity;
import com.monosky.daily.ui.fragment.CatalogFragment;
import com.monosky.daily.ui.fragment.FavoriteFragment;
import com.monosky.daily.ui.fragment.HistoryFragment;
import com.monosky.daily.ui.fragment.HotAuthorFragment;
import com.monosky.daily.ui.fragment.TodayFragment;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.util.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * APP主页面
 */
public class MainActivity extends BaseActivity {

    public static MainActivity mainActivity;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.main_container)
    RelativeLayout mMainContainer;
    @Bind(R.id.drawer_author_img)
    CircleImageView mDrawerAuthorImg;
    @Bind(R.id.drawer_login)
    TextView mDrawerLogin;
    @Bind(R.id.drawer_login_layout)
    LinearLayout mDrawerLoginLayout;
    @Bind(R.id.drawer_today)
    TextView mDrawerToday;
    @Bind(R.id.drawer_history)
    TextView mDrawerHistory;
    @Bind(R.id.drawer_hot_author)
    TextView mDrawerHotAuthor;
    @Bind(R.id.drawer_catalog)
    TextView mDrawerCatalog;
    @Bind(R.id.drawer_favorite)
    TextView mDrawerFavorite;
    @Bind(R.id.drawer_setting)
    TextView mDrawerSetting;
    @Bind(R.id.drawer_night)
    LinearLayout mDrawerNight;
    @Bind(R.id.drawer_offline)
    LinearLayout mDrawerOffline;
    @Bind(R.id.drawer_bottom_layout)
    LinearLayout mDrawerBottomLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    RelativeLayout mLeftDrawer;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private String mainPageTitle;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment currentFragment;   // 当前显示的Fragment
    private TodayFragment mTodayFragment;
    private HistoryFragment mHistoryFragment;
    private HotAuthorFragment mHotAuthorFragment;
    private CatalogFragment mCatalogFragment;
    private FavoriteFragment mFavoriteFragment;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mainActivity = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolbar();
        setDrawerView();

        mTodayFragment = new TodayFragment();
        mHistoryFragment = new HistoryFragment();
        mHotAuthorFragment = new HotAuthorFragment();
        mCatalogFragment = new CatalogFragment();
        mFavoriteFragment = new FavoriteFragment();
        // 添加显示第一个fragment
        fragmentList.add(mTodayFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, mTodayFragment).show(mTodayFragment).commit();
        currentFragment = mTodayFragment;

        // 注册广播
        IntentFilter intentFilterOn = new IntentFilter(ConstData.BROADCAST_LOGON);
        registerReceiver(logonBroadCastReceiver, intentFilterOn);

        IntentFilter intentFilterOff = new IntentFilter(ConstData.BROADCAST_LOGOFF);
        registerReceiver(logoffBroadCastReceiver, intentFilterOff);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 设置toolbar
     */
    private void initToolbar() {
        mainPageTitle = getResources().getString(R.string.title_today);
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_today);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(mainPageTitle);
                }
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.app_name);
                }
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 设置左侧菜单的点击事件
     */
    private void setDrawerView() {

        if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
            AuthorEntity authorData = GenerateTestDatas.getLogonAuthor();
            imageLoader.displayImage(authorData.getAvatar(), mDrawerAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
            mDrawerLogin.setText(authorData.getName());
        }

        mDrawerLoginLayout.setOnClickListener(onClick);
        mDrawerToday.setOnClickListener(onClick);
        mDrawerHistory.setOnClickListener(onClick);
        mDrawerHotAuthor.setOnClickListener(onClick);
        mDrawerCatalog.setOnClickListener(onClick);
        mDrawerFavorite.setOnClickListener(onClick);
        mDrawerSetting.setOnClickListener(onClick);
        mDrawerNight.setOnClickListener(onClick);
        mDrawerOffline.setOnClickListener(onClick);
        mDrawerBottomLayout.setOnClickListener(onClick);
    }

    /**
     * 自定义菜单的点击事件
     */
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(currentFragment);
            switch (v.getId()) {
                case R.id.drawer_login_layout:
                    if (TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
                        Intent intent = new Intent(mainActivity, LogonActivity.class);
                        mainActivity.startActivity(intent);
                    } else {
                        AuthorEntity authorData = GenerateTestDatas.getLogonAuthor();
                        Intent intent = new Intent(mainActivity, AuthorMainPageActivity.class);
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("authorData", authorData);
                        intent.putExtras(bundle);
                        intent.putExtra("type", ConstData.MAIN_PAGE_TYPE_SELF);
                        mainActivity.startActivity(intent);
                    }
                    break;
                case R.id.drawer_today:
                    mainPageTitle = getResources().getString(R.string.title_today);
                    if (!fragmentList.contains(mTodayFragment)) {
                        trx.add(R.id.main_container, mTodayFragment);
                    }
                    trx.show(mTodayFragment).commit();
                    currentFragment = mTodayFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_history:
                    mainPageTitle = getResources().getString(R.string.history);
                    if (!fragmentList.contains(mHistoryFragment)) {
                        fragmentList.add(mHistoryFragment);
                        trx.add(R.id.main_container, mHistoryFragment);
                    }
                    trx.show(mHistoryFragment).commit();
                    currentFragment = mHistoryFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_hot_author:
                    mainPageTitle = getResources().getString(R.string.hot_author);
                    if (!fragmentList.contains(mHotAuthorFragment)) {
                        fragmentList.add(mHotAuthorFragment);
                        trx.add(R.id.main_container, mHotAuthorFragment);
                    }
                    trx.show(mHotAuthorFragment).commit();
                    currentFragment = mHotAuthorFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_catalog:
                    mainPageTitle = getResources().getString(R.string.catalog);
                    if (!fragmentList.contains(mCatalogFragment)) {
                        fragmentList.add(mCatalogFragment);
                        trx.add(R.id.main_container, mCatalogFragment);
                    }
                    trx.show(mCatalogFragment).commit();
                    currentFragment = mCatalogFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_favorite:
                    mainPageTitle = getResources().getString(R.string.favorite);
                    if (!fragmentList.contains(mFavoriteFragment)) {
                        fragmentList.add(mFavoriteFragment);
                        trx.add(R.id.main_container, mFavoriteFragment);
                    }
                    trx.show(mFavoriteFragment).commit();
                    currentFragment = mFavoriteFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_setting:
                    trx.show(currentFragment);
                    Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(settingIntent);
                    break;
                case R.id.drawer_night:
                    trx.show(currentFragment);
                    Toast.makeText(MainActivity.this, "开启夜间模式", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.drawer_offline:
                    trx.show(currentFragment);
                    Toast.makeText(MainActivity.this, "开始离线", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    trx.show(currentFragment);
                    break;
            }
        }
    };

    /**
     * 点击到往期内容
     */
    public void clickHistory() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(currentFragment);
        mainPageTitle = getResources().getString(R.string.history);
        if (!fragmentList.contains(mHistoryFragment)) {
            fragmentList.add(mHistoryFragment);
            trx.add(R.id.main_container, mHistoryFragment);
        }
        trx.show(mHistoryFragment).commit();
        currentFragment = mHistoryFragment;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mainPageTitle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(logonBroadCastReceiver);
        unregisterReceiver(logoffBroadCastReceiver);
    }

    BroadcastReceiver logonBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((ConstData.BROADCAST_LOGON).equals(intent.getAction())) {
                mDrawerLayout.closeDrawers();
                AuthorEntity authorData = GenerateTestDatas.getLogonAuthor();
                imageLoader.displayImage(authorData.getAvatar(), mDrawerAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
                mDrawerLogin.setText(authorData.getName());
            }
        }
    };

    BroadcastReceiver logoffBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ((ConstData.BROADCAST_LOGOFF).equals(intent.getAction())) {
                mDrawerLayout.closeDrawers();
                mDrawerAuthorImg.setImageResource(R.mipmap.ic_default_avatar_light);
                mDrawerLogin.setText(getResources().getString(R.string.un_login));
            }
        }
    };
}
