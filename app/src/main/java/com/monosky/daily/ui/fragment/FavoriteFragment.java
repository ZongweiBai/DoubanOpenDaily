package com.monosky.daily.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.ContentDetailActivity;
import com.monosky.daily.ui.fragment.adapter.FavoriteAdapter;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.test.GenerateTestDatas;
import com.monosky.daily.ui.widget.swipyRefreshLayout.SwipyRefreshLayout;
import com.monosky.daily.ui.widget.swipyRefreshLayout.SwipyRefreshLayoutDirection;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private List<ContentData> mFavContentList;
    private FavoriteAdapter mFavAdapter;
    private SwipyRefreshLayout mRefreshLayout;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRefreshLayout = (SwipyRefreshLayout) getView().findViewById(R.id.fav_refresh_layout);
        // 刷新的样式
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
        mRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Hide the refresh after 2sec
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }, 5000);
                mRefreshLayout.setRefreshing(false);
            }
        });

        mListView = (ListView) getView().findViewById(R.id.fav_listview);
        mFavContentList = GenerateTestDatas.getFavContentData();
        mFavContentList.add(0, new ContentData());
        mFavAdapter = new FavoriteAdapter(getActivity(), mFavContentList);
        mListView.setAdapter(mFavAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContentDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
