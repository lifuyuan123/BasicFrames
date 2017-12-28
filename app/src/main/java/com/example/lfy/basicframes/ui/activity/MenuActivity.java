package com.example.lfy.basicframes.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.CommonAdapter;
import com.example.lfy.basicframes.databinding.ActivityMenuBinding;
import com.example.lfy.basicframes.databinding.MenuItemBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.StatusBarUtil;
import com.example.lfy.basicframes.utill.StatusBarUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 目录activity
 */
public class MenuActivity extends BaseActivity {

    private List<String> list=new ArrayList<>();
    private List<Class> classList=new ArrayList<>();
    private LinearLayoutManager manager=new LinearLayoutManager(this);
    private CommonAdapter<String> adapter;
    private ActivityMenuBinding menuBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuBinding=DataBindingUtil.setContentView(this,R.layout.activity_menu);

        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.main));

        menuBinding.titleBarMenu.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter=new CommonAdapter<String>(list,R.layout.menu_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, final int position, String s) {
                MenuItemBinding itemBinding= (MenuItemBinding) binding;
                itemBinding.tvMenuItem.setText(s);

                itemBinding.tvMenuItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MenuActivity.this,classList.get(position)));
                    }
                });
            }
        };

        getData();

        menuBinding.rvMenu.setLayoutManager(manager);
        menuBinding.rvMenu.setAdapter(adapter);
    }

    private void getData() {
        list.add("banner图列表");
        classList.add(BannerActivity.class);
        list.add("侧滑删除与拖拽");
        classList.add(SwipeDeleteActivity.class);
        list.add("权限试例");
        classList.add(PermissionsActivity.class);
    }
}
