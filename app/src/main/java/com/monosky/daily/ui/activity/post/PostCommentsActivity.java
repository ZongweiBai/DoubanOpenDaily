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
import com.monosky.daily.module.CommentData;
import com.monosky.daily.module.entity.CommentEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.activity.post.adapter.PostCommentsAdapter;
import com.monosky.daily.util.ToastUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 文章评论
 */
public class PostCommentsActivity extends BaseRefreshActivity {

    public static PostCommentsActivity mContentReplyActivity;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.comments_recycler_view)
    RecyclerView mCommentsRecyclerView;
    private PostEntity mPostEntity;
    private PostCommentsAdapter mPostCommentsAdapter;
    private List<CommentEntity> mCommentEntities = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    private Integer hotCommentsCount = 0;   // 热门评论数量
    private String maxId = "0";

    @Override
    protected int getLayout() {
        return R.layout.activity_post_comments;
    }

    @Override
    protected void initData() {
        mPostEntity = (PostEntity) getIntent().getSerializableExtra("postEntity");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle(getResources().getString(R.string.title_comments, mPostEntity.getComments_count()));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mLinearLayoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        mCommentsRecyclerView.setLayoutManager(mLinearLayoutManager);

        // 创建Adapter,并指定数据集
        mPostCommentsAdapter = new PostCommentsAdapter(this, mCommentEntities, hotCommentsCount);
        mCommentsRecyclerView.setAdapter(mPostCommentsAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mPostCommentsAdapter);
        mCommentsRecyclerView.addItemDecoration(headersDecor);
        mPostCommentsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mCommentsRecyclerView.addItemDecoration(mItemDecoration);

        // Add scrollListener
        mCommentsRecyclerView.addOnScrollListener(myOnScrollListener);
    }

    @Override
    protected void onRefreshStarted() {
        maxId = "0";
        mHasNext = true;
        requestHotComments();
    }

    /**
     * 请求热门评论
     */
    private void requestHotComments() {
        String requestUrl = APIConstData.GetPopularComments.replace("{postId}", String.valueOf(mPostEntity.getId()));
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        CommentData commentData = JSON.parseObject(t, CommentData.class);
                        hotCommentsCount = commentData.getTotal();
                        if ((ConstData.REQUEST_REFRESH).equals(mRequestType)) {
                            mCommentEntities.clear();
                        }
                        mCommentEntities.addAll(commentData.getComments());
                        requestComments();
                        ToastUtils.showShort(getBaseContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(getBaseContext(), "请求出错");
                        mRefreshing = false;
                        mLoading = false;
                        mSwipeRefresh.setRefreshing(false);
                    }
                })
                .encoding("UTF-8")
                .doTask();
    }

    /**
     * 请求普通评论
     */
    private void requestComments() {
        String requestUrl = APIConstData.GetComments.replace("{postId}", String.valueOf(mPostEntity.getId()));
        requestUrl = requestUrl.replace("{maxId}", maxId);
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        CommentData commentData = JSON.parseObject(t, CommentData.class);
                        mPositionStart = mCommentEntities.size();
                        mCommentEntities.addAll(commentData.getComments());
                        maxId = String.valueOf(mCommentEntities.get(mCommentEntities.size() - 1).getId());
                        if (mCommentEntities.size() >= hotCommentsCount + (commentData.getTotal())) {
                            mHasNext = false;
                            mCommentEntities.add(null);
                        }
                        mItemCount = mCommentEntities.size() - mPositionStart;
                        mPostCommentsAdapter.notifyItemRangeChanged(mPositionStart, mItemCount);
                        ToastUtils.showShort(getBaseContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(getBaseContext(), "请求出错");
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

    /**
     * 设置recycleView的滚动监听
     */
    RecyclerView.OnScrollListener myOnScrollListener = new RecyclerView.OnScrollListener() {
        int currentPosition = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentPosition = mLinearLayoutManager.findLastVisibleItemPosition();
            if (mCommentEntities == null || mCommentEntities.isEmpty() || mRefreshing || mLoading || !mHasNext) {
                return;
            }
            if (currentPosition + 4 >= mCommentEntities.size()) {
                mSwipeRefresh.setRefreshing(true);
                mLoading = true;
                mRequestType = ConstData.REQUEST_LOAD;
                requestComments();
            }
        }
    };

    public static void gotoPostComments(PostEntity postEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), PostCommentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("postEntity", postEntity);
        BaseApplication.getContext().startActivity(intent);
    }
}
