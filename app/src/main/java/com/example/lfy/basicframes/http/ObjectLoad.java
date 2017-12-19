package com.example.lfy.basicframes.http;



import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author:ggband
 * data:2017/12/13 001311:28
 * email:ggband520@163.com
 * desc:
 */

public class ObjectLoad {

    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
