package com.monosky.daily.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.monosky.daily.R;
import com.monosky.daily.ui.activity.adapter.AuthorAdapter;
import com.monosky.daily.module.AuthorData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.test.GenerateTestDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者详情
 */
public class AuthorActivity extends BaseActivity {

    private static AuthorActivity mInstance;
    private TextView mTopTitle;
    private RecyclerView mAuthorRecycleView;
    private AuthorAdapter mAuthorAdapter;
    private AuthorData mAuthorData;
    private List<ContentData> mAuthorContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        mInstance = this;

        mAuthorData = (AuthorData) getIntent().getSerializableExtra("authorData");

        getViews();
        setViews();
    }

    private void getViews() {
        mTopTitle = (TextView) findViewById(R.id.actionbar_title);
        mAuthorRecycleView = (RecyclerView) findViewById(R.id.author_recyclerview);
    }

    private void setViews() {
        mTopTitle.setText(getResources().getString(R.string.author_page_title, mAuthorData.getAuthorName()));
        mTopTitle.setOnClickListener(mAuthoOnClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAuthorRecycleView.setLayoutManager(layoutManager);
        mAuthorContents = new ArrayList<>();
        mAuthorAdapter = new AuthorAdapter(mInstance, mAuthorData, mAuthorContents);
        mAuthorRecycleView.setAdapter(mAuthorAdapter);

    }

    View.OnClickListener mAuthoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.actionbar_title:
                    mInstance.finish();
                    break;
            }
        }
    };

}
