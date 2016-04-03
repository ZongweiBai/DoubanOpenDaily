package com.monosky.daily.module;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 作者主页信息接口解析类
 */
public class ProfileData {

    private String follower_url;
    @JSONField(name = "is_followed")
    private boolean followed;
    private String book_do_count;
    private String uid;
    private String following_count;
    private String music_wish_count;
    private String note_count;
    private String movie_collect_url;
    private String movie_wish_count;
    private String movie_do_url;
    @JSONField(name = "is_special_user")
    private boolean special_user;
    private String intro;
    private String movie_collect_count;
    private String note_url;
    private String status_count;
    private String music_do_count;
    private String loc_name;
    private String review_count;
    private String alt;
    private String id;
    private String music_do_url;
    private String music_collect_url;
    private String book_wish_count;
    private String following_url;
    private String loc_id;
    private String name;
    private String music_wish_url;
    private String url;
    private String book_do_url;
    private String album_url;
    private String status_url;
    private String book_wish_url;
    private String movie_wish_url;
    private String book_collect_url;
    private String avatar;
    private String book_collect_count;
    private String follower_count;
    private String movie_do_count;
    private String review_url;
    private String music_collect_count;
    private String album_count;
    private String large_avatar;

    public void setFollower_url(String follower_url) {
        this.follower_url = follower_url;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public void setBook_do_count(String book_do_count) {
        this.book_do_count = book_do_count;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }

    public void setMusic_wish_count(String music_wish_count) {
        this.music_wish_count = music_wish_count;
    }

    public void setNote_count(String note_count) {
        this.note_count = note_count;
    }

    public void setMovie_collect_url(String movie_collect_url) {
        this.movie_collect_url = movie_collect_url;
    }

    public void setMovie_wish_count(String movie_wish_count) {
        this.movie_wish_count = movie_wish_count;
    }

    public void setMovie_do_url(String movie_do_url) {
        this.movie_do_url = movie_do_url;
    }

    public void setSpecial_user(boolean special_user) {
        this.special_user = special_user;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setMovie_collect_count(String movie_collect_count) {
        this.movie_collect_count = movie_collect_count;
    }

    public void setNote_url(String note_url) {
        this.note_url = note_url;
    }

    public void setStatus_count(String status_count) {
        this.status_count = status_count;
    }

    public void setMusic_do_count(String music_do_count) {
        this.music_do_count = music_do_count;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMusic_do_url(String music_do_url) {
        this.music_do_url = music_do_url;
    }

    public void setMusic_collect_url(String music_collect_url) {
        this.music_collect_url = music_collect_url;
    }

    public void setBook_wish_count(String book_wish_count) {
        this.book_wish_count = book_wish_count;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMusic_wish_url(String music_wish_url) {
        this.music_wish_url = music_wish_url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBook_do_url(String book_do_url) {
        this.book_do_url = book_do_url;
    }

    public void setAlbum_url(String album_url) {
        this.album_url = album_url;
    }

    public void setStatus_url(String status_url) {
        this.status_url = status_url;
    }

    public void setBook_wish_url(String book_wish_url) {
        this.book_wish_url = book_wish_url;
    }

    public void setMovie_wish_url(String movie_wish_url) {
        this.movie_wish_url = movie_wish_url;
    }

    public void setBook_collect_url(String book_collect_url) {
        this.book_collect_url = book_collect_url;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBook_collect_count(String book_collect_count) {
        this.book_collect_count = book_collect_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    public void setMovie_do_count(String movie_do_count) {
        this.movie_do_count = movie_do_count;
    }

    public void setReview_url(String review_url) {
        this.review_url = review_url;
    }

    public void setMusic_collect_count(String music_collect_count) {
        this.music_collect_count = music_collect_count;
    }

    public void setAlbum_count(String album_count) {
        this.album_count = album_count;
    }

    public void setLarge_avatar(String large_avatar) {
        this.large_avatar = large_avatar;
    }

    public String getFollower_url() {
        return follower_url;
    }

    public boolean isFollowed() {
        return followed;
    }

    public String getBook_do_count() {
        return book_do_count;
    }

    public String getUid() {
        return uid;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public String getMusic_wish_count() {
        return music_wish_count;
    }

    public String getNote_count() {
        return note_count;
    }

    public String getMovie_collect_url() {
        return movie_collect_url;
    }

    public String getMovie_wish_count() {
        return movie_wish_count;
    }

    public String getMovie_do_url() {
        return movie_do_url;
    }

    public boolean isSpecial_user() {
        return special_user;
    }

    public String getIntro() {
        return intro;
    }

    public String getMovie_collect_count() {
        return movie_collect_count;
    }

    public String getNote_url() {
        return note_url;
    }

    public String getStatus_count() {
        return status_count;
    }

    public String getMusic_do_count() {
        return music_do_count;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public String getReview_count() {
        return review_count;
    }

    public String getAlt() {
        return alt;
    }

    public String getId() {
        return id;
    }

    public String getMusic_do_url() {
        return music_do_url;
    }

    public String getMusic_collect_url() {
        return music_collect_url;
    }

    public String getBook_wish_count() {
        return book_wish_count;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public String getName() {
        return name;
    }

    public String getMusic_wish_url() {
        return music_wish_url;
    }

    public String getUrl() {
        return url;
    }

    public String getBook_do_url() {
        return book_do_url;
    }

    public String getAlbum_url() {
        return album_url;
    }

    public String getStatus_url() {
        return status_url;
    }

    public String getBook_wish_url() {
        return book_wish_url;
    }

    public String getMovie_wish_url() {
        return movie_wish_url;
    }

    public String getBook_collect_url() {
        return book_collect_url;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBook_collect_count() {
        return book_collect_count;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public String getMovie_do_count() {
        return movie_do_count;
    }

    public String getReview_url() {
        return review_url;
    }

    public String getMusic_collect_count() {
        return music_collect_count;
    }

    public String getAlbum_count() {
        return album_count;
    }

    public String getLarge_avatar() {
        return large_avatar;
    }
}
