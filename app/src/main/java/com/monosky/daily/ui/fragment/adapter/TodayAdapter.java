package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
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
import com.monosky.daily.ui.activity.MainActivity;
import com.monosky.daily.ui.activity.PostDetailActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 今日一刻adapter
 */
public class TodayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext = BaseApplication.getContext();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mOnClickListener;
    private SimpleDateFormat formatSdf = new SimpleDateFormat("dd", Locale.ENGLISH);
    private SimpleDateFormat formatSdfMonth = new SimpleDateFormat("MMM", Locale.ENGLISH);
    // 数据集
    private List<PostsEntity> mPostList;

    public TodayAdapter(List<PostsEntity> dataList) {
        super();
        mPostList = dataList;
        this.mOnClickListener = myOnClickListener;
    }

    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            PostDetailActivity.gotoPostDetail(mPostList.get(pos));
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建一个View
        View view;
        if (viewType == ConstData.HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ConstData.FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_footer, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == ConstData.POST_IMAGE) {
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
        PostsEntity postsEntity = mPostList.get(position);
        int viewType = getItemViewType(position);
        if (viewType == ConstData.HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.mItemHeader.setText(formatSdf.format(new Date()));
            headerHolder.mItemHeaderMonth.setText(formatSdfMonth.format(new Date()));
        } else if (viewType == ConstData.FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.mTodayToHistory.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            footerHolder.mTodayToHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.mainActivity.clickHistory();
                }
            });
        } else if (viewType == ConstData.POST_IMAGE) {
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
            imageHolder.mPostLayout.setOnClickListener(mOnClickListener);
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
            imgboxHolder.mPostLayout.setOnClickListener(mOnClickListener);
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
            listHolder.mPostLayout.setOnClickListener(mOnClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public int getItemViewType(int position) {
        PostsEntity postsEntity = mPostList.get(position);
        if (position == 0 && TextUtils.isEmpty(postsEntity.getTitle())) {
            return ConstData.HEADER;
        } else if (position == (mPostList.size() - 1) && TextUtils.isEmpty(postsEntity.getTitle())) {
            return ConstData.FOOTER;
        } else if (postsEntity.getDisplay_style() == 10003) {
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

    /**
     * 头部ViewHolder
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
     * 脚部ViewHolder
     */
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.today_to_history)
        TextView mTodayToHistory;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
