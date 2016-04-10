package com.monosky.daily.ui.activity.post;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.monosky.daily.BaseApplication;
import com.monosky.daily.R;
import com.monosky.daily.module.entity.CommentEntity;
import com.monosky.daily.module.entity.PostEntity;
import com.monosky.daily.ui.activity.BaseRefreshActivity;
import com.monosky.daily.ui.activity.post.adapter.PostCommentsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 文章评论
 */
public class PostCommentsActivity extends BaseRefreshActivity {

    public static PostCommentsActivity mContentReplyActivity;
    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.comments_recycler_view)
    RecyclerView mCommentsRecyclerView;
    private PostEntity mPostEntity;
    private PostCommentsAdapter mPostCommentsAdapter;
    private List<CommentEntity> mCommentEntities = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getLayout() {
        return R.layout.activity_post_comments;
    }

    @Override
    protected void initData() {
        mPostEntity = (PostEntity) getIntent().getSerializableExtra("postEntity");
    }

    @Override
    protected void initViews() {
        mToolBar.setTitle(getResources().getString(R.string.title_comments, mPostEntity.getComments_count()));
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onRefreshStarted() {

    }

    public static void gotoPostComments(PostEntity postEntity) {
        Intent intent = new Intent(BaseApplication.getContext(), PostCommentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("postEntity", postEntity);
        BaseApplication.getContext().startActivity(intent);
    }
}
