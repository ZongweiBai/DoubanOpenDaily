package com.monosky.daily.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.monosky.daily.R;
import com.monosky.daily.activity.AuthorActivity;
import com.monosky.daily.fragment.adapter.HotAuthorAdapter;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.test.GenerateTestDatas;
import com.monosky.daily.widget.PinnedSectionListView;
import com.monosky.daily.widget.swipyRefreshLayout.SwipyRefreshLayout;
import com.monosky.daily.widget.swipyRefreshLayout.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * 热门作者Fragment
 * A simple {@link Fragment} subclass.
 */
public class HotAuthorFragment extends Fragment {

    private HotAuthorAdapter mAuthorAdapter;
    private SwipyRefreshLayout mSwipeRefresh;
    private PinnedSectionListView mListView;
    private List<AuthorData> mListData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_author, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSwipeRefresh = (SwipyRefreshLayout) getView().findViewById(R.id.hot_author_refresh_layout);
        // 刷新的样式
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        mSwipeRefresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                mListData.addAll(GenerateTestDatas.loadAuthors());
                mAuthorAdapter.refresh(mListData);
                mSwipeRefresh.setRefreshing(false);
            }
        });
        mListView = (PinnedSectionListView) getView().findViewById(R.id.hot_author_listview);
        mListData = GenerateTestDatas.getAuthors();
        mAuthorAdapter = new HotAuthorAdapter(getActivity(), mListData);
        mListView.setAdapter(mAuthorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AuthorActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("authorData", mListData.get(position));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}
