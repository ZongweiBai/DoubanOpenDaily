package com.monosky.daily.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 栏目实体类
 */
public class ColumnEntity {

    private int id;
    private String icon;
    private String description;
    private String name;
    @JSONField(name = "post_total")
    private int postTotal;

    public void setId(int id) {
        this.id = id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostTotal(int postTotal) {
        this.postTotal = postTotal;
    }

    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPostTotal() {
        return postTotal;
    }
}
