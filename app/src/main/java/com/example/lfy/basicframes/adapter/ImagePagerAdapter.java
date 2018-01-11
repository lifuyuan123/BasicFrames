package com.example.lfy.basicframes.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.entity.GankBean;
import com.github.chrisbanes.photoview.PhotoView;
import java.util.ArrayList;
import java.util.List;

/**
 * author:ggband
 * data:2017/12/15 001517:09
 * email:ggband520@163.com
 * desc:
 */

public class ImagePagerAdapter extends PagerAdapter {

    private List<View> views=new ArrayList<>();
    private List<GankBean.ResultsBean> beanList=new ArrayList<>();
    private Context context;

    public ImagePagerAdapter(List<GankBean.ResultsBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
        init(beanList);
    }

    private void init(List<GankBean.ResultsBean> beanList) {
        for (GankBean.ResultsBean s : beanList) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_guide_image, null);
            PhotoView imageView=linearLayout.findViewById(R.id.image);
            Glide.with(context)
                    .load(s.getUrl())
                    .error(R.drawable.icon_error)
                    .placeholder(R.mipmap.dayu)
                    .into(imageView);

            views.add(linearLayout);
        }
    }

    @Override
    public int getCount() {
        return beanList.size();
    }
    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(views.get(arg1), 0);
        return views.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
