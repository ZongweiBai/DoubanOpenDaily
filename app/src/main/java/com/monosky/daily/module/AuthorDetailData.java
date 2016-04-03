package com.monosky.daily.module;

import com.monosky.daily.module.entity.AuthorEntity;
import com.monosky.daily.module.entity.PostEntity;

import java.util.List;

/**
 * 作者详情接口解析类
 */
public class AuthorDetailData {

    private int count;

    private AuthorEntity author;
    private int offset;
    private int total;

    private List<PostEntity> posts;

    public void setCount(int count) {
        this.count = count;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
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

    public AuthorEntity getAuthor() {
        return author;
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
