package com.monosky.daily.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.monosky.daily.R;
import com.monosky.daily.ui.fragment.adapter.CatalogAdapter;
import com.monosky.daily.module.CatalogData;
import com.monosky.daily.test.GenerateTestDatas;

import java.util.List;

/**
 * 栏目浏览Fragment
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment {

    private GridView mGridView;
    private CatalogAdapter mCatalogAdapter;
    private List<CatalogData> mCatalogDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGridView = (GridView) getView().findViewById(R.id.catalog_gridview);
        mCatalogDatas = GenerateTestDatas.getCatalogData();
        mCatalogAdapter = new CatalogAdapter(getActivity(), mCatalogDatas);
        mGridView.setAdapter(mCatalogAdapter);
    }

}
