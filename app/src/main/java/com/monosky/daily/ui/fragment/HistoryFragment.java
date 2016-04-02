package com.monosky.daily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.ui.fragment.adapter.HistoryAdaper;
import com.monosky.daily.util.DateUtils;
import com.monosky.daily.util.ToastUtils;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

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
    private HistoryAdaper mHistoryAdapter;
    private View footerView;
    private List<ContentData.PostsEntity> mPostsEntityList = new ArrayList<>();
    private SimpleDateFormat formatSdf = new SimpleDateFormat("yyyy-MM-dd");
    private int dayBefore = -1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 脚部
        footerView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.footer_view, null);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mHistoryRecyclerView.setLayoutManager(layoutManager);
//        mHistoryRecyclerView.setLoadMore(true);
        // 添加头部和脚部，如果不添加就使用默认的头部和脚部
//        mHistoryRecyclerView.addFootView(footerView);

        // 创建Adapter,并指定数据集
        mHistoryAdapter = new HistoryAdaper(mPostsEntityList);
        mHistoryRecyclerView.setAdapter(mHistoryAdapter);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mHistoryAdapter);
        mHistoryRecyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
//        mHistoryRecyclerView.addItemDecoration(new DividerDecoration(this));

        // 设置加载更多数据的监听
//        mHistoryRecyclerView.setLoadDataListener(new LoadMoreRecyclerView.LoadDataListener() {
//            @Override
//            public void onLoadMore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mHistoryRecyclerView.getAdapter().notifyDataSetChanged();
//                        // 加载更多完成后调用，必须在UI线程中
//                        mHistoryRecyclerView.loadMoreComplete();
////                        mHistoryRecyclerView.setLoadMore(false);
//                    }
//                }, 4000);
//            }
//        });

        // 初始化swipeRefreshLayout并发起请求
        initSwipeLayout();

    }

    @Override
    protected void onRefreshStarted() {
        dayBefore = -1;
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
                        mPostsEntityList.clear();
                        mPostsEntityList.addAll(contentData.getPosts());
//                        mHistoryAdapter = (HistoryAdaper) mHistoryRecyclerView.getAdapter();
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
