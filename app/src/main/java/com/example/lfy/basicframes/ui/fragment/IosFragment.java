package com.example.lfy.basicframes.ui.fragment;

import android.content.Intent;
import android.databinding.ViewDataBinding;
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
import com.example.lfy.basicframes.databinding.IosItemBinding;
import com.example.lfy.basicframes.entity.IosBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.ui.activity.WEBActivity;
import com.example.lfy.basicframes.ui.base.BaseFragment;
import com.example.lfy.basicframes.view.EmptyLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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

public class IosFragment extends BaseFragment {


    @BindView(R.id.rv_ios)
    RecyclerView rvIos;
    @BindView(R.id.fresh_ios)
    SmartRefreshLayout freshIos;
    @BindView(R.id.empty_layout_iod)
    EmptyLayout emptyLayoutIod;

    private LinearLayoutManager manager = new LinearLayoutManager(getContext());
    private CommonAdapter<IosBean.ResultsBean> adapter;
    private List<IosBean.ResultsBean> list = new ArrayList<>();
    private HeaderAndFooterWrapper headerAndFooterWrapper;//头尾布局适配器 传入我们的adapter
    private View footView;
    int page = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvIos.setLayoutManager(manager);


        emptyLayoutIod.bindView(rvIos);
        emptyLayoutIod.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freshIos.autoRefresh();
                page=1;
                getData(page);
            }
        });

        freshIos.setEnableAutoLoadmore(true);
        freshIos.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(page);
                refreshlayout.finishLoadmore();
                if (adapter.getItemCount() > 50) {
                    Toast.makeText(getContext(), "全部加载完成", Toast.LENGTH_SHORT).show();
                    footView.setVisibility(View.VISIBLE);
                    refreshlayout.setLoadmoreFinished(true);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getData(page);
                refreshlayout.finishRefresh();
                footView.setVisibility(View.GONE);
                refreshlayout.setLoadmoreFinished(false);
            }
        });

        adapter = new CommonAdapter<IosBean.ResultsBean>(list, R.layout.ios_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, final IosBean.ResultsBean iosBean) {
                IosItemBinding itemBinding = (IosItemBinding) binding;
                itemBinding.tvName.setText(iosBean.getDesc());
                itemBinding.tvUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), WEBActivity.class);
                        intent.putExtra("url", iosBean.getUrl());
                        startActivity(intent);
                    }
                });
            }
        };
        getData(page);
        footView = LayoutInflater.from(getContext()).inflate(R.layout.foot_view, null);
        footView.setVisibility(View.GONE);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);//将适配器传入
        headerAndFooterWrapper.addFootView(footView);
        rvIos.setAdapter(headerAndFooterWrapper);
        freshIos.autoRefresh();
    }

    private void getData(final int page) {
        emptyLayoutIod.showLoading();
        subscriber.getGankiOS("10", page + "", "iOS", new ApiCallBack<IosBean>() {
            @Override
            public void OnSuccess(IosBean value) {
                Log.e("ios", value.toString());
                if (page == 1) {
                    list.clear();
                }
                list.addAll(value.getResults());
                headerAndFooterWrapper.notifyDataSetChanged();
                emptyLayoutIod.showSuccess();
            }

            @Override
            public void OnError(String msg) {
                if (msg != null)
                    Log.e("iosOnError", msg.toString());
                emptyLayoutIod.showError();
            }

            @Override
            public void OnFinish() {
                Log.e("iosOnFinish", "OnFinish");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ios;
    }



}
