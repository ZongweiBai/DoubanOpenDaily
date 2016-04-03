package com.monosky.daily.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 作者实体类
 */
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = -4526786472961708980L;

    @JSONField(name = "is_followed")
    private boolean followed;
    @JSONField(name = "editor_notes")
    private String editorNotes;
    private String uid;
    private String resume;
    private String url;
    private String avatar;
    private String name;
    @JSONField(name = "is_special_user")
    private boolean specialUser;
    @JSONField(name = "last_post_time")
    private String lastPostTime;
    @JSONField(name = "n_posts")
    private int nposts;
    private String alt;
    @JSONField(name = "large_avatar")
    private String largeAvatar;
    private String id;
    @JSONField(name = "is_auth_author")
    private boolean authAuthor;

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public void setEditorNotes(String editorNotes) {
        this.editorNotes = editorNotes;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialUser(boolean specialUser) {
        this.specialUser = specialUser;
    }

    public void setLastPostTime(String lastPostTime) {
        this.lastPostTime = lastPostTime;
    }

    public void setNposts(int nposts) {
        this.nposts = nposts;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setLargeAvatar(String largeAvatar) {
        this.largeAvatar = largeAvatar;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthAuthor(boolean authAuthor) {
        this.authAuthor = authAuthor;
    }

    public boolean isFollowed() {
        return followed;
    }

    public String getEditorNotes() {
        return editorNotes;
    }

    public String getUid() {
        return uid;
    }

    public String getResume() {
        return resume;
    }

    public String getUrl() {
        return url;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public boolean isSpecialUser() {
        return specialUser;
    }

    public String getLastPostTime() {
        return lastPostTime;
    }

    public int getNposts() {
        return nposts;
    }

    public String getAlt() {
        return alt;
    }

    public String getLargeAvatar() {
        return largeAvatar;
    }

    public String getId() {
        return id;
    }

    public boolean isAuthAuthor() {
        return authAuthor;
    }
}
