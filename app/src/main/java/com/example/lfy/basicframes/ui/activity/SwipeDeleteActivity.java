package com.example.lfy.basicframes.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.SwipeDeleteAdapter;
import com.example.lfy.basicframes.adapter.touchHelper.ItemTouchHelperCallback;
import com.example.lfy.basicframes.entity.NormalModel;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.utill.ToastUtils;
import com.example.lfy.basicframes.view.EmptyLayout;
import com.example.lfy.basicframes.view.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.baseadapter.BGADivider;
import cn.bingoogolapple.baseadapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.baseadapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAOnRVItemLongClickListener;

public class SwipeDeleteActivity extends BaseActivity implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, BGAOnItemChildClickListener, BGAOnItemChildLongClickListener {

    @BindView(R.id.title_bar_delte)
    TitleBar titleBarDelte;
    @BindView(R.id.rv_delete)
    RecyclerView rvDelete;
    @BindView(R.id.empty_layout_delete)
    EmptyLayout emptyLayoutDelete;
    @BindView(R.id.fresh_delete)
    SmartRefreshLayout freshDelete;
    private SwipeDeleteAdapter mAdapter;
    private List<NormalModel> list=new ArrayList<>();
    private LinearLayoutManager manager=new LinearLayoutManager(this);
    private int page=1;
    //拖拽排序助手
     private ItemTouchHelperCallback mItemTouchHelper;
     private ItemTouchHelper helper;

//    「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete);
        ButterKnife.bind(this);
        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.main));

        titleBarDelte.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //初始化适配器和监听器
        mAdapter = new SwipeDeleteAdapter(rvDelete);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemChildLongClickListener(this);

        getData();

        mAdapter.setData(list);
        rvDelete.setLayoutManager(manager);
        rvDelete.addItemDecoration(BGADivider.newBitmapDivider());//自定义分割线
        rvDelete.setAdapter(mAdapter);

        // 初始化拖拽排序和滑动删除
        mItemTouchHelper = new ItemTouchHelperCallback(mAdapter);
        helper = new ItemTouchHelper(mItemTouchHelper);
        //拖拽绑定rv
        helper.attachToRecyclerView(rvDelete);

        //rv设置滑动监听
        rvDelete.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               //正在滚动  SCROLL_STATE_IDLE = 0;
               //正在被外部拖拽,一般为用户正在用手指滚动  SCROLL_STATE_DRAGGING = 1;
               //自动滚动开始  SCROLL_STATE_SETTLING = 2;

                //正在滑动
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }
        });

        freshDelete.setEnableAutoLoadmore(true);//可加载更多
        freshDelete.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getData();
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                getData();
                refreshlayout.finishRefresh();
            }
        });
        freshDelete.autoRefresh();//刷新

    }

    private void getData() {
        if (page==1){
            list.clear();
            for (int i = 0; i < 10; i++) {
                if (i % 4 == 0) {
                    list.add(new NormalModel("标题" + i, "我是短的描述" + i));
                } else {
                    list.add(new NormalModel("标题" + i, "我是很长很长长很长的描述" + i));
                }
            }
        }else {
            for (int i = 0; i < 10; i++) {
                if (i % 4 == 0) {
                    list.add(new NormalModel("标题" + (i+(page-1)*10), "我是短的描述" + i));
                } else {
                    list.add(new NormalModel("标题" + (i+(page-1)*10), "我是很长很长长很长的描述" + i));
                }
            }
        }
        mAdapter.notifyDataSetChanged();//刷新
    }



    //侧滑监听----------------------------------------------------------
    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
            mAdapter.closeOpenedSwipeItemLayoutWithAnim();
            mAdapter.removeItem(position);
        }
    }

    @Override
    public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
            ToastUtils.showShort("长按了删除 " + mAdapter.getItem(position).mTitle);
            return true;
        }
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        ToastUtils.showShort("点击了条目 " + mAdapter.getItem(position).mTitle);
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        ToastUtils.showShort("长按了条目 " + mAdapter.getItem(position).mTitle);
        return true;
    }

}
