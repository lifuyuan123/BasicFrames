package com.example.lfy.basicframes.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.CommonAdapter;
import com.example.lfy.basicframes.databinding.AndroidItemBinding;
import com.example.lfy.basicframes.entity.AndroidBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.ui.activity.WEBActivity;
import com.example.lfy.basicframes.ui.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:ggband
 * data:2017/12/14 00149:26
 * email:ggband520@163.com
 * desc:
 */

public class AndroidFragment extends BaseFragment {

    @BindView(R.id.rv_android)
    RecyclerView rvAndroid;
    @BindView(R.id.fresh_android)
    SmartRefreshLayout freshAndroid;
    @BindView(R.id.foot_android)
    BallPulseFooter footAndroid;
    private LinearLayoutManager manager = new LinearLayoutManager(getContext());
    private List<AndroidBean.ResultsBean> list = new ArrayList<>();
    private CommonAdapter<AndroidBean.ResultsBean> adapter;
    private int page = 1;
    private HeaderAndFooterWrapper headerAndFooterWrapper;//头尾布局适配器 传入我们的adapter
    private View footView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvAndroid.setLayoutManager(manager);
        footAndroid.setAnimatingColor(Color.parseColor("#ffffff"));//设置footview颜色
        footAndroid.setBackgroundColor(Color.parseColor("#2196F3"));
        freshAndroid.setEnableAutoLoadmore(true);
        freshAndroid.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(page);
                refreshlayout.finishLoadmore();
                if (adapter.getItemCount() == 50) {
                    Toast.makeText(getContext(), "数据加载完毕", Toast.LENGTH_SHORT).show();
                    footView.setVisibility(View.VISIBLE);
                    refreshlayout.setLoadmoreFinished(true);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getData(page);
                footView.setVisibility(View.GONE);
                refreshlayout.setLoadmoreFinished(false);
                refreshlayout.finishRefresh();
            }
        });


        adapter = new CommonAdapter<AndroidBean.ResultsBean>(list, R.layout.android_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, final AndroidBean.ResultsBean androidBean) {
                AndroidItemBinding itemBinding = (AndroidItemBinding) binding;
                itemBinding.tvName.setText(androidBean.getDesc());
                itemBinding.tvUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), WEBActivity.class);
                        intent.putExtra("url", androidBean.getUrl());
                        startActivity(intent);
                    }
                });
            }
        };

        getData(page);

        footView= LayoutInflater.from(getContext()).inflate(R.layout.foot_view,null);
        footView.setVisibility(View.GONE);
        headerAndFooterWrapper =new HeaderAndFooterWrapper(adapter);//将适配器传入
        headerAndFooterWrapper.addFootView(footView);
        rvAndroid.setAdapter(headerAndFooterWrapper);
        freshAndroid.autoRefresh();
    }


    private void getData(final int page) {
        subscriber.getGankAndroid("10", page + "", "Android", new ApiCallBack<AndroidBean>() {
            @Override
            public void OnSuccess(AndroidBean value) {
                Log.e("android", value.toString());
                if (page == 1) {
                    list.clear();
                }
                list.addAll(value.getResults());
                headerAndFooterWrapper.notifyDataSetChanged();//注意这里的刷新的adapter也需要用headerAndFooterWrapper
            }

            @Override
            public void OnError(String msg) {
                if (msg != null)
                    Log.e("androidOnError", msg.toString());
            }

            @Override
            public void OnFinish() {
                Log.e("androidOnFinish", "OnFinish");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_android;
    }

}
