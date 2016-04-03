package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CatalogAdapter extends BaseAdapter {

    private List<ColumnEntity> catalogDataList;
    private Context mContext;
    private ImageLoader mImageLoader = ImageLoader.getInstance();

    public CatalogAdapter(List<ColumnEntity> catalogDataList) {
        this.catalogDataList = catalogDataList;
        this.mContext = BaseApplication.getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_catalog_item, null);
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
        return convertView;
    }

    @Override
    public int getCount() {
        return catalogDataList.size();
    }

    @Override
    public ColumnEntity getItem(int position) {
        return catalogDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @Bind(R.id.catalog_top_line)
        View mCatalogTopLine;
        @Bind(R.id.catalog_img)
        ImageView mCatalogImg;
        @Bind(R.id.catalog_title)
        TextView mCatalogTitle;
        @Bind(R.id.catalog_label)
        TextView mCatalogLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
