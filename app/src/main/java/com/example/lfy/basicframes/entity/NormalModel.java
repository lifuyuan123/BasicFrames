package com.example.lfy.basicframes.entity;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:17/1/11 上午2:49
 * 描述:
 */
public class NormalModel {
    public String mTitle;
    public String mDetail;
    public boolean selected;

    public NormalModel(String title, String detail) {
        mTitle = title;
        mDetail = detail;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}