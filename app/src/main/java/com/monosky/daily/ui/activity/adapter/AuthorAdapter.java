package com.monosky.daily.ui.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.constant.ConstData;
import com.monosky.daily.R;
import com.monosky.daily.ui.activity.AuthorMainPageActivity;
import com.monosky.daily.ui.activity.ContentDetailActivity;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by jonez_000 on 2015/8/23.
 */
public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context mContext;
    private AuthorData mAuthorData;
    private List<ContentData> mAuthorContents;
    private View.OnClickListener mContentClickListener;

    public AuthorAdapter(Context mContext, AuthorData mAuthorData, List<ContentData> mAuthorContents) {
        this.mContext = mContext;
        this.mAuthorData = mAuthorData;
        this.mAuthorContents = mAuthorContents;
        this.mContentClickListener = contentClickListener;
    }

    View.OnClickListener contentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.author_content_layout:
                    int pos = Integer.parseInt(String.valueOf(v.getTag()));
                    intent = new Intent(mContext, ContentDetailActivity.class);
                    mContext.startActivity(intent);
                    break;
                case R.id.author_main_page:
                    intent = new Intent(mContext, AuthorMainPageActivity.class);
                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("authorData", mAuthorData);
                    intent.putExtras(bundle);
                    intent.putExtra("type", ConstData.MAIN_PAGE_TYPE_OTHER);
                    mContext.startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.activity_author_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContentData contentData = mAuthorContents.get(position);
        if(position == 0) {
//            imageLoader.displayImage(mAuthorData.getAuthorImg(), holder.mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
//            holder.mAuthorName.setText(mAuthorData.getAuthorName());
//            holder.mAuthorLabel.setText(mAuthorData.getAuthorLabel());
            holder.mAuthorLayout.setVisibility(View.VISIBLE);

            holder.mAuthorMainPage.setOnClickListener(mContentClickListener);
        } else {
            holder.mAuthorLayout.setVisibility(View.GONE);
        }
//        holder.mContentTitle.setText(contentData.getContentTitle());
//        imageLoader.displayImage(contentData.getContentImg(), holder.mContentImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
//        holder.mContentLabel.setText(contentData.getContentLabel());
        holder.mContentLayout.setTag(position);
        holder.mContentLayout.setOnClickListener(mContentClickListener);
    }

    @Override
    public int getItemCount() {
        return mAuthorContents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mAuthorLayout;
        public ImageView mAuthorImg;
        public TextView mAuthorName;
        public TextView mAuthorLabel;
        public TextView mAuthorMainPage;
        public RelativeLayout mContentLayout;
        public TextView mContentTitle;
        public ImageView mContentImg;
        public TextView mContentLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            mAuthorLayout = (LinearLayout) itemView.findViewById(R.id.author_layout);
            mAuthorImg = (ImageView) itemView.findViewById(R.id.author_img);
            mAuthorName = (TextView) itemView.findViewById(R.id.author_name);
            mAuthorLabel = (TextView) itemView.findViewById(R.id.author_label);
            mAuthorMainPage = (TextView) itemView.findViewById(R.id.author_main_page);
            mContentLayout = (RelativeLayout) itemView.findViewById(R.id.author_content_layout);
            mContentTitle = (TextView) itemView.findViewById(R.id.author_content_title);
            mContentImg = (ImageView) itemView.findViewById(R.id.author_content_img);
            mContentLabel = (TextView) itemView.findViewById(R.id.author_content_label);
        }
    }
}
