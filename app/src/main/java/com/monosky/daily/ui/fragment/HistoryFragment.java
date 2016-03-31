package com.monosky.daily.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monosky.daily.R;
import com.monosky.daily.ui.fragment.adapter.HistoryAdaper;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.test.GenerateTestDatas;
import com.monosky.daily.ui.widget.PinnedSectionListView;
import com.monosky.daily.ui.widget.swipyRefreshLayout.SwipyRefreshLayout;
import com.monosky.daily.ui.widget.swipyRefreshLayout.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * 往期内容Fragment
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private HistoryAdaper mHistoryAdapter;
    private SwipyRefreshLayout mSwipeRefresh;
    private PinnedSectionListView mListView;
    private List<ContentData> mListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefresh = (SwipyRefreshLayout) getView().findViewById(R.id.history_refresh_layout);
        mSwipeRefresh.setDirection(SwipyRefreshLayoutDirection.TOP);
        // 刷新的样式
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        mListView = (PinnedSectionListView) getView().findViewById(R.id.history_listview);

        mSwipeRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                mListData.addAll(GenerateTestDatas.loadHistoryContents());
                mHistoryAdapter.refresh(mListData);
                mSwipeRefresh.setRefreshing(false);
            }
        });

        mSwipeRefresh.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListData = GenerateTestDatas.getHistoryContents();
                mHistoryAdapter = new HistoryAdaper(getActivity(), mListData);
                mListView.setAdapter(mHistoryAdapter);
                mSwipeRefresh.setRefreshing(false);
                mSwipeRefresh.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
            }
        }, 5000);

    }
}
