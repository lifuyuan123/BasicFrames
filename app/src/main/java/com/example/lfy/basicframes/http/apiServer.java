package com.example.lfy.basicframes.http;

import com.example.lfy.basicframes.entity.AndroidBean;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.entity.IosBean;
import com.example.lfy.basicframes.entity.RestBean;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * author:ggband
 * data:2017/12/13 001310:27
 * email:ggband520@163.com
 * desc:请求接口
 */

public interface apiServer {

    //获取干货图片
    @GET("/api/data/{fuli}/{count}/{page}")
    Observable<GankBean> getGank(@Path("count") String count, @Path("page") String page,@Path(value="fuli")String type);

    //获取干货android
    @GET("/api/data/{fuli}/{count}/{page}")
    Observable<AndroidBean> getGankAndroid(@Path("count") String count, @Path("page") String page, @Path(value="fuli")String type);

    //获取干货ios
    @GET("/api/data/{fuli}/{count}/{page}")
    Observable<IosBean> getGankOIS(@Path("count") String count, @Path("page") String page, @Path(value="fuli")String type);

    //获取干货休息视频
    @GET("/api/data/{fuli}/{count}/{page}")
    Observable<RestBean> getGankRest(@Path("count") String count, @Path("page") String page, @Path(value="fuli")String type);


}
