package com.example.lfy.basicframes.ui.activity;

import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.adapter.ImagePagerAdapter;
import com.example.lfy.basicframes.entity.GankBean;
import com.example.lfy.basicframes.utill.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePagerActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_page)
    TextView tvPage;
    private ImagePagerAdapter adapter;
    private List<GankBean.ResultsBean> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pager);
        ButterKnife.bind(this);
        //状态栏相关
        StatusBarUtil.darkMode(this);

        Bundle bundle=getIntent().getExtras();
        int position = bundle.getInt("position");
        list = (List<GankBean.ResultsBean>)bundle.getSerializable("key");
        tvPage.setText(position+1+"/"+list.size());

        adapter=new ImagePagerAdapter(list,this);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(position);//选择当前页
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                tvPage.setText(position+1+"/"+list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }
}
