package com.example.lfy.basicframes.ui.activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.CommonAdapter;
import com.example.lfy.basicframes.databinding.MenuItemBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.StatusBarUtil;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 目录activity
 */
public class MenuActivity extends BaseActivity {

    @BindView(R.id.title_bar_menu)
    TitleBar titleBarMenu;
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    private List<String> list=new ArrayList<>();
    private List<Class> classList=new ArrayList<>();
    private LinearLayoutManager manager=new LinearLayoutManager(this);
    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.main));

        titleBarMenu.setLeftLayoutClickListener(new View.OnClickListener() {
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

        rvMenu.setLayoutManager(manager);
        rvMenu.setAdapter(adapter);
    }

    private void getData() {
        list.add("banner图列表");
        classList.add(BannerActivity.class);
        list.add("侧滑删除与拖拽");
        classList.add(SwipeDeleteActivity.class);
    }
}
