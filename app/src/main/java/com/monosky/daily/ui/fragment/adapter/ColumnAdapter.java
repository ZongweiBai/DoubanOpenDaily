package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.ui.activity.post.ColumnPostsActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColumnAdapter extends BaseAdapter {

    private List<ColumnEntity> columnDataList;
    private Context mContext;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private View.OnClickListener mOnClickListener;

    public ColumnAdapter(List<ColumnEntity> columnDataList) {
        this.columnDataList = columnDataList;
        this.mContext = BaseApplication.getContext();
        this.mOnClickListener = myOnClickListener;
    }

    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(String.valueOf(view.getTag()));
            ColumnPostsActivity.gotoColumnPosts(columnDataList.get(position));
        }
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_column, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ColumnEntity columnsEntity = getItem(position);
        if (position == 0 || position == 1) {
            viewHolder.mCatalogTopLine.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mCatalogTopLine.setVisibility(View.GONE);
        }
        mImageLoader.displayImage(columnsEntity.getIcon(), viewHolder.mCatalogImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_column_default_light));
        viewHolder.mCatalogTitle.setText(columnsEntity.getName());
        viewHolder.mCatalogLabel.setText(columnsEntity.getDescription());

        viewHolder.mColumnLayout.setTag(position);
        viewHolder.mColumnLayout.setOnClickListener(mOnClickListener);
        return convertView;
    }

    @Override
    public int getCount() {
        return columnDataList.size();
    }

    @Override
    public ColumnEntity getItem(int position) {
        return columnDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @Bind(R.id.column_layout)
        LinearLayout mColumnLayout;
        @Bind(R.id.catalog_top_line)
        View mCatalogTopLine;
        @Bind(R.id.column_img)
        ImageView mCatalogImg;
        @Bind(R.id.column_title)
        TextView mCatalogTitle;
        @Bind(R.id.column_label)
        TextView mCatalogLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
