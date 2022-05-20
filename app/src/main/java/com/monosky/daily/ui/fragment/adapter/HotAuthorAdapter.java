package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.ui.activity.author.AuthorActivity;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 热门作者adapter
 */
public class HotAuthorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<AuthorEntity> mAuthorsEntities = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mOnClickListener;

    public HotAuthorAdapter(List<AuthorEntity> list) {
        this.mAuthorsEntities = list;
        this.mContext = BaseApplication.getContext();
        this.mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Integer.parseInt(String.valueOf(view.getTag()));
                AuthorActivity.gotoAuthorDetail(mAuthorsEntities.get(pos));
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstData.FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_simple_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = View.inflate(parent.getContext(), R.layout.item_hot_author, null);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ConstData.FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.mHotAuthorFooter.setText(mContext.getString(R.string.author_all));
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            AuthorEntity authorData = mAuthorsEntities.get(position);
            itemHolder.mAuthorName.setText(authorData.getName());
            itemHolder.mAuthorName.setTextColor(ContextCompat.getColor(mContext, R.color.content_tile_unread));
            itemHolder.mAuthorLabel.setText(authorData.getEditorNotes());
            imageLoader.displayImage(authorData.getAvatar(), itemHolder.mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
            itemHolder.mHotAuthorContentLayout.setTag(position);
            itemHolder.mHotAuthorContentLayout.setOnClickListener(mOnClickListener);
        }
    }

    @Override
    public long getHeaderId(int position) {
        if (position < 5) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_hot_author_header, null);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        if (position < 5) {
            headerHolder.mHotAuthorHeader.setText(mContext.getString(R.string.week_rec));
        } else {
            headerHolder.mHotAuthorHeader.setText(mContext.getString(R.string.hot_author_rec));
        }
    }

    @Override
    public int getItemCount() {
        return mAuthorsEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAuthorsEntities.get(position) == null) {
            return ConstData.FOOTER;
        }
        return position;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.author_img)
        CircleImageView mAuthorImg;
        @Bind(R.id.author_name)
        TextView mAuthorName;
        @Bind(R.id.author_label)
        TextView mAuthorLabel;
        @Bind(R.id.hot_author_content_layout)
        RelativeLayout mHotAuthorContentLayout;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.hot_author_header)
        TextView mHotAuthorHeader;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.simple_footer_text)
        TextView mHotAuthorFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
