package com.monosky.daily.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.monosky.daily.ConstData;
import com.monosky.daily.R;
import com.monosky.daily.fragment.CatalogFragment;
import com.monosky.daily.fragment.FavoriteFragment;
import com.monosky.daily.fragment.HistoryFragment;
import com.monosky.daily.fragment.HotAuthorFragment;
import com.monosky.daily.fragment.TodayFragment;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.test.GenerateTestDatas;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.util.SharedPreferencesUtil;
import com.monosky.daily.view.materialMenu.MaterialMenuDrawable;
import com.monosky.daily.view.materialMenu.MaterialMenuView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * APP主页面
 */
public class MainActivity extends BaseActivity {

    public static MainActivity mainActivity;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    private MaterialMenuView materialMenu;
    private boolean direction;
    protected TextView mTopTilte;
    protected ImageView mTopSearch;
    protected ProgressBar mTopProgress;
    private DrawerLayout mDrawerLayout = null;
    private String mainPageTitle;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment currentFragment;   // 当前显示的Fragment
    private TodayFragment mTodayFragment;
    private HistoryFragment mHistoryFragment;
    private HotAuthorFragment mHotAuthorFragment;
    private CatalogFragment mCatalogFragment;
    private FavoriteFragment mFavoriteFragment;
    private LinearLayout mDrawerLoginLayout;
    private ImageView mDrawerAuthorImg;
    private TextView mDrawerLogin;
    private TextView mDrawerToday;
    private TextView mDrawerHistory;
    private TextView mDrawerHotAuthor;
    private TextView mDrawerCatalog;
    private TextView mDrawerFavorite;
    private TextView mDrawerSetting;
    private LinearLayout mDrawerNight;
    private LinearLayout mDrawerOffline;
    private LinearLayout mDrawerBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        materialMenu = (MaterialMenuView) findViewById(R.id.action_bar_menu);
        mainPageTitle = getResources().getString(R.string.title_today);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTopProgress = (ProgressBar) findViewById(R.id.menu_progress_bar);
        mTopSearch = (ImageView) findViewById(R.id.menu_search);
        mTopTilte = (TextView) findViewById(R.id.menu_title);
        materialMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开抽屉
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mTopTilte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开抽屉
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });
        mTopSearch.setVisibility(View.VISIBLE);

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mTopTilte.setText(mainPageTitle);
                mTopSearch.setVisibility(View.VISIBLE);
                direction = false;
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mTopTilte.setText(getResources().getString(R.string.app_name));
                mTopSearch.setVisibility(View.GONE);
                direction = true;
                super.onDrawerOpened(drawerView);
            }
        });

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

    /**
     * 设置菜单的点击事件
     */
    private void setDrawerView() {
        mDrawerLoginLayout = (LinearLayout) findViewById(R.id.drawer_login_layout);
        mDrawerAuthorImg = (ImageView) findViewById(R.id.drawer_author_img);
        mDrawerLogin = (TextView) findViewById(R.id.drawer_login);
        mDrawerToday = (TextView) findViewById(R.id.drawer_today);
        mDrawerHistory = (TextView) findViewById(R.id.drawer_history);
        mDrawerHotAuthor = (TextView) findViewById(R.id.drawer_hot_author);
        mDrawerCatalog = (TextView) findViewById(R.id.drawer_catalog);
        mDrawerFavorite = (TextView) findViewById(R.id.drawer_favorite);
        mDrawerSetting = (TextView) findViewById(R.id.drawer_setting);
        mDrawerNight = (LinearLayout) findViewById(R.id.drawer_night);
        mDrawerOffline = (LinearLayout) findViewById(R.id.drawer_offline);
        mDrawerBottomLayout = (LinearLayout) findViewById(R.id.drawer_bottom_layout);

        if(!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
            AuthorData authorData = GenerateTestDatas.getLogonAuthor();
            imageLoader.displayImage(authorData.getAuthorImg(), mDrawerAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
            mDrawerLogin.setText(authorData.getAuthorName());
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
                    if(TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
                        Intent intent = new Intent(mainActivity, LogonActivity.class);
                        mainActivity.startActivity(intent);
                    } else {
                        AuthorData authorData = GenerateTestDatas.getLogonAuthor();
                        Intent intent = new Intent(mainActivity, AuthorMainPageActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("authorData", authorData);
                        intent.putExtras(bundle);
                        intent.putExtra("type", ConstData.MAIN_PAGE_TYPE_SELF);
                        mainActivity.startActivity(intent);
                    }
                    break;
                case R.id.drawer_today:
                    mainPageTitle = getResources().getString(R.string.title_today);
                    if(!fragmentList.contains(mTodayFragment)) {
                        trx.add(R.id.main_container, mTodayFragment);
                    }
                    trx.show(mTodayFragment).commit();
                    currentFragment = mTodayFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_history:
                    mainPageTitle = getResources().getString(R.string.history);
                    if(!fragmentList.contains(mHistoryFragment)) {
                        fragmentList.add(mHistoryFragment);
                        trx.add(R.id.main_container, mHistoryFragment);
                    }
                    trx.show(mHistoryFragment).commit();
                    currentFragment = mHistoryFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_hot_author:
                    mainPageTitle = getResources().getString(R.string.hot_author);
                    if(!fragmentList.contains(mHotAuthorFragment)) {
                        fragmentList.add(mHotAuthorFragment);
                        trx.add(R.id.main_container, mHotAuthorFragment);
                    }
                    trx.show(mHotAuthorFragment).commit();
                    currentFragment = mHotAuthorFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_catalog:
                    mainPageTitle = getResources().getString(R.string.catalog);
                    if(!fragmentList.contains(mCatalogFragment)) {
                        fragmentList.add(mCatalogFragment);
                        trx.add(R.id.main_container, mCatalogFragment);
                    }
                    trx.show(mCatalogFragment).commit();
                    currentFragment = mCatalogFragment;
                    mDrawerLayout.closeDrawers();
                    break;
                case R.id.drawer_favorite:
                    mainPageTitle = getResources().getString(R.string.favorite);
                    if(!fragmentList.contains(mFavoriteFragment)) {
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
        if(!fragmentList.contains(mHistoryFragment)) {
            fragmentList.add(mHistoryFragment);
            trx.add(R.id.main_container, mHistoryFragment);
        }
        trx.show(mHistoryFragment).commit();
        currentFragment = mHistoryFragment;
        mTopTilte.setText(mainPageTitle);
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
            if((ConstData.BROADCAST_LOGON).equals(intent.getAction())) {
                mDrawerLayout.closeDrawers();
                AuthorData authorData = GenerateTestDatas.getLogonAuthor();
                imageLoader.displayImage(authorData.getAuthorImg(), mDrawerAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
                mDrawerLogin.setText(authorData.getAuthorName());
            }
        }
    };

    BroadcastReceiver logoffBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if((ConstData.BROADCAST_LOGOFF).equals(intent.getAction())) {
                mDrawerLayout.closeDrawers();
                mDrawerAuthorImg.setImageResource(R.mipmap.ic_default_avatar_light);
                mDrawerLogin.setText(getResources().getString(R.string.un_login));
            }
        }
    };
}
