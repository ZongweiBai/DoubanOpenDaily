package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.ContentDetailActivity;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.util.ImageLoaderOption;
import com.monosky.daily.ui.widget.PinnedSectionListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonez_000 on 2015/8/17.
 */
public class HistoryAdaper extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<ContentData> contentDataList = new ArrayList<ContentData>();
    private Context mContext;
    private ImageLoader imageLoader =  ImageLoader.getInstance();
    private View.OnClickListener mHistoryClickListener;

    public HistoryAdaper(Context context,List<ContentData> list){
        this.contentDataList.clear();
        this.contentDataList.addAll(list);
        this.mContext=context;
        this.mHistoryClickListener = historyClickListener;
    }

    View.OnClickListener historyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag(R.id.history_content_layout);
            Intent intent = new Intent(mContext, ContentDetailActivity.class);
            mContext.startActivity(intent);
        }
    };

    @Override
    public int getCount() {
        return contentDataList.size();
    }

    @Override
    public ContentData getItem(int position) {
        return contentDataList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_history_item, null);
            viewHolder.mHistoryItemTitleLayout = (LinearLayout) convertView.findViewById(R.id.history_item_title_layout);
            viewHolder.mHistoryItemTitle = (TextView) convertView.findViewById(R.id.history_item_title);
            viewHolder.mContentLayout = (RelativeLayout) convertView.findViewById(R.id.history_content_layout);
            viewHolder.mCatalog = (TextView) convertView.findViewById(R.id.history_content_catalog);
            viewHolder.mContentTitle = (TextView) convertView.findViewById(R.id.history_content_title);
            viewHolder.mContentImg = (ImageView) convertView.findViewById(R.id.history_content_img);
            viewHolder.mContentLabel = (TextView) convertView.findViewById(R.id.history_content_label);
            viewHolder.mContentSepline = (View) convertView.findViewById(R.id.history_item_sepline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ContentData contentData = getItem(position);
//        if(ContentData.SECTION == contentData.getSortType()) {
//            viewHolder.mContentLayout.setVisibility(View.GONE);
//            viewHolder.mCatalog.setVisibility(View.GONE);
//            viewHolder.mContentTitle.setVisibility(View.GONE);
//            viewHolder.mContentImg.setVisibility(View.GONE);
//            viewHolder.mContentLabel.setVisibility(View.GONE);
//            viewHolder.mHistoryItemTitleLayout.setVisibility(View.VISIBLE);
//            viewHolder.mHistoryItemTitle.setText(contentData.getContentTime());
//            viewHolder.mContentSepline.setVisibility(View.GONE);
//        } else {
//            viewHolder.mHistoryItemTitleLayout.setVisibility(View.GONE);
//            viewHolder.mContentLayout.setVisibility(View.VISIBLE);
//            viewHolder.mContentLayout.setTag(R.id.history_content_layout, position);
//            viewHolder.mContentLayout.setOnClickListener(mHistoryClickListener);
//            if(TextUtils.isEmpty(contentData.getContentCatlog())) {
//                viewHolder.mCatalog.setVisibility(View.GONE);
//            } else {
//                viewHolder.mCatalog.setText(contentData.getContentCatlog());
//                viewHolder.mCatalog.setVisibility(View.VISIBLE);
//            }
//            viewHolder.mContentTitle.setText(contentData.getContentTitle());
//            viewHolder.mContentLabel.setText(contentData.getContentLabel());
//            imageLoader.displayImage(contentData.getContentImg(), viewHolder.mContentImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
//            if(position == contentDataList.size()-1) {
//                viewHolder.mContentSepline.setVisibility(View.GONE);
//            } else {
//                viewHolder.mContentSepline.setVisibility(View.VISIBLE);
//            }
//        }
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;//0是标题，1是内容
    }

    @Override
    public int getViewTypeCount() {
        return 2;//2种view的类型 baseAdapter中得方法
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    private class ViewHolder {
        LinearLayout mHistoryItemTitleLayout;
        TextView mHistoryItemTitle;
        RelativeLayout mContentLayout;
        TextView mCatalog;
        TextView mContentTitle;
        ImageView mContentImg;
        TextView mContentLabel;
        View mContentSepline;
    }

    public void refresh(List<ContentData> refreshList) {
        contentDataList.clear();
        contentDataList.addAll(refreshList);
        this.notifyDataSetChanged();
    }
}
