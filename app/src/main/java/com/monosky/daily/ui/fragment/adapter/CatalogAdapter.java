package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.module.CatalogData;

import java.util.List;

/**
 * Created by jonez_000 on 2015/8/18.
 */
public class CatalogAdapter extends BaseAdapter {

    private List<CatalogData> catalogDataList;
    private Context mContext;

    public CatalogAdapter(Context mContext, List<CatalogData> catalogDataList) {
        this.mContext = mContext;
        this.catalogDataList = catalogDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_catalog_item, null);
            viewHolder.mCatalogTopLine = (View) convertView.findViewById(R.id.catalog_top_line);
            viewHolder.mCatalogImg = (ImageView) convertView.findViewById(R.id.catalog_img);
            viewHolder.mCatalogTitle = (TextView) convertView.findViewById(R.id.catalog_title);
            viewHolder.mCatalogLabel = (TextView) convertView.findViewById(R.id.catalog_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CatalogData catalogData = getItem(position);
        if(position == 0 || position == 1) {
            viewHolder.mCatalogTopLine.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mCatalogTopLine.setVisibility(View.GONE);
        }
        viewHolder.mCatalogImg.setBackgroundResource(R.mipmap.ic_column_default_light);
        viewHolder.mCatalogTitle.setText(catalogData.getCatalogTitle());
        viewHolder.mCatalogLabel.setText(catalogData.getCatalogLabel());
        return convertView;
    }

    @Override
    public int getCount() {
        return catalogDataList.size();
    }

    @Override
    public CatalogData getItem(int position) {
        return catalogDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        View mCatalogTopLine;
        ImageView mCatalogImg;
        TextView mCatalogTitle;
        TextView mCatalogLabel;
    }
}
