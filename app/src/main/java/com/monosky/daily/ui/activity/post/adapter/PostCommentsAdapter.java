package com.monosky.daily.ui.activity.post.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.monosky.daily.R;
import com.monosky.daily.constant.ConstData;
import com.monosky.daily.module.entity.CommentEntity;
import com.monosky.daily.ui.view.readmoretextview.ReadMoreTextView;
import com.monosky.daily.util.DateUtils;
import com.monosky.daily.util.ImageLoaderOption;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文章评论Adapter
 * Created by baymin on 2015/8/20.
 */
public class PostCommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommentEntity> mCommentEntities;
    private Integer hotCommentsCount = 0;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mReplyOnClickListener;
    private Map<String, View> viewMap = new HashMap<>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PostCommentsAdapter(Context mContext, List<CommentEntity> mCommentEntities, Integer hotCommentsCount) {
        this.mContext = mContext;
        this.mCommentEntities = mCommentEntities;
        this.hotCommentsCount = hotCommentsCount;
        this.mReplyOnClickListener = replyOnClickListener;
    }

    View.OnClickListener replyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.reply_layout:
                    showOperationWindow(v, v.getTag(R.id.reply_layout));
                    break;
            }
        }
    };

    /**
     * 显示评论的可操作popupwindow
     *
     * @param view
     * @param tag
     */
    private void showOperationWindow(View view, Object tag) {
        final int position = (int) tag;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.reply_operation, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.app_main_bg));
        popupWindow.update();

        TextView mReplyCopy = (TextView) contentView.findViewById(R.id.reply_oper_copy);
        TextView mReplyReport = (TextView) contentView.findViewById(R.id.reply_oper_report);
        mReplyCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", mCommentEntities.get(position).getContent());
                cmb.setPrimaryClip(myClip);
                popupWindow.dismiss();
                Toast.makeText(mContext, "已复制到剪切板", Toast.LENGTH_SHORT).show();
            }
        });
        mReplyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        // 设置好参数之后再show
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = contentView.getMeasuredWidth();
        int popupHeight = contentView.getMeasuredHeight();
        int[] location = new int[2];
        View viewShow = viewMap.get(String.valueOf(position));
        viewShow.getLocationOnScreen(location);
//        if(PostCommentsActivity.mContentReplyActivity.getmRelpyListView().getLastVisiblePosition() == position) {
        popupWindow.showAtLocation(viewShow, Gravity.NO_GRAVITY,
                popupWidth / 2, location[1] - popupHeight);
//        } else {
        popupWindow.showAsDropDown(viewShow,
                0, 0);
//        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ConstData.FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_simple_footer, parent, false);
            return new FooterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (ConstData.FOOTER == getItemViewType(position)) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.mSimpleFooterText.setText(mContext.getText(R.string.comments_all));
        } else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            CommentEntity commentEntity = mCommentEntities.get(position);
            if (TextUtils.isEmpty(commentEntity.getAuthor().getAvatar())) {
                itemViewHolder.mReplyAvatar.setImageResource(R.mipmap.ic_default_avatar_author);
            } else {
                imageLoader.displayImage(commentEntity.getAuthor().getAvatar(), itemViewHolder.mReplyAvatar, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_author));
            }
            itemViewHolder.mReplyAuthor.setText(commentEntity.getAuthor().getName());
            itemViewHolder.mVoteCount.setText(String.valueOf(commentEntity.getVoteCount()));
            try {
                itemViewHolder.mReplyTime.setText(DateUtils.fromToday(sdf.parse(commentEntity.getCreatedTime())));
            } catch (ParseException e) {
                e.printStackTrace();
                itemViewHolder.mReplyTime.setText(commentEntity.getCreatedTime());
            }
            if (commentEntity.getRefComment() != null) {
                itemViewHolder.mRefComment.setVisibility(View.VISIBLE);
                String expandText = "<font color=\"#00BFA2\">" + commentEntity.getRefComment().getAuthor().getName() + "</font>&nbsp;&nbsp;";
                expandText = expandText + commentEntity.getRefComment().getContent();
                itemViewHolder.mExpandableText.setText(Html.fromHtml(expandText));
            } else {
                itemViewHolder.mRefComment.setVisibility(View.GONE);
            }
            itemViewHolder.mReplyContent.setText(commentEntity.getContent());
        }
    }

    @Override
    public long getHeaderId(int position) {
        if (position < hotCommentsCount) {
            return 0;
        }
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        if (position < hotCommentsCount) {
            headerHolder.mCommentHeader.setText(mContext.getString(R.string.hot_comments));
        } else {
            headerHolder.mCommentHeader.setText(mContext.getString(R.string.all_comments));
        }
    }

    @Override
    public int getItemCount() {
        return mCommentEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mCommentEntities.get(position) == null) {
            return ConstData.FOOTER;
        }
        return position;
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.simple_footer_text)
        TextView mSimpleFooterText;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.reply_avatar)
        ImageView mReplyAvatar;
        @Bind(R.id.reply_author)
        TextView mReplyAuthor;
        @Bind(R.id.vote_count)
        TextView mVoteCount;
        @Bind(R.id.reply_time)
        TextView mReplyTime;
        @Bind(R.id.expandable_text)
        TextView mExpandableText;
        @Bind(R.id.expand_collapse)
        ImageButton mExpandCollapse;
        @Bind(R.id.ref_comment)
        ExpandableTextView mRefComment;
        @Bind(R.id.reply_content)
        ReadMoreTextView mReplyContent;
        @Bind(R.id.reply_layout)
        LinearLayout mReplyLayout;

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comment_header)
        TextView mCommentHeader;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
