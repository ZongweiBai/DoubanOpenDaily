package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.MainActivity;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 今日一刻adapter
 * Created by jonez_000 on 2015/8/16.
 */
public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ImageLoader imageLoader =  ImageLoader.getInstance();
    // 数据集
    private List<ContentData> mDataset;

    public TodayAdapter(Context context,List<ContentData> dataset) {
        super();
        mDataset = dataset;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View
        View view = View.inflate(viewGroup.getContext(), R.layout.fragment_today_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        holder.mContentLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        // 绑定数据到ViewHolder上
        viewHolder.mContentLayout.setTag(pos);
        if(pos == 0) {
            viewHolder.mTodayItemTitle.setText("16 Aug");
            viewHolder.mTodayItemTitle.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mTodayItemTitle.setVisibility(View.GONE);
        }
        if(pos == getItemCount()-1) {
            viewHolder.mHistory.setVisibility(View.VISIBLE);
            viewHolder.mHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.mainActivity.clickHistory();
                }
            });
        } else {
            viewHolder.mHistory.setVisibility(View.GONE);
        }
        ContentData data = mDataset.get(pos);
        if(TextUtils.isEmpty(data.getContentCatlog())) {
            viewHolder.mCatalog.setVisibility(View.GONE);
        } else {
            viewHolder.mCatalog.setText(data.getContentCatlog());
            viewHolder.mCatalog.setVisibility(View.VISIBLE);
        }
        viewHolder.mContentTitle.setText(data.getContentTitle());
        viewHolder.mContentLabel.setText(data.getContentLabel());
        imageLoader.displayImage(data.getContentImg(), viewHolder.mContentImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTodayItemTitle;
        public RelativeLayout mContentLayout;
        public TextView mCatalog;
        public TextView mContentTitle;
        public ImageView mContentImg;
        public TextView mContentLabel;
        public TextView mHistory;

        public ViewHolder(View itemView) {
            super(itemView);
            mTodayItemTitle = (TextView) itemView.findViewById(R.id.today_item_title);
            mContentLayout = (RelativeLayout) itemView.findViewById(R.id.today_content_layout);
            mCatalog = (TextView) itemView.findViewById(R.id.today_content_catalog);
            mContentTitle = (TextView) itemView.findViewById(R.id.today_content_title);
            mContentImg = (ImageView) itemView.findViewById(R.id.today_content_img);
            mContentLabel = (TextView) itemView.findViewById(R.id.today_content_label);
            mHistory = (TextView) itemView.findViewById(R.id.today_to_history);
        }

    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Object data);
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
}
