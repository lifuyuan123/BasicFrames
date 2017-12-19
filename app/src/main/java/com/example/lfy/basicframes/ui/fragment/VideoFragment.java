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
import com.example.lfy.basicframes.databinding.RestItemBinding;
import com.example.lfy.basicframes.entity.RestBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.ui.activity.WEBActivity;
import com.example.lfy.basicframes.ui.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.tencent.smtt.sdk.TbsVideo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:ggband
 * data:2017/12/14 00149:27
 * email:ggband520@163.com
 * desc:
 */

public class VideoFragment extends BaseFragment {


    @BindView(R.id.rv_vedio)
    RecyclerView rvVedio;
    @BindView(R.id.fresh_vedio)
    SmartRefreshLayout freshVedio;
    @BindView(R.id.foot_vedio)
    BallPulseFooter footVedio;

    private LinearLayoutManager manager = new LinearLayoutManager(getContext());
    private List<RestBean.ResultsBean> list = new ArrayList<>();
    private CommonAdapter<RestBean.ResultsBean> adapter;
    private int page = 1;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        footVedio.setAnimatingColor(Color.parseColor("#ffffff"));//设置footview颜色
        footVedio.setBackgroundColor(Color.parseColor("#11BBFF"));//设置背景颜色
        freshVedio.setEnableAutoLoadmore(true);
        freshVedio.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(page);
                refreshlayout.finishLoadmore();
                if (adapter.getItemCount() > 50) {
                    Toast.makeText(getContext(), "全部加载完成", Toast.LENGTH_SHORT).show();
                    refreshlayout.setLoadmoreFinished(true);
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getData(page);
                refreshlayout.finishRefresh();
            }
        });

        rvVedio.setLayoutManager(manager);
        adapter = new CommonAdapter<RestBean.ResultsBean>(list, R.layout.rest_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, final RestBean.ResultsBean resultsBean) {
                RestItemBinding itemBinding = (RestItemBinding) binding;
                itemBinding.tvName.setText(resultsBean.getDesc());
                itemBinding.tvUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), WEBActivity.class);
                        intent.putExtra("url", resultsBean.getUrl());
                        startActivity(intent);
                    }
                });
                itemBinding.tvAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //判断当前Tbs播放器是否已经可以使用。
                        if (TbsVideo.canUseTbsPlayer(getContext())) {
                            //直接调用播放接口，传入视频流的url
                            TbsVideo.openVideo(getContext(), resultsBean.getUrl());
                        }
                    }
                });
            }
        };

        getData(page);
        rvVedio.setAdapter(adapter);
        freshVedio.autoRefresh();
    }

    private void getData(final int page) {
        subscriber.getGankRest("10", page + "", "休息视频", new ApiCallBack<RestBean>() {
            @Override
            public void OnSuccess(RestBean value) {
                Log.e("rest", value.toString());
                if (page == 1) {
                    list.clear();
                }
                list.addAll(value.getResults());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void OnError(String msg) {
                Log.e("restOnError", msg.toString());
            }

            @Override
            public void OnFinish() {
                Log.e("restOnFinish", "OnFinish");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_vedio;
    }



}
