package com.example.lfy.basicframes.application;

import android.app.Application;
import android.util.Log;

import com.example.lfy.basicframes.utill.LogUtil;
import com.example.lfy.basicframes.utill.Utils;
import com.tencent.smtt.sdk.QbSdk;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 * author:ggband
 * data:2017/12/13 001310:02
 * email:ggband520@163.com
 * desc:
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。
            }
            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@","加载内核是否成功:"+b);
            }
        });

        //初始化
        Utils.init(this,getApplicationContext());

        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);

        LogUtil.closeOrOpenAll(true);//打开日志

    }
}
