package com.monosky.daily.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monosky.daily.R;
import com.monosky.daily.activity.ContentDetailActivity;
import com.monosky.daily.fragment.adapter.TodayAdapter;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.test.GenerateTestDatas;

import java.util.List;

/**
 * 今日一刻FragMent
 */
public class TodayFragment extends Fragment {

    private TodayAdapter mTodayAdapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecycleView;
    private List<ContentData> mListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSwipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.today_swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mSwipeRefresh.setRefreshing(false);
                        mTodayAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
        // 顶部刷新的样式
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);

        mRecycleView = (RecyclerView) getView().findViewById(R.id.today_recycle_view);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mRecycleView.setLayoutManager(layoutManager);
        // 第一次进入时，显示刷新，必须这样调用，否则进度图片无法显示
        mSwipeRefresh.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
                mListData = GenerateTestDatas.getDefaultContents();

                // 创建Adapter,并指定数据集
                mTodayAdapter = new TodayAdapter(getActivity(), mListData);
                mRecycleView.setAdapter(mTodayAdapter);
                mTodayAdapter.setOnItemClickListener(new TodayAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, Object data) {
                        Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

}
