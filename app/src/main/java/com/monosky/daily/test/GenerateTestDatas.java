package com.monosky.daily.test;

import com.monosky.daily.module.ReplyData;
import com.monosky.daily.module.entity.AuthorEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成测试数据的类
 * Created by jonez_000 on 2015/8/16.
 */
public class GenerateTestDatas {

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
    public static AuthorEntity getLogonAuthor() {
//        AuthorData authorData = new AuthorData("http://img4.douban.com/icon/ul72417058-6.jpg", "lost");
        return null;
    }
}
