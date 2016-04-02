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
     * 热门作者
     */
    public static String GetRecAuthor = "https://moment.douban.com/api/auth_authors/rec";

    /**
     * 全部作者
     * https://moment.douban.com/api/auth_authors/all?count=20&start=20
     */
    public static String GetAuthor = "https://moment.douban.com/api/auth_authors/all?count=20&start=";
}
