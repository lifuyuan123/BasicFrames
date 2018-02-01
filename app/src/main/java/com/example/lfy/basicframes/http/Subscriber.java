package com.example.lfy.basicframes.http;

import android.content.Context;

import com.example.lfy.basicframes.application.MyApplication;
import com.example.lfy.basicframes.entity.AndroidBean;
import com.example.lfy.basicframes.entity.BannerModel;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.entity.IosBean;
import com.example.lfy.basicframes.entity.RestBean;
import com.example.lfy.basicframes.utill.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.spi.CharsetProvider;
import java.util.Map;

import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import rx.functions.Action1;


/**
 * author:ggband
 * data:2017/12/13 001311:17
 * email:ggband520@163.com
 * desc:
 */

public class Subscriber extends ObjectLoad{

    //请求接口
    private apiServer apiserver;

    private CacheProvider cacheProvider;

    //初始化Subscriber后就通过retrofit将apiServer的实例返回
    public Subscriber() {
        apiserver=ApiClient.getInstence().create(apiServer.class);
        //缓存的服务
        cacheProvider = new RxCache.Builder()
                .persistence(Utils.getContext().getFilesDir(), new GsonSpeaker())
                .using(CacheProvider.class);
    }


    //通过实例此类得到apiserver接口
    private static Subscriber subscriber;
    public static Subscriber getInstemce(){
        if (subscriber==null){
            subscriber=new Subscriber();
        }
        return subscriber;
    }


    //获取干货图片的方法（不缓存）
    public void getGank(String cout,String page,String type, ApiCallBack<GankBean> callBack){
        //完成一次消息订阅
        observe( apiserver.getGank(cout,page,type)).subscribe(callBack );//订阅
    }

    //获取干货图片的方法（缓存）
    // EvictProvider  new出新对象传入布尔值  ture代表不缓存 直接网络请求
    // false代表用缓存   DynamicKey  new出新对象  传入page数  缓存的页面tag
    public void getGank(String cout,String page,String type,boolean updata, ApiCallBack<GankBean> callBack){
        //完成一次消息订阅
        observe( cacheProvider.getGank(   apiserver.getGank(cout,page,type)   ,  new EvictProvider(updata),  new DynamicKey(page) )  ).subscribe(callBack );//订阅
    }



    //获取干货图片的方法
    public void getGankAndroid(String cout,String page,String type, ApiCallBack<AndroidBean> callBack){
        //完成一次消息订阅
        observe( apiserver.getGankAndroid(cout,page,type)).subscribe(callBack );//订阅
    }

    //获取干货图片的方法
    public void getGankiOS(String cout,String page,String type, ApiCallBack<IosBean> callBack){
        //完成一次消息订阅
        observe( apiserver.getGankOIS(cout,page,type)).subscribe(callBack );//订阅
    }

    //获取干货图片的方法
    public void getGankRest(String cout,String page,String type, ApiCallBack<RestBean> callBack){
        //完成一次消息订阅
        observe( apiserver.getGankRest(cout,page,type)).subscribe(callBack );//订阅
    }



}
