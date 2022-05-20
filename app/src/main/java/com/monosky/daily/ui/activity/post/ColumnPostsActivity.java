package com.monosky.daily.ui.activity.post;

import android.content.Intent;

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
import com.monosky.daily.module.ColumnPostData;
import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.activity.post.adapter.ColumnPostsAdapter;
import com.monosky.daily.util.ToastUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 栏目文章列表
 */
public class ColumnPostsActivity extends BaseRefreshActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.column_recycler_view)
    RecyclerView mColumnRecyclerView;
    private ColumnEntity mColumnEntity;
    private List<PostEntity> mPostEntityList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private ColumnPostsAdapter mColumnPostsAdapter;
    private int maxId = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_column_posts;
    }

    @Override
    protected void initData() {
        mColumnEntity = (ColumnEntity) getIntent().getSerializableExtra("column");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle(mColumnEntity.getName());
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mLayoutManager = new LinearLayoutManager(this);
        mColumnRecyclerView.setLayoutManager(mLayoutManager);
        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mColumnRecyclerView.addItemDecoration(mItemDecoration);
        mColumnRecyclerView.addOnScrollListener(mOnScrollListener);

        mColumnPostsAdapter = new ColumnPostsAdapter(mColumnEntity, mPostEntityList);
        mColumnRecyclerView.setAdapter(mColumnPostsAdapter);
    }

    @Override
    protected void onRefreshStarted() {
        maxId = 0;
        requestPosts();
    }

    private void requestPosts() {
        String requestUrl = APIConstData.GetColumnPosts.replace("{maxId}", String.valueOf(maxId));
        requestUrl = requestUrl.replace("{columnId}", String.valueOf(mColumnEntity.getId()));
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        ColumnPostData postData = JSON.parseObject(t, ColumnPostData.class);
                        if (mRequestType.equals(ConstData.REQUEST_REFRESH)) {
                            mPostEntityList.clear();
                            mPostEntityList.add(null);
                            mPositionStart = 0;
                        } else {
                            mPositionStart = mPostEntityList.size();
                        }
                        mPostEntityList.addAll(postData.getPosts());
                        maxId = mPostEntityList.get(mPostEntityList.size() - 1).getId();
                        if (mPostEntityList.size() + 1 >= postData.getTotal()) {
                            mHasNext = false;
                        }
                        mItemCount = mPostEntityList.size() - mPositionStart;
                        mColumnPostsAdapter.notifyItemRangeChanged(mPositionStart, mItemCount);
                        ToastUtils.showShort(ColumnPostsActivity.this, "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(ColumnPostsActivity.this, "请求出错");
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

    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        int currentPosition = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            currentPosition = mLayoutManager.findLastVisibleItemPosition();
            if (mPostEntityList == null || mPostEntityList.isEmpty() || mRefreshing || mLoading || !mHasNext) {
                return;
            }
            if (currentPosition + 4 >= mPostEntityList.size()) {
                mSwipeRefresh.setRefreshing(true);
                mLoading = true;
                mRequestType = ConstData.REQUEST_LOAD;
                requestPosts();
            }
        }
    };

    public static void gotoColumnPosts(ColumnEntity columnEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), ColumnPostsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("column", columnEntity);
        BaseApplication.getContext().startActivity(intent);
    }

}
