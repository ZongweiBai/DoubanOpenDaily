package com.monosky.daily.module.entity;

import java.io.Serializable;

/**
 * 缩略图通用实体类
 */
public class ThumbCommonEntity implements Serializable {

    private static final long serialVersionUID = -657008840713642886L;

    private String url;
    private int width;
    private int height;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
