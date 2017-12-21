package com.example.lfy.basicframes.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.entity.BannerModel;
import com.example.lfy.basicframes.entity.RestBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.http.apiServer;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.LogUtil;
import com.example.lfy.basicframes.utill.StatusBarUtil;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.utill.ToastUtils;
import com.example.lfy.basicframes.view.TitleBar;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BannerActivity extends BaseActivity implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {

    @BindView(R.id.banner_main_default)
    BGABanner mDefaultBanner;
    @BindView(R.id.banner_main_cube)
    BGABanner mCubeBanner;
    @BindView(R.id.banner_main_accordion)
    BGABanner mAccordionBanner;
    @BindView(R.id.banner_main_flip)
    BGABanner mFlipBanner;
    @BindView(R.id.banner_main_rotate)
    BGABanner mRotateBanner;
    @BindView(R.id.banner_main_alpha)
    BGABanner mAlphaBanner;
    @BindView(R.id.banner_main_zoomFade)
    BGABanner mZoomFadeBanner;
    @BindView(R.id.banner_main_fade)
    BGABanner mFadeBanner;
    @BindView(R.id.banner_main_zoomCenter)
    BGABanner mZoomCenterBanner;
    @BindView(R.id.banner_main_zoom)
    BGABanner mZoomBanner;
    @BindView(R.id.banner_main_stack)
    BGABanner mStackBanner;
    @BindView(R.id.banner_main_zoomStack)
    BGABanner mZoomStackBanner;
    @BindView(R.id.banner_main_depth)
    BGABanner mDepthBanner;
    @BindView(R.id.title_bar_banner)
    TitleBar titleBarBanner;
    private apiServer apiserver;//服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        //沉浸
        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.main));

        //返回
        titleBarBanner.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        apiserver= new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/")
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Observable  需要RxJava2CallAdapterFactory  retrofit默认是DefaultCallAdapterFactory  不加会创建失败
                .build().create(apiServer.class);

        setListener();
        loadData();
    }


    //订阅
    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void setListener() {
        mDefaultBanner.setDelegate(this);
        mCubeBanner.setDelegate(this);
    }

    private void loadData() {
        loadData(mDefaultBanner, 1);
        loadData(mCubeBanner, 2);
        loadData(mAccordionBanner, 3);
        loadData(mFlipBanner, 4);
        loadData(mRotateBanner, 5);
        loadData(mAlphaBanner, 6);
        loadData(mZoomFadeBanner, 3);
        loadData(mFadeBanner, 4);
        loadData(mZoomCenterBanner, 5);
        loadData(mZoomBanner, 6);
        loadData(mStackBanner, 3);
        loadData(mZoomStackBanner, 4);
        loadData(mDepthBanner, 5);
    }


    //加载数据  获取网络图片
    private void loadData(final BGABanner banner, final int count) {

        observe( apiserver.getBanner(count)).subscribe(new ApiCallBack<BannerModel>() {
            @Override
            public void OnSuccess(BannerModel bannerModel) {
                LogUtil.e(bannerModel.toString());
                /**
                * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                */
                banner.setAutoPlayAble(bannerModel.imgs.size() > 1);

                banner.setAdapter(BannerActivity.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void OnError(String msg) {
                ToastUtils.showShort("网络数据加载失败");
            }

            @Override
            public void OnFinish() {}
        });

    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
        ToastUtils.showShort("点击了第" + (position + 1) + "页");
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Picasso.with(itemView.getContext())
                .load(model)
                .placeholder(R.drawable.holder).error(R.drawable.holder)
                .into(itemView);

//        Glide.with(itemView.getContext())
//                .load(model)
//                .apply(new RequestOptions().placeholder(R.drawable.holder).error(R.drawable.holder).dontAnimate().centerCrop())
//                .into(itemView);
    }
}
