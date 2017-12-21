package com.example.lfy.basicframes.adapter.touchHelper;


//拖拽帮助接口
public interface ItemTouchHelperAdapter {
    //是否移动
    boolean onItemMove(int fromPosition, int toPosition);
    //删除
    void onItemDismiss(int position);
}