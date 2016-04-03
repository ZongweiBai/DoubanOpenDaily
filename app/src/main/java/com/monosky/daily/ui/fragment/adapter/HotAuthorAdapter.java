package com.monosky.daily.ui.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.AuthorsEntity;
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

    private List<AuthorsEntity> mAuthorsEntities = new ArrayList<>();
    private Context mContext;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public HotAuthorAdapter(List<AuthorsEntity> list) {
        this.mAuthorsEntities = list;
        this.mContext = BaseApplication.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == -1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_author_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = View.inflate(parent.getContext(), R.layout.item_hot_author, null);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == -1) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.mHotAuthorFooter.setText(mContext.getString(R.string.author_all));
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            AuthorsEntity authorData = mAuthorsEntities.get(position);
            itemHolder.mAuthorName.setText(authorData.getName());
            itemHolder.mAuthorLabel.setText(authorData.getEditorNotes());
            imageLoader.displayImage(authorData.getAvatar(), itemHolder.mAuthorImg, ImageLoaderOption.optionInfoImage(R.mipmap.ic_empty_light));
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
        if (TextUtils.isEmpty(mAuthorsEntities.get(position).getName())) {
            return -1;
        }
        return 0;
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
        @Bind(R.id.hot_author_footer)
        TextView mHotAuthorFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
