package com.monosky.daily.ui.fragment.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.module.entity.PostsEntity;
import com.monosky.daily.ui.activity.MainActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 今日一刻adapter
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> implements View.OnClickListener {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    // 数据集
    private List<PostsEntity> mPostList;

    public TodayAdapter(List<PostsEntity> dataList) {
        super();
        mPostList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View
        View view = View.inflate(viewGroup.getContext(), R.layout.fragment_today_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        holder.mTodayContentLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        // 绑定数据到ViewHolder上
        viewHolder.mTodayContentLayout.setTag(pos);
        if (pos == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
            viewHolder.mTodayItemTitle.setText(sdf.format(new Date()));
            viewHolder.mTodayItemTitle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTodayItemTitle.setVisibility(View.GONE);
        }
        if (pos == getItemCount() - 1) {
            viewHolder.mTodayToHistory.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.white));
            viewHolder.mTodayToHistory.setVisibility(View.VISIBLE);
            viewHolder.mTodayToHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.mainActivity.clickHistory();
                }
            });
        } else {
            viewHolder.mTodayToHistory.setVisibility(View.GONE);
        }
        PostsEntity postsEntity = mPostList.get(pos);
        if (TextUtils.isEmpty(postsEntity.getColumn())) {
            viewHolder.mTodayContentCatalog.setVisibility(View.GONE);
        } else {
            viewHolder.mTodayContentCatalog.setTextColor(ContextCompat.getColor(BaseApplication.getContext(), R.color.white));
            viewHolder.mTodayContentCatalog.setText(postsEntity.getColumn());
            viewHolder.mTodayContentCatalog.setVisibility(View.VISIBLE);
        }
        viewHolder.mTodayContentTitle.setText(postsEntity.getTitle());
        viewHolder.mTodayContentLabel.setText(postsEntity.getAbstractX());
        String thumbImgUrl = null;
        if (postsEntity.getThumbs() != null && !postsEntity.getThumbs().isEmpty() && postsEntity.getThumbs().size() > 0) {
            if (postsEntity.getThumbs().get(0) != null && postsEntity.getThumbs().get(0).getSmall() != null) {
                if (!TextUtils.isEmpty(postsEntity.getThumbs().get(0).getSmall().getUrl())) {
                    thumbImgUrl = postsEntity.getThumbs().get(0).getSmall().getUrl();
                }
            }
        }
        if (TextUtils.isEmpty(thumbImgUrl)) {
            viewHolder.mTodayContentImg.setVisibility(View.GONE);
            viewHolder.mTodayContentImg.setImageResource(R.mipmap.ic_empty_light);
        } else {
            viewHolder.mTodayContentImg.setVisibility(View.VISIBLE);
            imageLoader.displayImage(thumbImgUrl, viewHolder.mTodayContentImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.today_item_title)
        TextView mTodayItemTitle;
        @Bind(R.id.today_content_catalog)
        TextView mTodayContentCatalog;
        @Bind(R.id.catalog_layout)
        RelativeLayout mCatalogLayout;
        @Bind(R.id.today_content_title)
        TextView mTodayContentTitle;
        @Bind(R.id.today_content_img)
        ImageView mTodayContentImg;
        @Bind(R.id.today_content_label)
        TextView mTodayContentLabel;
        @Bind(R.id.today_content_layout)
        RelativeLayout mTodayContentLayout;
        @Bind(R.id.today_to_history)
        TextView mTodayToHistory;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
