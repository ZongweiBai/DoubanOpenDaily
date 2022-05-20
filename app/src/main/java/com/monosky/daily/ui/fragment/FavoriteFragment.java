package com.monosky.daily.ui.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.monosky.daily.R;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.fragment.adapter.FavoriteAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的喜欢Fragment
 */
public class FavoriteFragment extends BaseRefreshFragment {

    @Bind(R.id.fav_recycler)
    RecyclerView mFavRecycler;
    private List<PostEntity> mFavPostList = new ArrayList();
    private FavoriteAdapter mFavAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.fragment_favorite;
    }

    @Override
    protected void onRefreshStarted() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        // 设置布局管理器
        mFavRecycler.setLayoutManager(mLayoutManager);
        mFavAdapter = new FavoriteAdapter(mFavPostList);
        mFavRecycler.setAdapter(mFavAdapter);

        initSwipeLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
