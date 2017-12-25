package com.example.lfy.basicframes.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityDataBindingBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.ToastUtils;

public class DataBindingActivity extends BaseActivity implements View.OnClickListener{

    @NonNull
    private ActivityDataBindingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);

        binding.tv1.setText("databinding  测试一");
        binding.lin1.setVisibility(View.GONE);
        binding.tv2.setOnClickListener(this);
        binding.lin2.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_2:
                binding.lin1.setVisibility(View.VISIBLE);
                break;
            case R.id.lin_1:
                break;
            case R.id.tv_2:
                ToastUtils.showShort("点击了tv2");
                break;
        }
    }
}
