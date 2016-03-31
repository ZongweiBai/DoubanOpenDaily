package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.ui.widget.PinnedSectionListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonez_000 on 2015/8/18.
 */
public class HotAuthorAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<AuthorData> authorDataList = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader =  ImageLoader.getInstance();

    public HotAuthorAdapter(Context context,List<AuthorData> list){
        this.authorDataList.clear();
        this.authorDataList.addAll(list);
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return authorDataList.size();
    }

    @Override
    public AuthorData getItem(int position) {
        return authorDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_hot_author_item, null);
            viewHolder.mAuthorItemTitleLayout = (LinearLayout) convertView.findViewById(R.id.hot_author_item_title_layout);
            viewHolder.mAuthorItemTitle = (TextView) convertView.findViewById(R.id.hot_author_item_title);
            viewHolder.mAuthorLayout = (RelativeLayout) convertView.findViewById(R.id.hot_author_content_layout);
            viewHolder.mAuthorName = (TextView) convertView.findViewById(R.id.author_name);
            viewHolder.mAuthorImg = (ImageView) convertView.findViewById(R.id.author_img);
            viewHolder.mAuthorLabel = (TextView) convertView.findViewById(R.id.author_label);
            viewHolder.mAuthorSepline = (View) convertView.findViewById(R.id.hot_author_item_sepline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AuthorData authorData = getItem(position);
        if(AuthorData.SECTION == authorData.getSortType()) {
            viewHolder.mAuthorLayout.setVisibility(View.GONE);
            viewHolder.mAuthorName.setVisibility(View.GONE);
            viewHolder.mAuthorImg.setVisibility(View.GONE);
            viewHolder.mAuthorLabel.setVisibility(View.GONE);
            viewHolder.mAuthorItemTitleLayout.setVisibility(View.VISIBLE);
            if(AuthorData.TYPE_WEEK==authorData.getType()) {
                viewHolder.mAuthorItemTitle.setText(mContext.getResources().getString(R.string.week_recommand));
            } else {
                viewHolder.mAuthorItemTitle.setText(mContext.getResources().getString(R.string.hot_author_recommand));
            }
            viewHolder.mAuthorSepline.setVisibility(View.GONE);
        } else {
            viewHolder.mAuthorItemTitleLayout.setVisibility(View.GONE);
            viewHolder.mAuthorLayout.setVisibility(View.VISIBLE);
            viewHolder.mAuthorName.setText(authorData.getAuthorName());
            viewHolder.mAuthorLabel.setText(authorData.getAuthorLabel());
            imageLoader.displayImage(authorData.getAuthorImg(), viewHolder.mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
            if(position == authorDataList.size()-1) {
                viewHolder.mAuthorSepline.setVisibility(View.GONE);
            } else {
                viewHolder.mAuthorSepline.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == AuthorData.SECTION;//0是标题，1是内容
    }

    @Override
    public int getViewTypeCount() {
        return 2;//2种view的类型 baseAdapter中得方法
    }

    @Override
    public int getItemViewType(int position) {
        return ((AuthorData) getItem(position)).getSortType();
    }

    private class ViewHolder {
        LinearLayout mAuthorItemTitleLayout;
        TextView mAuthorItemTitle;
        RelativeLayout mAuthorLayout;
        ImageView mAuthorImg;
        TextView mAuthorName;
        TextView mAuthorLabel;
        View mAuthorSepline;
    }

    public void refresh(List<AuthorData> refreshList) {
        authorDataList.clear();
        authorDataList.addAll(refreshList);
        this.notifyDataSetChanged();
    }
}
