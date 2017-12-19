package com.example.lfy.basicframes.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lfy.basicframes.http.Subscriber;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:ggband
 * data:2017/12/14 00149:12
 * email:ggband520@163.com
 * desc:
 */

abstract public class BaseFragment extends Fragment {

    private Unbinder bind;
    protected Subscriber subscriber;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriber= Subscriber.getInstemce();
    }
    abstract protected int getLayoutId();


    //onDestroyView此方法将view置为空
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
