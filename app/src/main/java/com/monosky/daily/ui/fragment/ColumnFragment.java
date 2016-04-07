package com.monosky.daily.ui.fragment;

import android.os.Bundle;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.monosky.daily.R;
import com.monosky.daily.constant.APIConstData;
import com.monosky.daily.module.CatalogData;
import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.ui.fragment.adapter.ColumnAdapter;
import com.monosky.daily.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 栏目浏览Fragment
 */
public class ColumnFragment extends BaseRefreshFragment {

    @Bind(R.id.column_grid_view)
    GridView mColumnGridView;
    private ColumnAdapter mColumnAdapter;
    private List<ColumnEntity> mColumnsEntities = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.fragment_column;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mColumnAdapter = new ColumnAdapter(mColumnsEntities);
        mColumnGridView.setAdapter(mColumnAdapter);

        initSwipeLayout();
    }

    @Override
    protected void onRefreshStarted() {
        String requestUrl = APIConstData.GetColumns;
        new RxVolley.Builder()
                .url(requestUrl)
                .httpMethod(RxVolley.Method.GET)
                .cacheTime(5)
                .shouldCache(true)
                .callback(new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        CatalogData catalogData = JSON.parseObject(t, CatalogData.class);
                        mColumnsEntities.clear();
                        mColumnsEntities.addAll(catalogData.getColumns());
                        mColumnAdapter.notifyDataSetChanged();
                        ToastUtils.showShort(getContext(), "请求成功");
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        ToastUtils.showShort(getContext(), "请求出错");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mSwipeRefresh.setRefreshing(false);
                    }
                })
                .encoding("UTF-8")
                .doTask();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
