package com.example.lfy.basicframes.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.CommonAdapter;
import com.example.lfy.basicframes.databinding.FragmentWelfareBinding;
import com.example.lfy.basicframes.databinding.GankItemBinding;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.http.Subscriber;
import com.example.lfy.basicframes.ui.activity.ImagePagerActivity;
import com.example.lfy.basicframes.utill.LogUtil;
import com.example.lfy.basicframes.utill.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author:ggband
 * data:2017/12/14 00149:23
 * email:ggband520@163.com
 * desc:福利
 */

public class WelfareFragment extends Fragment {
    protected Subscriber subscriber;
    //瀑布流
    private StaggeredGridLayoutManager manager;
    private CommonAdapter<GankBean.ResultsBean> adapter;
    private List<GankBean.ResultsBean> list = new ArrayList<>();
    private int page = 1;
    private FragmentWelfareBinding inflate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = DataBindingUtil.inflate(inflater, R.layout.fragment_welfare, container, false);
        return inflate.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriber= Subscriber.getInstemce();
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        inflate.rvWelfare.setLayoutManager(manager);
        //获取数据
        getData(page);
        adapter = new CommonAdapter<GankBean.ResultsBean>(list, R.layout.gank_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, final int position, GankBean.ResultsBean gankBean) {
                GankItemBinding itemBinding = (GankItemBinding) binding;
                int width = ((Activity) itemBinding.cardView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
                int height = ((Activity) itemBinding.cardView.getContext()).getWindowManager().getDefaultDisplay().getHeight();
                ViewGroup.LayoutParams params = itemBinding.cardView.getLayoutParams();
                //设置图片的相对于屏幕的宽高比
                params.width = width / 2;

                if (position == 0) {
                    params.height = (int) (width / 2* 0.75);
                }else {
                    params.height = (int) (width / 2*1.5);
                }
                itemBinding.cardView.setLayoutParams(params);

//                Picasso.with(getContext()).load(gankBean.getUrl()).into(itemBinding.ivCard);
//                itemBinding.tvTitle.setText(gankBean.getType());
                //使用databinding加载  只需要将实体类传进去
                itemBinding.setGank(gankBean);


                itemBinding.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
//                        intent.putExtra("position",position+1+"") ;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("key", (Serializable) list);
                        bundle.putInt("position", position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        };

        inflate.rvWelfare.setAdapter(adapter);
        inflate.welfareHeard.setSpinnerStyle(SpinnerStyle.Scale);//设置下拉动画类型
//        freshWelfare.setPrimaryColors(0xff444444, 0xffffffff);//设置头尾布局不经颜色
        inflate.freshWelfare.setEnableAutoLoadmore(true);//可加载更多
        inflate.freshWelfare.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(page);
                refreshlayout.finishLoadmore();
                if (adapter.getItemCount() > 500) {
                    ToastUtils.showShort("数据加载完毕");
                    refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                //显示加载中
                inflate.emptyLayout.showLoading();
                getData(page);
                refreshlayout.setLoadmoreFinished(false);//再次触发加载更多事件
                refreshlayout.finishRefresh();
            }
        });

        //绑定rv,内部后续隐藏rv操作
        inflate.emptyLayout.bindView(inflate.rvWelfare);
        //重新加载
        inflate.emptyLayout.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("重新加载");
                //触发自动刷新
                inflate.freshWelfare.autoRefresh();
                page=1;
                getData(page);
            }
        });

        //触发自动刷新
        inflate.freshWelfare.autoRefresh();
    }



    private void getData(final int page) {

        subscriber.getGank(10 + "", page + "", "福利", new ApiCallBack<GankBean>() {
            @Override
            public void OnSuccess(GankBean value) {
                Log.e("getDataOnSuccess", value.toString());
                if (page == 1) {
                    list.clear();
                }
                list.addAll(value.getResults());
                adapter.notifyDataSetChanged();
                //关闭所有
                inflate.emptyLayout.showSuccess();

            }

            @Override
            public void OnError(String msg) {
                //加载错误
                inflate.emptyLayout.showError();
                if (msg!=null)
                Log.e("getDataOnError", msg.toString());
            }

            @Override
            public void OnFinish() {
                //关闭所有
//                emptyLayout.showSuccess();
                Log.e("getDataOnFinish", "OnFinish");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.e("welfare  销毁");
    }


}
