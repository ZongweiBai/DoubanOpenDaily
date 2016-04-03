package com.monosky.daily.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 文章实体类
 */
public class PostEntity implements Serializable {

    private static final long serialVersionUID = -3013120344884696524L;

    private int display_style;
    private boolean is_editor_choice;
    private String published_time;
    private String original_url;
    private String url;
    private String short_url;
    private boolean is_liked;

    private AuthorEntity author;
    private String column;
    private int app_css;
    @JSONField(name = "abstract")
    private String abstractX;
    private String date;
    private int like_count;
    private int comments_count;
    private String created_time;
    private String title;
    private String share_pic_url;
    private String type;
    private int id;
    private List<ThumbEntity> thumbs;

    public void setDisplay_style(int display_style) {
        this.display_style = display_style;
    }

    public void setIs_editor_choice(boolean is_editor_choice) {
        this.is_editor_choice = is_editor_choice;
    }

    public void setPublished_time(String published_time) {
        this.published_time = published_time;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setApp_css(int app_css) {
        this.app_css = app_css;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShare_pic_url(String share_pic_url) {
        this.share_pic_url = share_pic_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThumbs(List<ThumbEntity> thumbs) {
        this.thumbs = thumbs;
    }

    public int getDisplay_style() {
        return display_style;
    }

    public boolean isIs_editor_choice() {
        return is_editor_choice;
    }

    public String getPublished_time() {
        return published_time;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public String getUrl() {
        return url;
    }

    public String getShort_url() {
        return short_url;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public String getColumn() {
        return column;
    }

    public int getApp_css() {
        return app_css;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public String getDate() {
        return date;
    }

    public int getLike_count() {
        return like_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getTitle() {
        return title;
    }

    public String getShare_pic_url() {
        return share_pic_url;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public List<ThumbEntity> getThumbs() {
        return thumbs;
    }
}
