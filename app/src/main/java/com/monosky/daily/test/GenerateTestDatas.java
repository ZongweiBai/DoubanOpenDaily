package com.monosky.daily.test;

import com.monosky.daily.module.AuthorData;
import com.monosky.daily.module.CatalogData;
import com.monosky.daily.module.ContentData;
import com.monosky.daily.module.ReplyData;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成测试数据的类
 * Created by jonez_000 on 2015/8/16.
 */
public class GenerateTestDatas {

    /**
     * 生成往期内容数据
     *
     * @return
     */
    public static List<ContentData> getHistoryContents() {
        List<ContentData> contents = new ArrayList<>();
        return contents;
    }

    /**
     * 生成往期刷新内容
     *
     * @return
     */
    public static List<ContentData> loadHistoryContents() {
        List<ContentData> contents = new ArrayList<>();
        return contents;
    }

    /**
     * 生成热门作者数据
     */
    public static List<AuthorData> getAuthors() {
        List<AuthorData> authors = new ArrayList<>();
        authors.add(new AuthorData(AuthorData.SECTION, AuthorData.TYPE_WEEK));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_WEEK));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_WEEK));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_WEEK));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_WEEK));
        authors.add(new AuthorData(AuthorData.SECTION, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul79644553-1.jpg", "静岛", "写过风月小说，写过星座专栏，写过唱片简介，写过电视剧，面善嘴毒，脸尖腿粗", AuthorData.ITEM, AuthorData.TYPE_HOT));
        return authors;
    }

    /**
     * 生成热门作者刷新数据
     *
     * @return
     */
    public static List<AuthorData> loadAuthors() {
        List<AuthorData> authors = new ArrayList<>();
        authors.add(new AuthorData("http://img3.douban.com/icon/ul81006765-2.jpg", "莫吉托", "天秤座，既往选择困难，人格分裂，时而正能量爆棚，时而45度仰望天空。拖着青春的尾巴不愿长大。", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul81006765-2.jpg", "莫吉托", "天秤座，既往选择困难，人格分裂，时而正能量爆棚，时而45度仰望天空。拖着青春的尾巴不愿长大。", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul81006765-2.jpg", "莫吉托", "天秤座，既往选择困难，人格分裂，时而正能量爆棚，时而45度仰望天空。拖着青春的尾巴不愿长大。", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul81006765-2.jpg", "莫吉托", "天秤座，既往选择困难，人格分裂，时而正能量爆棚，时而45度仰望天空。拖着青春的尾巴不愿长大。", AuthorData.ITEM, AuthorData.TYPE_HOT));
        authors.add(new AuthorData("http://img3.douban.com/icon/ul81006765-2.jpg", "莫吉托", "天秤座，既往选择困难，人格分裂，时而正能量爆棚，时而45度仰望天空。拖着青春的尾巴不愿长大。", AuthorData.ITEM, AuthorData.TYPE_HOT));
        return authors;
    }

    /**
     * 生成栏目数据
     *
     * @return
     */
    public static List<CatalogData> getCatalogData() {
        List<CatalogData> catalogs = new ArrayList<>();
        catalogs.add(new CatalogData(null, "热门精选", "收录“一刻”最受欢迎内容"));
        catalogs.add(new CatalogData(null, "打鸡血", "每日段子，提神醒脑"));
        catalogs.add(new CatalogData(null, "洗洗睡", "睡前静静，做个好梦"));
        catalogs.add(new CatalogData(null, "爱美丽", "时尚穿衣护肤美发全搜罗内容"));
        catalogs.add(new CatalogData(null, "打鸡血", "每日段子，提神醒脑"));
        catalogs.add(new CatalogData(null, "洗洗睡", "睡前静静，做个好梦"));
        catalogs.add(new CatalogData(null, "爱美丽", "时尚穿衣护肤美发全搜罗内容"));
        catalogs.add(new CatalogData(null, "打鸡血", "每日段子，提神醒脑"));
        catalogs.add(new CatalogData(null, "洗洗睡", "睡前静静，做个好梦"));
        catalogs.add(new CatalogData(null, "爱美丽", "时尚穿衣护肤美发全搜罗内容"));
        catalogs.add(new CatalogData(null, "打鸡血", "每日段子，提神醒脑"));
        catalogs.add(new CatalogData(null, "洗洗睡", "睡前静静，做个好梦"));
        catalogs.add(new CatalogData(null, "爱美丽", "时尚穿衣护肤美发全搜罗"));
        catalogs.add(new CatalogData(null, "爱美丽", "时尚穿衣护肤美发全搜罗"));
        return catalogs;
    }

    /**
     * 获取评论内容
     *
     * @return
     */
    public static List<ReplyData> getReplyData() {
        List<ReplyData> replyDatas = new ArrayList<>();
        replyDatas.add(new ReplyData("http://img3.douban.com/icon/u53589019-4.jpg", "虚无主义的丰饶", "2分钟前", "现在一直在用写乐长刀研趴格子，速记了再用lamy2000。"));
        replyDatas.add(new ReplyData("http://img3.douban.com/icon/u119322720-1.jpg", "marry", "3分钟前", "英雄 "));
        replyDatas.add(new ReplyData("http://img4.douban.com/icon/u96204122-7.jpg", "丘比的沙拉沙拉", "4分钟前", "早年间用英雄，总觉得不顺手，后来换了许多，还是小时候那只好，"));
        replyDatas.add(new ReplyData("http://img4.douban.com/icon/u54379830-7.jpg", "壹诺", "9分钟前", "lamy在用，不错"));
        replyDatas.add(new ReplyData("http://img3.douban.com/icon/u88708225-3.jpg", "愿世界温柔待你", "18分钟前", "凌美狩猎者真的像你说的那般好用么，网上评价不是太好，想入手一支但又怕买到盗版，为你的手绘点个赞哦！"));
        replyDatas.add(new ReplyData("http://img3.douban.com/icon/u132195174-2.jpg", "夏夜的菠萝包", "20分钟前", "好穷又好想买。"));
        return replyDatas;
    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    public static AuthorData getLogonAuthor() {
        AuthorData authorData = new AuthorData("http://img4.douban.com/icon/ul72417058-6.jpg", "lost");
        return authorData;
    }
}
