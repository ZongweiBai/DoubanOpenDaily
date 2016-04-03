package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
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
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.PostsEntity;
import com.monosky.daily.module.entity.ThumbsEntity;
import com.monosky.daily.ui.activity.PostDetailActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 往期内容适配器
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext = BaseApplication.getContext();
    private List<PostsEntity> mPostsEntities = new ArrayList<>();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mHistoryClickListener;
    private SimpleDateFormat parseSdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatSdf = new SimpleDateFormat("dd", Locale.ENGLISH);
    private SimpleDateFormat formatSdfMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);

    public HistoryAdapter(List<PostsEntity> list) {
        this.mPostsEntities.clear();
        this.mPostsEntities.addAll(list);
        this.mHistoryClickListener = historyClickListener;
    }

    View.OnClickListener historyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            PostDetailActivity.gotoPostDetail(mPostsEntities.get(pos));
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View
        View view;
        if (viewType == ConstData.POST_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_post_image, null);
            return new ImageViewHolder(view);
        } else if (viewType == ConstData.POST_IMG_BOX) {
            view = View.inflate(parent.getContext(), R.layout.item_post_image_box, null);
            return new ImgBoxViewHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_post_list, null);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostsEntity postsEntity = mPostsEntities.get(position);
        int viewType = getItemViewType(position);
        if (viewType == ConstData.POST_IMAGE) {
            ImageViewHolder imageHolder = (ImageViewHolder) holder;
            imageHolder.mPostTitle.setText(postsEntity.getTitle());
            imageHolder.mPostTitle.setTextColor(ContextCompat.getColor(mContext, R.color.content_tile_unread));
            if (TextUtils.isEmpty(postsEntity.getColumn())) {
                imageHolder.mPostColumn.setVisibility(View.GONE);
            } else {
                imageHolder.mPostColumn.setText(postsEntity.getColumn());
                imageHolder.mPostColumn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                imageHolder.mPostColumn.setVisibility(View.VISIBLE);
            }
            if (postsEntity.isIs_editor_choice()) {
                imageHolder.mPostEditorMark.setVisibility(View.VISIBLE);
            } else {
                imageHolder.mPostEditorMark.setVisibility(View.GONE);
            }
            imageLoader.displayImage(postsEntity.getThumbs().get(0).getSmall().getUrl(), imageHolder.mPostThumb, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_light));
            imageHolder.mPostLayout.setTag(position);
            imageHolder.mPostLayout.setOnClickListener(mHistoryClickListener);
        } else if (viewType == ConstData.POST_IMG_BOX) {
            ImgBoxViewHolder imgboxHolder = (ImgBoxViewHolder) holder;
            imgboxHolder.mPostTitle.setText(postsEntity.getTitle());
            imgboxHolder.mPostTitle.setTextColor(ContextCompat.getColor(mContext, R.color.content_tile_unread));
            if (TextUtils.isEmpty(postsEntity.getColumn())) {
                imgboxHolder.mPostColumn.setVisibility(View.GONE);
            } else {
                imgboxHolder.mPostColumn.setText(postsEntity.getColumn());
                imgboxHolder.mPostColumn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                imgboxHolder.mPostColumn.setVisibility(View.VISIBLE);
            }
            if (postsEntity.isIs_editor_choice()) {
                imgboxHolder.mPostEditorMark.setVisibility(View.VISIBLE);
            } else {
                imgboxHolder.mPostEditorMark.setVisibility(View.GONE);
            }
            imageLoader.displayImage(postsEntity.getThumbs().get(0).getSmall().getUrl(), imgboxHolder.mPostThumb, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_light));
            imageLoader.displayImage(postsEntity.getThumbs().get(1).getSmall().getUrl(), imgboxHolder.mPostThumb2, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_light));
            imageLoader.displayImage(postsEntity.getThumbs().get(2).getSmall().getUrl(), imgboxHolder.mPostThumb3, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_light));
            imgboxHolder.mPostLayout.setTag(position);
            imgboxHolder.mPostLayout.setOnClickListener(mHistoryClickListener);
        } else {
            ListViewHolder listHolder = (ListViewHolder) holder;
            listHolder.mPostTitle.setText(postsEntity.getTitle());
            listHolder.mPostTitle.setTextColor(ContextCompat.getColor(mContext, R.color.content_tile_unread));
            listHolder.mPostAbstract.setText(postsEntity.getAbstractX());
            listHolder.mPostAbstract.setTextColor(ContextCompat.getColor(mContext, R.color.content_label_unread));
            if (TextUtils.isEmpty(postsEntity.getColumn())) {
                listHolder.mPostColumn.setVisibility(View.GONE);
            } else {
                listHolder.mPostColumn.setText(postsEntity.getColumn());
                listHolder.mPostColumn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                listHolder.mPostColumn.setVisibility(View.VISIBLE);
            }
            if (postsEntity.isIs_editor_choice()) {
                listHolder.mPostEditorMark.setVisibility(View.VISIBLE);
            } else {
                listHolder.mPostEditorMark.setVisibility(View.GONE);
            }

            String thumbImgUrl = null;
            if (postsEntity.getThumbs() != null && !postsEntity.getThumbs().isEmpty() && postsEntity.getThumbs().size() > 0) {
                if (postsEntity.getThumbs().get(0) != null && postsEntity.getThumbs().get(0).getSmall() != null) {
                    if (!TextUtils.isEmpty(postsEntity.getThumbs().get(0).getSmall().getUrl())) {
                        thumbImgUrl = postsEntity.getThumbs().get(0).getSmall().getUrl();
                    }
                }
            }
            if (TextUtils.isEmpty(thumbImgUrl)) {
                listHolder.mPostThumb.setVisibility(View.GONE);
            } else {
                listHolder.mPostThumb.setVisibility(View.VISIBLE);
                imageLoader.displayImage(thumbImgUrl, listHolder.mPostThumb, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_image_square_light));
            }

            listHolder.mPostLayout.setTag(position);
            listHolder.mPostLayout.setOnClickListener(mHistoryClickListener);
        }
    }

    public void refresh(List<PostsEntity> refreshList) {
        mPostsEntities.clear();
        mPostsEntities.addAll(refreshList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        PostsEntity postsEntity = mPostsEntities.get(position);
        if (postsEntity.getDisplay_style() == 10003) {
            List<ThumbsEntity> thumbsEntityList = postsEntity.getThumbs();
            if (thumbsEntityList == null || thumbsEntityList.isEmpty()) {
                return ConstData.POST_LIST;
            }
            ThumbsEntity thumbsEntity = thumbsEntityList.get(0);
            if (thumbsEntity == null || TextUtils.isEmpty(thumbsEntity.getTag_name())) {
                return ConstData.POST_LIST;
            }
            if ("img_3".equals(thumbsEntity.getTag_name()) || thumbsEntityList.size() < 3) {
                return ConstData.POST_IMAGE;
            } else {
                return ConstData.POST_IMG_BOX;
            }
        }
        return ConstData.POST_LIST;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        return getPublishDate(position).getTime();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        // 创建一个View
        View view = View.inflate(parent.getContext(), R.layout.item_history_header, null);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        Date publishTime = getPublishDate(position);
        headerViewHolder.mItemHeader.setText(formatSdf.format(publishTime));
        headerViewHolder.mItemHeaderMonth.setText(formatSdfMonth.format(publishTime));
    }

    @Override
    public int getItemCount() {
        return mPostsEntities.size();
    }

    public Date getPublishDate(int position) {
        try {
            return parseSdf.parse(mPostsEntities.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 悬浮头部ViewHolder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_header)
        TextView mItemHeader;
        @Bind(R.id.item_header_month)
        TextView mItemHeaderMonth;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 图文ViewHolder
     */
    static class ListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_column)
        TextView mPostColumn;
        @Bind(R.id.post_title)
        TextView mPostTitle;
        @Bind(R.id.post_editor_mark)
        ImageView mPostEditorMark;
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

    /**
     * 大图ViewHolder
     */
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_column)
        TextView mPostColumn;
        @Bind(R.id.post_title)
        TextView mPostTitle;
        @Bind(R.id.post_editor_mark)
        ImageView mPostEditorMark;
        @Bind(R.id.post_thumb)
        ImageView mPostThumb;
        @Bind(R.id.post_layout)
        RelativeLayout mPostLayout;

        ImageViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 多图ViewHolder
     */
    static class ImgBoxViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.post_column)
        TextView mPostColumn;
        @Bind(R.id.post_title)
        TextView mPostTitle;
        @Bind(R.id.post_editor_mark)
        ImageView mPostEditorMark;
        @Bind(R.id.post_thumb)
        ImageView mPostThumb;
        @Bind(R.id.post_thumb2)
        ImageView mPostThumb2;
        @Bind(R.id.post_thumb3)
        ImageView mPostThumb3;
        @Bind(R.id.post_layout)
        RelativeLayout mPostLayout;

        ImgBoxViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
