package com.monosky.daily.module;

import com.monosky.daily.module.entity.PostsEntity;

import java.util.List;

/**
 * 每日文章接口解析类
 */
public class ContentData {

    private int count;
    private int offset;
    private String date;
    private int total;

    private List<PostsEntity> posts;

    public void setCount(int count) {
        this.count = count;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPosts(List<PostsEntity> posts) {
        this.posts = posts;
    }

    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    public String getDate() {
        return date;
    }

    public int getTotal() {
        return total;
    }

    public List<PostsEntity> getPosts() {
        return posts;
    }

}
