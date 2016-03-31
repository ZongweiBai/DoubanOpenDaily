package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 我的喜欢Fragment
 * Created by jonez_000 on 2015/8/18.
 */
public class FavoriteAdapter extends BaseAdapter {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private List<ContentData> mFavContentList;
    private Context mContext;

    public FavoriteAdapter(Context mContext, List<ContentData> mFavContentList) {
        this.mFavContentList = mFavContentList;
        this.mContext = mContext;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_favorite_item, null);
            viewHolder.mFavItemTitle = (TextView) convertView.findViewById(R.id.fav_item_title);
            viewHolder.mFavCatalogLayout = (RelativeLayout) convertView.findViewById(R.id.catalog_layout);
            viewHolder.mFavCatalog = (TextView) convertView.findViewById(R.id.fav_content_catalog);
            viewHolder.mFavContentLayout = (RelativeLayout) convertView.findViewById(R.id.fav_content_layout);
            viewHolder.mFavContentTitle = (TextView) convertView.findViewById(R.id.fav_content_title);
            viewHolder.mFavContentLabel = (TextView) convertView.findViewById(R.id.fav_content_label);
            viewHolder.mFavContentImg = (ImageView) convertView.findViewById(R.id.fav_content_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ContentData favContent = getItem(position);
        if(position == 0) {
            String favNum = mContext.getResources().getString(R.string.favorite_title, mFavContentList.size()-1);
            viewHolder.mFavItemTitle.setText(favNum);
            viewHolder.mFavItemTitle.setVisibility(View.VISIBLE);
            viewHolder.mFavContentLayout.setVisibility(View.GONE);
        } else {
            viewHolder.mFavItemTitle.setVisibility(View.GONE);
            viewHolder.mFavContentLayout.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(favContent.getContentCatlog())) {
                viewHolder.mFavCatalogLayout.setVisibility(View.GONE);
            } else {
                viewHolder.mFavCatalog.setText(favContent.getContentCatlog());
                viewHolder.mFavCatalogLayout.setVisibility(View.VISIBLE);
            }
            viewHolder.mFavContentTitle.setText(favContent.getContentTitle());
            viewHolder.mFavContentLabel.setText(favContent.getContentLabel());
            imageLoader.displayImage(favContent.getContentImg(), viewHolder.mFavContentImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
        }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(position == 0) {
                    return true;
                } else {
                    return v.onTouchEvent(event);
                }
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return mFavContentList.size();
    }

    @Override
    public ContentData getItem(int position) {
        return mFavContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView mFavItemTitle;
        RelativeLayout mFavContentLayout;
        RelativeLayout mFavCatalogLayout;
        TextView mFavCatalog;
        TextView mFavContentTitle;
        TextView mFavContentLabel;
        ImageView mFavContentImg;
    }
}
