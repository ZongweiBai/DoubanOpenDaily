package com.monosky.daily.ui.activity.author;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.AuthorDetailData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.activity.author.adapter.AuthorAdapter;
import com.monosky.daily.util.ToastUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 作者详情(信息+文章)
 */
public class AuthorActivity extends BaseRefreshActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.author_recycler_view)
    RecyclerView mAuthorRecyclerView;
    private AuthorAdapter mAuthorAdapter;
    private AuthorEntity mAuthorEntity;
    private List<PostEntity> mPostEntities = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int maxId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_author;
    }

    @Override
    protected void initData() {
        mAuthorEntity = (AuthorEntity) getIntent().getSerializableExtra("authorData");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle(getResources().getString(R.string.author_page_title, mAuthorEntity.getName()));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mLayoutManager = new LinearLayoutManager(this);
        mAuthorRecyclerView.setLayoutManager(mLayoutManager);
        mAuthorAdapter = new AuthorAdapter(mAuthorEntity, mPostEntities);
        mAuthorRecyclerView.setAdapter(mAuthorAdapter);
        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mAuthorRecyclerView.addItemDecoration(mItemDecoration);

        // Add scrollListener
        mAuthorRecyclerView.addOnScrollListener(myOnScrollListener);
    }

    /**
     * 设置recycleView的滚动监听
     */
    RecyclerView.OnScrollListener myOnScrollListener = new RecyclerView.OnScrollListener() {
        int currentPosition = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentPosition = mLayoutManager.findLastVisibleItemPosition();
            if (mPostEntities == null || mPostEntities.isEmpty() || mRefreshing || mLoading || !mHasNext) {
                return;
            }
            if (currentPosition + 4 >= mPostEntities.size()) {
                mSwipeRefresh.setRefreshing(true);
                mLoading = true;
                mRequestType = ConstData.REQUEST_LOAD;
                requestPosts();
            }
        }
    };

    @Override
    protected void onRefreshStarted() {
        mRequestType = ConstData.REQUEST_REFRESH;
        requestPosts();
    }

    private void requestPosts() {
        String requestUrl = APIConstData.GetAuthorDetail.replace("{authorId}", mAuthorEntity.getId());
        if (mRequestType.equals(ConstData.REQUEST_LOAD)) {
            requestUrl = APIConstData.GetAuthorDetailMore.replace("{authorId}", mAuthorEntity.getId());
            requestUrl = requestUrl.replace("{maxId}", String.valueOf(maxId));
        }
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        AuthorDetailData authorDetail = JSON.parseObject(t, AuthorDetailData.class);
                        if (mRequestType.equals(ConstData.REQUEST_REFRESH)) {
                            mPositionStart = 0;
                            mPostEntities.clear();
                            mPostEntities.add(null);
                        } else {
                            mPositionStart = mPostEntities.size();
                        }
                        mAuthorEntity = authorDetail.getAuthor();
                        mPostEntities.addAll(authorDetail.getPosts());
                        if (authorDetail.getPosts().size() > 1) {
                            maxId = authorDetail.getPosts().get(authorDetail.getPosts().size() - 1).getId();
                        }
                        if (mPostEntities.size() >= authorDetail.getTotal()) {
                            mHasNext = false;
                            mPostEntities.add(null);
                        }
                        mItemCount = mPostEntities.size() - mPositionStart;
                        mAuthorAdapter.notifyItemRangeChanged(mPositionStart, mItemCount);
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

    public static void gotoAuthorDetail(AuthorEntity authorEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), AuthorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("authorData", authorEntity);
        BaseApplication.getContext().startActivity(intent);
    }
}
