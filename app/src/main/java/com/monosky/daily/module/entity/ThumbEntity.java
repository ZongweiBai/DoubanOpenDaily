package com.monosky.daily.module.entity;

import java.io.Serializable;

/**
 * 文章缩略图实体类
 */
public class ThumbEntity implements Serializable {

    private static final long serialVersionUID = 852581443665555149L;

    private int id;
    private String tag_name;
    private String description;
    private ThumbCommonEntity small;
    private ThumbCommonEntity medium;
    private ThumbCommonEntity large;

    public void setMedium(ThumbCommonEntity medium) {
        this.medium = medium;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLarge(ThumbCommonEntity large) {
        this.large = large;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public void setSmall(ThumbCommonEntity small) {
        this.small = small;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ThumbCommonEntity getMedium() {
        return medium;
    }

    public String getDescription() {
        return description;
    }

    public ThumbCommonEntity getLarge() {
        return large;
    }

    public String getTag_name() {
        return tag_name;
    }

    public ThumbCommonEntity getSmall() {
        return small;
    }

    public int getId() {
        return id;
    }

}
