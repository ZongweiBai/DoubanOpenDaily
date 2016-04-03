package com.monosky.daily.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.adapter.ContentReplyAdapter;
import com.monosky.daily.module.ReplyData;
import com.monosky.daily.test.GenerateTestDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章评论
 */
public class PostReplyActivity extends BaseActivity {

    public static PostReplyActivity mContentReplyActivity;

    private TextView mTopTitle;
    private ListView mRelpyListView;
    private TextView mReplyBottom;
    private ContentReplyAdapter mRelpyAdapter;
    private List<ReplyData> mReplyDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_reply);
        mContentReplyActivity = this;

        getViews();
        setViews();
    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mRelpyListView = (ListView) findViewById(R.id.reply_listview);
        mReplyBottom = (TextView) findViewById(R.id.reply_tip);
    }

    private void setViews() {
        mReplyDataList = GenerateTestDatas.getReplyData();
        int replyNum = mReplyDataList.size();
        if(replyNum > 0) {
            mTopTitle.setText(replyNum+"条评论");
        } else {
            mTopTitle.setText("暂无评论");
        }
        mTopTitle.setOnClickListener(replyOnClick);
        mReplyBottom.setOnClickListener(replyOnClick);

        mRelpyAdapter = new ContentReplyAdapter(this, mReplyDataList);
        mRelpyListView.setAdapter(mRelpyAdapter);
        mRelpyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 弹出选择框（popupwindow）
            }
        });
    }

    private View.OnClickListener replyOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.actionbar_title:
                    PostReplyActivity.this.finish();
                    break;
                case R.id.reply_tip:
                    // 跳转到登录页，登录成功后关闭登录页
                    Intent intent = new Intent(PostReplyActivity.this, LogonActivity.class);
                    PostReplyActivity.this.startActivity(intent);
                    break;
            }
        }
    };

    public ListView getmRelpyListView() {
        return mRelpyListView;
    }
}
