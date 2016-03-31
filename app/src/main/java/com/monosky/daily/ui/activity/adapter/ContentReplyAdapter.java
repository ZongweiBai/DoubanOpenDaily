package com.monosky.daily.ui.activity.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.ContentReplyActivity;
import com.monosky.daily.module.ReplyData;
import com.monosky.daily.util.ImageLoaderOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章评论Adapter
 * Created by jonez_000 on 2015/8/20.
 */
public class ContentReplyAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReplyData> mReplyDatas;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private View.OnClickListener mReplyOnClickListener;
    private Map<String, View> viewMap = new HashMap<>();

    public ContentReplyAdapter(Context mContext, List<ReplyData> mReplyDatas) {
        this.mContext = mContext;
        this.mReplyDatas = mReplyDatas;
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
     * @param view
     * @param tag
     */
    private void showOperationWindow(View view, Object tag) {
        final int position = (int) tag;
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.reply_operation, null);
//        contentView.getBackground().setAlpha(0);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(
                R.color.app_main_bg));
//        // 打开窗口时设置窗体背景透明度
//        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
//        lp.alpha = 1f;
//        ((Activity) mContext).getWindow().setAttributes(lp);
        popupWindow.update();

        TextView mReplyCopy = (TextView) contentView.findViewById(R.id.reply_oper_copy);
        TextView mReplyReport = (TextView) contentView.findViewById(R.id.reply_oper_report);
        mReplyCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", mReplyDatas.get(position).getReplyContent());
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
        int popupHeight =  contentView.getMeasuredHeight();
        int[] location = new int[2];
        View viewShow = viewMap.get(String.valueOf(position));
        viewShow.getLocationOnScreen(location);
        if(ContentReplyActivity.mContentReplyActivity.getmRelpyListView().getLastVisiblePosition() == position) {
            popupWindow.showAtLocation(viewShow, Gravity.NO_GRAVITY,
                    popupWidth/2, location[1]-popupHeight);
        } else {
            popupWindow.showAsDropDown(viewShow,
                    0, 0);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_content_reply_item, null);
            viewHolder.mAvatar = (ImageView) convertView.findViewById(R.id.reply_avatar);
            viewHolder.mAuthor = (TextView) convertView.findViewById(R.id.reply_author);
            viewHolder.mReplyTime = (TextView) convertView.findViewById(R.id.reply_time);
            viewHolder.mReplyContent = (TextView) convertView.findViewById(R.id.reply_content);
            viewHolder.mReplyAll = (TextView) convertView.findViewById(R.id.reply_all);
            viewHolder.mReplyLayout = (LinearLayout) convertView.findViewById(R.id.reply_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ReplyData replyData = getItem(position);
        imageLoader.displayImage(replyData.getRelpyAuthorImg(), viewHolder.mAvatar, ImageLoaderOption.optionInfoImage(R.mipmap.ic_default_avatar_author));
        viewHolder.mAuthor.setText(replyData.getReplyAuthor());
        viewHolder.mReplyTime.setText(replyData.getReplyTime());
        viewHolder.mReplyContent.setText(replyData.getReplyContent());
        if(position == getCount()-1) {
            viewHolder.mReplyAll.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mReplyAll.setVisibility(View.GONE);
        }
        viewHolder.mReplyLayout.setTag(R.id.reply_layout, position);
        viewHolder.mReplyLayout.setOnClickListener(mReplyOnClickListener);
        viewMap.put(String.valueOf(position), viewHolder.mAuthor);
        return convertView;
    }

    @Override
    public int getCount() {
        return mReplyDatas.size();
    }

    @Override
    public ReplyData getItem(int position) {
        return mReplyDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        ImageView mAvatar;
        TextView mAuthor;
        TextView mReplyTime;
        TextView mReplyContent;
        TextView mReplyAll;
        LinearLayout mReplyLayout;
    }
}
