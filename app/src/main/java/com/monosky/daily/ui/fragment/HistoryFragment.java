package com.monosky.daily.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.fragment.adapter.HistoryAdapter;
import com.monosky.daily.util.DateUtils;
import com.monosky.daily.util.ToastUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 往期内容Fragment
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseRefreshFragment {

    @Bind(R.id.history_recycler_view)
    RecyclerView mHistoryRecyclerView;
    private HistoryAdapter mHistoryAdapter;
    private List<PostEntity> mPostsEntityList = new ArrayList<>();
    private SimpleDateFormat formatSdf = new SimpleDateFormat("yyyy-MM-dd");
    private LinearLayoutManager layoutManager;
    private int dayBefore = -1;

    @Override

    protected int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // create linear manager
        layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mHistoryRecyclerView.setLayoutManager(layoutManager);

        // 创建Adapter,并指定数据集
        mHistoryAdapter = new HistoryAdapter(mPostsEntityList);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);
        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mHistoryAdapter);
        mHistoryRecyclerView.addItemDecoration(headersDecor);
        mHistoryAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });

        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mHistoryRecyclerView.addItemDecoration(mItemDecoration);

        // Add scrollListener
        mHistoryRecyclerView.addOnScrollListener(myOnScrollListener);

        // initialize swipeRefreshLayout and request
        initSwipeLayout();

    }

    @Override
    protected void onRefreshStarted() {
        dayBefore = -1;
        requestContent();
    }

    private void requestContent() {
        String requestUrl = APIConstData.GetContentByDate + formatSdf.format(DateUtils.beforeToday(dayBefore));
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        ContentData contentData = JSON.parseObject(t, ContentData.class);
                        if ((ConstData.REQUEST_REFRESH).equals(mRequestType)) {
                            mPostsEntityList.clear();
                        }
                        mPostsEntityList.addAll(contentData.getPosts());
                        mHistoryAdapter.refresh(mPostsEntityList);
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

    /**
     * 设置recycleView的滚动监听
     */
    RecyclerView.OnScrollListener myOnScrollListener = new RecyclerView.OnScrollListener() {
        int currentPosition = 0;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentPosition = layoutManager.findLastVisibleItemPosition();
            if (mPostsEntityList == null || mPostsEntityList.isEmpty() || mRefreshing || mLoading) {
                return;
            }
            if (currentPosition + 4 >= mPostsEntityList.size()) {
                mSwipeRefresh.setRefreshing(true);
                mLoading = true;
                mRequestType = ConstData.REQUEST_LOAD;
                dayBefore--;
                requestContent();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
