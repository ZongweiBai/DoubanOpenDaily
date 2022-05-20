package com.monosky.daily.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.fragment.adapter.TodayAdapter;
import com.monosky.daily.util.DateUtils;
import com.monosky.daily.util.ToastUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 今日一刻Fragment
 */
public class TodayFragment extends BaseRefreshFragment {

    @Bind(R.id.today_recycle_view)
    RecyclerView mRecycleView;
    private TodayAdapter mTodayAdapter;
    private List<PostEntity> mListData = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_today;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mRecycleView.setLayoutManager(layoutManager);
        // 创建Adapter,并指定数据集
        mTodayAdapter = new TodayAdapter(mListData);
        mRecycleView.setAdapter(mTodayAdapter);
        // Add decoration for dividers between list items
        HorizontalDividerItemDecoration mItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.drawer_line).sizeResId(R.dimen.common_divider).build();
        mRecycleView.addItemDecoration(mItemDecoration);

        // 初始化swipeRefreshLayout并发起请求
        initSwipeLayout();
    }

    @Override
    protected void onRefreshStarted() {
        String requestUrl = APIConstData.GetContentByDate + DateUtils.getDate();
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
                        mListData.clear();
                        mListData.add(new PostEntity());
                        mListData.addAll(contentData.getPosts());
                        mListData.add(new PostEntity());
                        mTodayAdapter.notifyDataSetChanged();
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
