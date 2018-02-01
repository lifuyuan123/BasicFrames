package com.example.lfy.basicframes.http;

import com.example.lfy.basicframes.entity.GankBean;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;

/**
 * author:ggband
 * data:2018/2/1 000115:23
 * email:ggband520@163.com
 * desc:rxCache   缓存接口   需要缓存的就在此定义接口
 */

public interface CacheProvider {

    //获取干货图片  EvictProvider  new出新对象传入布尔值  ture代表不缓存 直接网络请求
    // false代表用缓存   DynamicKey  new出新对象  传入page数  缓存的页面tag
    @LifeCache(duration = 1, timeUnit = TimeUnit.MINUTES)//缓存时间
    Observable<GankBean> getGank( Observable<GankBean> dataGank,EvictProvider evictDynamicKey,DynamicKey key);
}
