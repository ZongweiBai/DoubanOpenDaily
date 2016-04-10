package com.monosky.daily.module.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 评论实体类
 */
public class CommentEntity implements Serializable {

    private static final long serialVersionUID = -8850768928151503277L;

    @JSONField(name = "ref_comment")
    private CommentEntity refComment;
    private AuthorEntity author;
    private String content;
    @JSONField(name = "post_id")
    private int postId;
    @JSONField(name = "vote_count")
    private int voteCount;
    private boolean voted;
    @JSONField(name = "created_time")
    private String createdTime;
    private int id;

    public void setRefComment(CommentEntity refComment) {
        this.refComment = refComment;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CommentEntity getRefComment() {
        return refComment;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public int getPostId() {
        return postId;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVoted() {
        return voted;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public int getId() {
        return id;
    }
}
