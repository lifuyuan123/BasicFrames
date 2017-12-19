package com.example.lfy.basicframes.http;

import com.example.lfy.basicframes.entity.AndroidBean;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.entity.IosBean;
import com.example.lfy.basicframes.entity.RestBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;


/**
 * author:ggband
 * data:2017/12/13 001311:17
 * email:ggband520@163.com
 * desc:
 */

public class Subscriber extends ObjectLoad{

    //请求接口
    private apiServer apiserver;

    //初始化Subscriber后就通过retrofit将apiServer的实例返回
    public Subscriber() {
        apiserver=ApiClient.getInstence().create(apiServer.class);
    }


    //通过实例此类得到apiserver接口
    private static Subscriber subscriber;
    public static Subscriber getInstemce(){
        if (subscriber==null){
            subscriber=new Subscriber();
        }
        return subscriber;
    }


    //获取干货图片的方法
    public void getGank(String cout,String page,String type, ApiCallBack<GankBean> callBack){
        //完成一次消息订阅
        observe( apiserver.getGank(cout,page,type)).subscribe(callBack );//订阅
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
