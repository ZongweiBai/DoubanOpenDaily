package com.monosky.daily.module;

import java.io.Serializable;

/**
 * 热门作者实体类
 * Created by jonez_000 on 2015/8/18.
 */
public class AuthorData implements Serializable {

    public static final int ITEM = 0;
    public static final int SECTION = 1;

    public static final int TYPE_WEEK = 0;
    public static final int TYPE_HOT = 1;

    private String authorImg;
    private String authorName;
    private String authorLabel;
    private int sortType;    //0:真实内容 1：内容分类
    private int type;    //0:本周推荐 1：热门作者

    public AuthorData() {
    }

    public AuthorData(int sortType, int type) {
        this.sortType = sortType;
        this.type = type;
    }

    public AuthorData(String authorImg, String authorName) {
        this.authorImg = authorImg;
        this.authorName = authorName;
    }

    public AuthorData(String authorImg, String authorName, String authorLabel, int sortType, int type) {
        this.authorImg = authorImg;
        this.authorName = authorName;
        this.authorLabel = authorLabel;
        this.sortType = sortType;
        this.type = type;
    }

    public String getAuthorImg() {
        return authorImg;
    }

    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLabel() {
        return authorLabel;
    }

    public void setAuthorLabel(String authorLabel) {
        this.authorLabel = authorLabel;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
