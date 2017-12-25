package com.example.lfy.basicframes.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityGuideBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.LogUtil;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * banner导航
 */
public class GuideActivity extends BaseActivity {

    private ActivityGuideBinding guideBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guideBinding=DataBindingUtil.setContentView(this,R.layout.activity_guide);
        setListener();
        processLogic();
        LogUtil.e("guide");
    }


    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        guideBinding.bannerGuideForeground.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }
       // 设置数据源
    private void processLogic() {
        //背景
        guideBinding.bannerGuideBackground.setData(R.drawable.uoko_guide_background_1, R.drawable.uoko_guide_background_2, R.drawable.uoko_guide_background_3);
        //前景
        guideBinding.bannerGuideForeground.setData(R.drawable.uoko_guide_foreground_1, R.drawable.uoko_guide_foreground_2, R.drawable.uoko_guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        guideBinding.bannerGuideBackground.setBackgroundResource(android.R.color.white);
    }
}
