package com.example.lfy.basicframes.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lfy.basicframes.http.Subscriber;
import com.example.lfy.basicframes.utill.StatusBarUtil;


/**
 * author:ggband
 * data:2017/12/13 001310:56
 * email:ggband520@163.com
 * desc:基类
 */

public class BaseActivity extends AppCompatActivity {

   protected Subscriber subscriber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriber=Subscriber.getInstemce();



    }
}
