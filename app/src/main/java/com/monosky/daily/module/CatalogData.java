package com.monosky.daily.module;

import com.monosky.daily.module.entity.ColumnEntity;

import java.util.List;

/**
 * 栏目分类接口解析类
 */
public class CatalogData {

    private int total;

    private List<ColumnEntity> columns;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public int getTotal() {
        return total;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

}
