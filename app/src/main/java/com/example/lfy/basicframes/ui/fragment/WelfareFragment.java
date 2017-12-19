package com.example.lfy.basicframes.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.CommonAdapter;
import com.example.lfy.basicframes.databinding.GankItemBinding;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.http.ApiCallBack;
import com.example.lfy.basicframes.ui.activity.ImagePagerActivity;
import com.example.lfy.basicframes.ui.base.BaseFragment;
import com.example.lfy.basicframes.utill.ToastUtils;
import com.example.lfy.basicframes.view.EmptyLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:ggband
 * data:2017/12/14 00149:23
 * email:ggband520@163.com
 * desc:福利
 */

public class WelfareFragment extends BaseFragment {

    @BindView(R.id.rv_welfare)
    RecyclerView rv;
    @BindView(R.id.fresh_welfare)
    SmartRefreshLayout freshWelfare;
    @BindView(R.id.welfare_heard)
    ClassicsHeader welfareHeard;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    //瀑布流
    private StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    private CommonAdapter<GankBean.ResultsBean> adapter;
    private List<GankBean.ResultsBean> list = new ArrayList<>();
    private int page = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setLayoutManager(manager);
        //获取数据
        getData(page);
        adapter = new CommonAdapter<GankBean.ResultsBean>(list, R.layout.gank_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, final int position, GankBean.ResultsBean gankBean) {
                GankItemBinding itemBinding = (GankItemBinding) binding;

                if (position == 0) {
                    int width = ((Activity) itemBinding.cardView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
                    int height = ((Activity) itemBinding.cardView.getContext()).getWindowManager().getDefaultDisplay().getHeight();
                    ViewGroup.LayoutParams params = itemBinding.cardView.getLayoutParams();
                    //设置图片的相对于屏幕的宽高比
                    params.width = width / 2;
                    params.height = (int) (height * 0.5);
                    itemBinding.cardView.setLayoutParams(params);
                }

                Picasso.with(getContext()).load(gankBean.getUrl()).into(itemBinding.ivCard);
                itemBinding.tvTitle.setText(gankBean.getType());

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

        rv.setAdapter(adapter);
        welfareHeard.setSpinnerStyle(SpinnerStyle.Scale);//设置下拉动画类型
//        freshWelfare.setPrimaryColors(0xff444444, 0xffffffff);//设置头尾布局不经颜色
        freshWelfare.setEnableAutoLoadmore(true);//可加载更多
        freshWelfare.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData(page);
                refreshlayout.finishLoadmore();
                if (adapter.getItemCount() > 100) {
                    Toast.makeText(getContext(), "数据加载完毕", Toast.LENGTH_SHORT).show();
                    refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                }
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                getData(page);
                refreshlayout.setLoadmoreFinished(false);//再次触发加载更多事件
                refreshlayout.finishRefresh();
            }
        });

        //绑定rv,内部后续隐藏rv操作
        emptyLayout.bindView(rv);
        //重新加载
        emptyLayout.setOnButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("重新加载");
                //触发自动刷新
                freshWelfare.autoRefresh();
                page=1;
                getData(page);
            }
        });

        //触发自动刷新
        freshWelfare.autoRefresh();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_welfare;
    }

    private void getData(final int page) {
        //显示加载中
        emptyLayout.showLoading();

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
                emptyLayout.showSuccess();

            }

            @Override
            public void OnError(String msg) {
                //加载错误
                emptyLayout.showError();
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


}
