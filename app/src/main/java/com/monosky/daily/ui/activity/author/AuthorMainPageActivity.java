package com.monosky.daily.ui.activity.author;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.ProfileData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.util.SharedPreferencesUtil;
import com.monosky.daily.util.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者主页
 */
public class AuthorMainPageActivity extends BaseRefreshActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.author_main_img)
    CircleImageView mAuthorMainImg;
    @Bind(R.id.author_main_name)
    TextView mAuthorMainName;
    @Bind(R.id.loc_name)
    TextView mLocName;
    @Bind(R.id.author_main_like)
    TextView mAuthorMainLike;
    @Bind(R.id.author_main_intro)
    TextView mAuthorMainIntro;
    @Bind(R.id.following_count)
    TextView mFollowingCount;
    @Bind(R.id.follower_count)
    TextView mFollowerCount;
    @Bind(R.id.status_count)
    TextView mStatusCount;
    @Bind(R.id.note_count)
    TextView mNoteCount;
    @Bind(R.id.album_count)
    TextView mAlbumCount;
    @Bind(R.id.review_count)
    TextView mReviewCount;
    @Bind(R.id.movie_collect_count)
    TextView mMovieCollectCount;
    @Bind(R.id.movie_wish_count)
    TextView mMovieWishCount;
    @Bind(R.id.movie_do_count)
    TextView mMovieDoCount;
    @Bind(R.id.book_collect_count)
    TextView mBookCollectCount;
    @Bind(R.id.book_wish_count)
    TextView mBookWishCount;
    @Bind(R.id.book_do_count)
    TextView mBookDoCount;
    @Bind(R.id.music_collect_count)
    TextView mMusicCollectCount;
    @Bind(R.id.music_wish_count)
    TextView mMusicWishCount;
    @Bind(R.id.music_do_count)
    TextView mMusicDoCount;
    @Bind(R.id.logoff_btn)
    Button mLogoffBtn;
    @Bind(R.id.contentScrollView)
    ScrollView mContentScrollView;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private AuthorEntity mAuthorEntity;
    private String type;
    private ProfileData mProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_author_main_page;
    }

    @Override
    protected void initData() {
        mAuthorEntity = (AuthorEntity) getIntent().getSerializableExtra("authorData");
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void initViews() {
        mContentScrollView.setVisibility(View.INVISIBLE);
        mToolBar.setTitle(getResources().getString(R.string.douban_title));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onRefreshStarted() {
        String requestUrl = APIConstData.GetProfile.replace("{authorId}", mAuthorEntity.getUid());
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        mProfileData = JSON.parseObject(t, ProfileData.class);
                        setViewDate();
                        ToastUtils.showShort(BaseApplication.getContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(BaseApplication.getContext(), "请求出错");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mRefreshing = false;
                        mLoading = false;
                        mSwipeRefresh.setRefreshing(false);
                    }
                })
                .encoding("UTF-8")
                .doTask();
    }

    private void setViewDate() {
        imageLoader.displayImage(mProfileData.getAvatar(), mAuthorMainImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
        mAuthorMainName.setText(mProfileData.getName());
        if (TextUtils.isEmpty(mProfileData.getLoc_name())) {
            mLocName.setVisibility(View.GONE);
        } else {
            mLocName.setText(mProfileData.getLoc_name());
            mLocName.setTextColor(ContextCompat.getColor(this, R.color.content_label_readed));
        }

        mFollowingCount.setText(mProfileData.getFollowing_count());
        mFollowingCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mFollowingCount.setTag(mProfileData.getFollowing_url());
        mFollowingCount.setOnClickListener(myOnClick);

        mFollowerCount.setText(mProfileData.getFollower_count());
        mFollowerCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mFollowerCount.setTag(mProfileData.getFollower_url());
        mFollowerCount.setOnClickListener(myOnClick);

        mStatusCount.setText(mProfileData.getStatus_count());
        mStatusCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mStatusCount.setTag(mProfileData.getStatus_url());
        mStatusCount.setOnClickListener(myOnClick);

        mNoteCount.setText(mProfileData.getNote_count());
        mNoteCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mNoteCount.setTag(mProfileData.getNote_url());
        mNoteCount.setOnClickListener(myOnClick);

        mAlbumCount.setText(mProfileData.getAlbum_count());
        mAlbumCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mAlbumCount.setTag(mProfileData.getAlbum_url());
        mAlbumCount.setOnClickListener(myOnClick);

        mReviewCount.setText(mProfileData.getReview_count());
        mReviewCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mReviewCount.setTag(mProfileData.getReview_url());
        mReviewCount.setOnClickListener(myOnClick);

        mMovieCollectCount.setText(mProfileData.getMovie_collect_count());
        mMovieCollectCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMovieCollectCount.setTag(mProfileData.getMovie_collect_url());
        mMovieCollectCount.setOnClickListener(myOnClick);

        mMovieDoCount.setText(mProfileData.getMovie_do_count());
        mMovieDoCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMovieDoCount.setTag(mProfileData.getMovie_do_url());
        mMusicDoCount.setOnClickListener(myOnClick);

        mMovieWishCount.setText(mProfileData.getMovie_wish_count());
        mMovieWishCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMovieWishCount.setTag(mProfileData.getMovie_wish_url());
        mMovieWishCount.setOnClickListener(myOnClick);

        mMusicCollectCount.setText(mProfileData.getMusic_collect_count());
        mMusicCollectCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMusicCollectCount.setTag(mProfileData.getMusic_collect_url());
        mMusicCollectCount.setOnClickListener(myOnClick);

        mMusicDoCount.setText(mProfileData.getMusic_do_count());
        mMusicDoCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMusicDoCount.setTag(mProfileData.getMusic_do_url());
        mMusicDoCount.setOnClickListener(myOnClick);

        mMusicWishCount.setText(mProfileData.getMusic_wish_count());
        mMusicWishCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mMusicWishCount.setTag(mProfileData.getMusic_wish_url());
        mMusicWishCount.setOnClickListener(myOnClick);

        mBookCollectCount.setText(mProfileData.getBook_collect_count());
        mBookCollectCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mBookCollectCount.setTag(mProfileData.getBook_collect_url());
        mBookCollectCount.setOnClickListener(myOnClick);

        mBookDoCount.setText(mProfileData.getBook_do_count());
        mBookDoCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mBookDoCount.setTag(mProfileData.getBook_do_url());
        mBookDoCount.setOnClickListener(myOnClick);

        mBookWishCount.setText(mProfileData.getBook_wish_count());
        mBookWishCount.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mBookWishCount.setTag(mProfileData.getBook_wish_url());
        mBookWishCount.setOnClickListener(myOnClick);

        mLogoffBtn.setVisibility(View.GONE);
        if ((ConstData.MAIN_PAGE_TYPE_SELF).equals(type)) {
            mAuthorMainLike.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(SharedPreferencesUtil.getInstance().getValue(ConstData.LOGON_ACCOUNT))) {
                mLogoffBtn.setVisibility(View.VISIBLE);
                mLogoffBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
                mLogoffBtn.setOnClickListener(myOnClick);
            }
        } else {
            mAuthorMainLike.setTextColor(ContextCompat.getColor(this, R.color.white));
            if (mProfileData.isFollowed()) {
                mAuthorMainLike.setText(getResources().getString(R.string.author_main_liked));
            } else {
                mAuthorMainLike.setOnClickListener(myOnClick);
            }
        }
        mAuthorMainIntro.setTextColor(ContextCompat.getColor(this, R.color.main_color));
        mAuthorMainIntro.setOnClickListener(myOnClick);

        mContentScrollView.setVisibility(View.VISIBLE);
    }

    View.OnClickListener myOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
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
                case R.id.author_main_intro:
                    AuthorIntroActivity.gotoAuthorIntro(mProfileData);
                    break;
                default:
                    String redirectUrl = String.valueOf(view.getTag());
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri url = Uri.parse(redirectUrl);
                    intent.setData(url);
                    startActivity(intent);
                    break;
            }
        }
    };

    public static void gotoAuthorMain(AuthorEntity authorEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), AuthorMainPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("authorData", authorEntity);
        BaseApplication.getContext().startActivity(intent);
    }
}
