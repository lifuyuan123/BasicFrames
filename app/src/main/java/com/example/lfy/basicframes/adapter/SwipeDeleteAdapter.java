package com.example.lfy.basicframes.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.touchHelper.ItemTouchHelperAdapter;
import com.example.lfy.basicframes.entity.NormalModel;
import com.example.lfy.basicframes.view.SwipeBackActivity.BGASwipeItemLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;

/**
 * author:ggband
 * data:2017/12/21 002110:22
 * email:ggband520@163.com
 * desc:侧滑删除的layout
 */

public class SwipeDeleteAdapter extends BGARecyclerViewAdapter<NormalModel> implements ItemTouchHelperAdapter {
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

    public SwipeDeleteAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_bgaswipe);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
        swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_delete);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, NormalModel model) {
        //设置标题和内容
        viewHolderHelper.setText(R.id.tv_item_bgaswipe_title, model.mTitle)
                .setText(R.id.tv_item_bgaswipe_detail, model.mDetail);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }


    //拖拽监听-----------------------------------------------------------------
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        /**
         * 拖拽后，切换位置，数据排序
         */
        Collections.swap(getData(),fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);

        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        /**
         * 移除之前的数据
         */
//        getData().remove(position);
//        notifyItemRemoved(position);
    }
}
