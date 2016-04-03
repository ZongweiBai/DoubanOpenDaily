package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.module.entity.PostsEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 我的喜欢Adapter
 */
public class FavoriteAdapter extends RecyclerView.Adapter<TodayAdapter.HeaderViewHolder> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<PostsEntity> mPostList;
    private Context mContext;

    public FavoriteAdapter(List<PostsEntity> mFavContentList) {
        this.mPostList = mFavContentList;
        this.mContext = BaseApplication.getContext();
    }

    @Override
    public TodayAdapter.HeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TodayAdapter.HeaderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
