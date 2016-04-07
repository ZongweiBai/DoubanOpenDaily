package com.monosky.daily.module;

import com.monosky.daily.module.entity.ColumnEntity;
import com.monosky.daily.module.entity.PostEntity;

import java.util.List;

/**
 * 栏目文章接口解析类
 */
public class ColumnPostData {

    private int count;
    private ColumnEntity column;
    private int offset;
    private int total;

    private List<PostEntity> posts;

    public void setCount(int count) {
        this.count = count;
    }

    public void setColumn(ColumnEntity column) {
        this.column = column;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPosts(List<PostEntity> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public ColumnEntity getColumn() {
        return column;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }
}
