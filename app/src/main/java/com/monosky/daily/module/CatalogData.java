package com.monosky.daily.module;

import java.io.Serializable;

/**
 * 栏目分类
 * Created by jonez_000 on 2015/8/18.
 */
public class CatalogData implements Serializable {

    private String catalogImg;
    private String catalogTitle;
    private String catalogLabel;

    public CatalogData() {
    }

    public CatalogData(String catalogImg, String catalogTitle, String catalogLabel) {
        this.catalogImg = catalogImg;
        this.catalogTitle = catalogTitle;
        this.catalogLabel = catalogLabel;
    }

    public String getCatalogImg() {
        return catalogImg;
    }

    public void setCatalogImg(String catalogImg) {
        this.catalogImg = catalogImg;
    }

    public String getCatalogTitle() {
        return catalogTitle;
    }

    public void setCatalogTitle(String catalogTitle) {
        this.catalogTitle = catalogTitle;
    }

    public String getCatalogLabel() {
        return catalogLabel;
    }

    public void setCatalogLabel(String catalogLabel) {
        this.catalogLabel = catalogLabel;
    }
}
