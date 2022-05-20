package com.monosky.daily.ui.activity.post.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.post.PostDetailActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 栏目文章适配器
 */
public class ColumnPostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ColumnEntity mColumnEntity;
    private List<PostEntity> mPostEntityList;
    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private View.OnClickListener mOnClickListener;

    public ColumnPostsAdapter(ColumnEntity columnEntity, List<PostEntity> postEntityList) {
        this.mColumnEntity = columnEntity;
        this.mPostEntityList = postEntityList;
        this.mOnClickListener = myOnClickListener;
    }

    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = Integer.parseInt(String.valueOf(view.getTag()));
            PostDetailActivity.gotoPostDetail(mPostEntityList.get(position));
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstData.HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = View.inflate(parent.getContext(), R.layout.item_author_list, null);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == ConstData.HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.mColumnTitle.setText(mColumnEntity.getName());
            headerHolder.mColumnLabel.setText(mColumnEntity.getDescription());
            mImageLoader.displayImage(mColumnEntity.getIcon(), headerHolder.mColumnImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_column_default_light));
        } else {
            ListViewHolder listHolder = (ListViewHolder) holder;

            PostEntity postEntity = mPostEntityList.get(position);
            listHolder.mPostColumn.setVisibility(View.GONE);
            listHolder.mPostAbstract.setText(postEntity.getAbstractX());
            listHolder.mPostTitle.setText(postEntity.getTitle());

            String thumbImgUrl = null;
            if (postEntity.getThumbs() != null && !postEntity.getThumbs().isEmpty() && postEntity.getThumbs().size() > 0) {
                if (postEntity.getThumbs().get(0) != null && postEntity.getThumbs().get(0).getSmall() != null) {
                    if (!TextUtils.isEmpty(postEntity.getThumbs().get(0).getSmall().getUrl())) {
                        thumbImgUrl = postEntity.getThumbs().get(0).getSmall().getUrl();
                    }
                }
            }
            if (TextUtils.isEmpty(thumbImgUrl)) {
                listHolder.mPostThumb.setImageResource(R.mipmap.ic_default_image_square_light);
            } else {
                mImageLoader.displayImage(postEntity.getThumbs().get(0).getSmall().getUrl(), listHolder.mPostThumb, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_square_light));
            }

            listHolder.mPostLayout.setTag(position);
            listHolder.mPostLayout.setOnClickListener(mOnClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mPostEntityList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ConstData.HEADER;
        } else {
            return position;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.column_img)
        ImageView mColumnImg;
        @Bind(R.id.column_title)
        TextView mColumnTitle;
        @Bind(R.id.column_label)
        TextView mColumnLabel;
        @Bind(R.id.column_layout)
        RelativeLayout mColumnLayout;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_column)
        TextView mPostColumn;
        @Bind(R.id.post_title)
        TextView mPostTitle;
        @Bind(R.id.post_thumb)
        ImageView mPostThumb;
        @Bind(R.id.post_abstract)
        TextView mPostAbstract;
        @Bind(R.id.post_layout)
        RelativeLayout mPostLayout;

        ListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
