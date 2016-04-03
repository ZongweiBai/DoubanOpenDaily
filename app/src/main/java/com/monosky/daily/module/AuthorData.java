package com.monosky.daily.module;

import com.monosky.daily.module.entity.AuthorEntity;

import java.util.List;

/**
 * 作者列表接口解析类
 */
public class AuthorData {

    private int count;
    private int start;
    private int total;

    private List<AuthorEntity> authors;

    public void setCount(int count) {
        this.count = count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }
}
