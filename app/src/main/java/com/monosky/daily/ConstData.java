package com.monosky.daily;

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
