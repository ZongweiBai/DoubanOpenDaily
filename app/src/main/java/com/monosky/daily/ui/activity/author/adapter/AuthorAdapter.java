package com.monosky.daily.ui.activity.author.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.author.AuthorMainPageActivity;
import com.monosky.daily.ui.activity.post.PostDetailActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者详情页（信息+文章）adapter
 */
public class AuthorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context mContext;
    private AuthorEntity mAuthorEntity;
    private List<PostEntity> mPostList;
    private View.OnClickListener mOnClickListener;

    public AuthorAdapter(AuthorEntity mAuthor, List<PostEntity> mPosts) {
        this.mContext = BaseApplication.getContext();
        this.mAuthorEntity = mAuthor;
        this.mPostList = mPosts;
        this.mOnClickListener = contentClickListener;
    }

    View.OnClickListener contentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = Integer.parseInt(String.valueOf(v.getTag()));
            PostDetailActivity.gotoPostDetail(mPostList.get(pos));
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ConstData.HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author_header, parent, false);
            return new HeaderViewHolder(view);
        } else if (viewType == ConstData.FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_simple_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_author_list, null);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostEntity postsEntity = mPostList.get(position);
        int viewType = getItemViewType(position);
        if (viewType == ConstData.HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.mAuthorName.setText(mAuthorEntity.getName());
            headerHolder.mAuthorName.setTextColor(ContextCompat.getColor(mContext, R.color.content_tile_unread));
            headerHolder.mAuthorResume.setText(mAuthorEntity.getResume());
            headerHolder.mAuthorResume.setTextColor(ContextCompat.getColor(mContext, R.color.content_label_readed));
            imageLoader.displayImage(mAuthorEntity.getAvatar(), headerHolder.mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_light));
            headerHolder.mAuthorMainPage.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
            headerHolder.mAuthorMainPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthorMainPageActivity.gotoAuthorMain(mAuthorEntity);
                }
            });
        } else if (viewType == ConstData.FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.mSimpleFooterText.setText(mContext.getString(R.string.author_post_all));
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
        PostEntity postsEntity = mPostList.get(position);
        if (position == 0 && postsEntity == null) {
            return ConstData.HEADER;
        } else if (position == (mPostList.size() - 1) && postsEntity == null) {
            return ConstData.FOOTER;
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
     * 头部ViewHolder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.author_img)
        CircleImageView mAuthorImg;
        @Bind(R.id.author_name)
        TextView mAuthorName;
        @Bind(R.id.author_resume)
        TextView mAuthorResume;
        @Bind(R.id.author_main_page)
        TextView mAuthorMainPage;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 底部ViewHolder
     */
    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.simple_footer_text)
        TextView mSimpleFooterText;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
