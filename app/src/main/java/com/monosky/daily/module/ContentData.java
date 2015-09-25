package com.monosky.daily.module;

import java.io.Serializable;

/**
 * 文章实体类
 * Created by jonez_000 on 2015/8/16.
 */
public class ContentData implements Serializable {

    public static final int ITEM = 0;
    public static final int SECTION = 1;

    private String contentTitle;
    private String contentLabel;
    private String contentCatlog;
    private String contentImg;
    private int sortType;    //0:真实内容 1：内容分类（暂时以日期分类）
    private String contentTime;

    public ContentData() {
    }

    public ContentData(String contentTime, int sortType) {
        this.contentTime = contentTime;
        this.sortType = sortType;
    }

    public ContentData(String contentTitle, String contentLabel, String contentCatlog, String getContentImg, String contentTime, int sortType) {
        this.contentTitle = contentTitle;
        this.contentLabel = contentLabel;
        this.contentCatlog = contentCatlog;
        this.contentImg = getContentImg;
        this.contentTime = contentTime;
        this.sortType = sortType;
    }

    public ContentData(String contentTitle, String contentLabel, String contentCatlog, String getContentImg) {
        this.contentTitle = contentTitle;
        this.contentLabel = contentLabel;
        this.contentCatlog = contentCatlog;
        this.contentImg = getContentImg;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentLabel() {
        return contentLabel;
    }

    public void setContentLabel(String contentLabel) {
        this.contentLabel = contentLabel;
    }

    public String getContentCatlog() {
        return contentCatlog;
    }

    public void setContentCatlog(String contentCatlog) {
        this.contentCatlog = contentCatlog;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getContentTime() {
        return contentTime;
    }

    public void setContentTime(String contentTime) {
        this.contentTime = contentTime;
    }
}
