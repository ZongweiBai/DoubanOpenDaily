package com.monosky.daily.module;

import java.io.Serializable;

/**
 * 文章评论实体类
 * Created by jonez_000 on 2015/8/20.
 */
public class ReplyData implements Serializable {

    private String relpyAuthorImg;
    private String replyAuthor;
    private String replyTime;
    private String replyContent;

    public ReplyData() {
    }

    public ReplyData(String relpyAuthorImg, String replyAuthor, String replyTime, String replyContent) {
        this.relpyAuthorImg = relpyAuthorImg;
        this.replyAuthor = replyAuthor;
        this.replyTime = replyTime;
        this.replyContent = replyContent;
    }

    public String getRelpyAuthorImg() {
        return relpyAuthorImg;
    }

    public void setRelpyAuthorImg(String relpyAuthorImg) {
        this.relpyAuthorImg = relpyAuthorImg;
    }

    public String getReplyAuthor() {
        return replyAuthor;
    }

    public void setReplyAuthor(String replyAuthor) {
        this.replyAuthor = replyAuthor;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
