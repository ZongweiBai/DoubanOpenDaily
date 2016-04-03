package com.monosky.daily.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.ui.fragment.adapter.HotAuthorAdapter;
import com.monosky.daily.util.ToastUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 热门作者Fragment
 */
public class HotAuthorFragment extends BaseRefreshFragment {

    @Bind(R.id.hot_author_recycler)
    RecyclerView mHotAuthorRecycler;
    private HotAuthorAdapter mAuthorAdapter;
    private List<AuthorEntity> mAuthorsEntities = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private int start = 0;
    private int recCount = 0;

    @Override
    protected int getLayout() {
        return R.layout.fragment_hot_author;
    }

    @Override
    protected void onRefreshStarted() {
        start = 0;
        mHasNext = true;
        requestRecAuthors();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mHotAuthorRecycler.setLayoutManager(layoutManager);

        // 创建Adapter,并指定数据集
        mAuthorAdapter = new HotAuthorAdapter(mAuthorsEntities);
        mHotAuthorRecycler.setAdapter(mAuthorAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAuthorAdapter);
        mHotAuthorRecycler.addItemDecoration(headersDecor);
        mAuthorAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mHotAuthorRecycler.addItemDecoration(mItemDecoration);

        // Add scrollListener
        mHotAuthorRecycler.addOnScrollListener(myOnScrollListener);

        // initialize swipeRefreshLayout and request
        initSwipeLayout();
    }

    /**
     * 设置recycleView的滚动监听
     */
    RecyclerView.OnScrollListener myOnScrollListener = new RecyclerView.OnScrollListener() {
        int currentPosition = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentPosition = layoutManager.findLastVisibleItemPosition();
            if (mAuthorsEntities == null || mAuthorsEntities.isEmpty() || mRefreshing || mLoading || !mHasNext) {
                return;
            }
            if (currentPosition + 4 >= mAuthorsEntities.size()) {
                mSwipeRefresh.setRefreshing(true);
                mLoading = true;
                mRequestType = ConstData.REQUEST_LOAD;
                requestAuthors();
            }
        }
    };

    private void requestRecAuthors() {
        String requestUrl = APIConstData.GetRecAuthor;
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        AuthorData authorData = JSON.parseObject(t, AuthorData.class);
                        recCount = authorData.getTotal();
                        if ((ConstData.REQUEST_REFRESH).equals(mRequestType)) {
                            mAuthorsEntities.clear();
                        }
                        mAuthorsEntities.addAll(authorData.getAuthors());
                        requestAuthors();
                        ToastUtils.showShort(getContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(getContext(), "请求出错");
                        mRefreshing = false;
                        mLoading = false;
                        mSwipeRefresh.setRefreshing(false);
                    }
                })
                .encoding("UTF-8")
                .doTask();
    }

    private void requestAuthors() {
        String requestUrl = APIConstData.GetHotAuthor + start;
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        AuthorData authorData = JSON.parseObject(t, AuthorData.class);
                        start += authorData.getAuthors().size();
                        mPositionStart = mAuthorsEntities.size();
                        mAuthorsEntities.addAll(authorData.getAuthors());
                        if (mAuthorsEntities.size() >= recCount + (authorData.getTotal())) {
                            mHasNext = false;
                            mAuthorsEntities.add(new AuthorEntity());
                        }
                        mItemCount = mAuthorsEntities.size() - mPositionStart;
                        mAuthorAdapter.notifyItemRangeChanged(mPositionStart, mItemCount);
                        ToastUtils.showShort(getContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(getContext(), "请求出错");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
