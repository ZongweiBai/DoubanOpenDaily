package com.monosky.daily.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.monosky.daily.module.entity.CommentEntity;

import java.util.List;

/**
 * 评论接口数据解析类
 */
public class CommentData {

    private int count;
    @JSONField(name = "post_id")
    private int postId;
    private String hint;
    private int start;
    private int total;

    private List<CommentEntity> comments;

    public void setCount(int count) {
        this.count = count;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public int getCount() {
        return count;
    }

    public int getPostId() {
        return postId;
    }

    public String getHint() {
        return hint;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

}
