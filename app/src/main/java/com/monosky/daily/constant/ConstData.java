package com.monosky.daily.constant;

import java.io.Serializable;

/**
 * 保存一些常量
 * Created by jonez_000 on 2015/8/20.
 */
public class ConstData implements Serializable {

    public static final Boolean isDebug = true;
    public static final String TAG = "DoubanOpenDaily";

    /**
     * 广播常量
     */
    public static String BROADCAST_LOGON = "BROADCAST_LOGON";
    public static String BROADCAST_LOGOFF = "BROADCAST_LOGOFF";

    /**
     * 刷新或者加载更多
     */
    public static String REQUEST_REFRESH = "refresh";
    public static String REQUEST_LOAD = "load";

    /**
     * 文章列表的类型
     * 1 纯文字/文字+图片
     * 2 大图
     * 3 多张图片
     * 98 头部
     * 99 底部
     */
    public static int POST_LIST = 1;
    public static int POST_IMAGE = 2;
    public static int POST_IMG_BOX = 3;
    public static int HEADER = 98;
    public static int FOOTER = 99;

    /**
     * SharePreference常量
     */
    public static String FONT_SIZE_SETTING = "FONT_SIZE_SETTING";
    public static String LOGON_ACCOUNT = "LOGON_ACCOUNT";
    public static String LOGON_PWD = "LOGON_PWD";

    public static String ACCOUNT = "monosky";
    public static String PASSWORD = "123456";
    public static String LOGON_TYPE_REGISTER = "1";
    public static String LOGON_TYPE_FORGET = "2";
    public static String MAIN_PAGE_TYPE_OTHER = "1";
    public static String MAIN_PAGE_TYPE_SELF = "2";
}
