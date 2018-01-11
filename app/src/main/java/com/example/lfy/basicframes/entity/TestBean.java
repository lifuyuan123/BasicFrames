package com.example.lfy.basicframes.entity;

import android.databinding.BindingAdapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.utill.ToastUtils;


/**
 * author:ggband
 * data:2018/1/10 001014:24
 * email:ggband520@163.com
 * desc:
 */

public class TestBean {

    private String url;
    private String name;
    private int age;

    public TestBean(String url, String name, int age) {
        this.url = url;
        this.name = name;
        this.age = age;
    }

    public TestBean() {}

    //点击事件
    public void onClick(View view) {
        ToastUtils.showShort("被点击了");
    }

    //图片加载 "bind:userface"    去掉"bind:"编译就不会警告
    @BindingAdapter("userface")
    public static void getInternetImage(ImageView iv, String userface) {
        Glide.with(iv.getContext())
               .load(userface)
               .error(R.drawable.icon_error)
               .placeholder(R.mipmap.dayu)
               .into(iv);
     }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
