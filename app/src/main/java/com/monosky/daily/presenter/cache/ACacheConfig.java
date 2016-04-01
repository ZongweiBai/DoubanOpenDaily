package com.monosky.daily.presenter.cache;

import com.monosky.daily.constant.APIConstData;

import java.util.ArrayList;
import java.util.List;

/**
 * ACache缓存配置，配置可以缓存数据的接口
 * User: Baymin
 * Date: 2015-11-13
 * Time: 00:23
 */
public class ACacheConfig {

    public static List<String> cacheInterface;

    static {
        cacheInterface = new ArrayList<>();
        cacheInterface.add(APIConstData.GetContentByDate);
    }
}
