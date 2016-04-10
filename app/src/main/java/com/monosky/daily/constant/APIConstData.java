package com.monosky.daily.constant;

/**
 * 接口地址
 */
public class APIConstData {

    /**
     * 根据日期查询文章
     * https://moment.douban.com/api/stream/date/2015-09-24
     */
    public static String GetContentByDate = "https://moment.douban.com/api/stream/date/";

    /**
     * 栏目总览
     * https://moment.douban.com/api/columns
     */
    public static String GetColumns = "https://moment.douban.com/api/columns";

    /**
     * 推荐作者
     */
    public static String GetRecAuthor = "https://moment.douban.com/api/auth_authors/rec";

    /**
     * 热门作者
     * https://moment.douban.com/api/auth_authors/all?count=20&start=20
     */
    public static String GetHotAuthor = "https://moment.douban.com/api/auth_authors/all?count=20&start=";

    /**
     * 作者信息
     */
    public static String GetAuthorDetail = "https://moment.douban.com/api/author/{authorId}/posts";

    /**
     * 作者更多文章信息
     * https://moment.douban.com/api/author/1992643/posts?count=10&max_id=109408
     */
    public static String GetAuthorDetailMore = "https://moment.douban.com/api/author/{authorId}/posts?count=10&max_id={maxId}";

    /**
     * 作者主页信息
     */
    public static String GetProfile = "https://moment.douban.com/api/user/{authorId}/profile";

    /**
     * 栏目文章列表及翻页
     * https://moment.douban.com/api/column/26/posts?max_id=124073
     */
    public static String GetColumnPosts = "https://moment.douban.com/api/column/{columnId}/posts?max_id={maxId}";

    /**
     * 文章热门评论列表
     * https://moment.douban.com/api/post/{postId}/popular_comments
     */
    public static String GetPopularComments = "https://moment.douban.com/api/post/{postId}/popular_comments";

    /**
     * 文章评论列表
     * https://moment.douban.com/api/post/125195/comments?count=20&max_id=406309
     */
    public static String GetComments = "https://moment.douban.com/api/post/{postId}/comments?count=20&max_id={maxId}";

}
