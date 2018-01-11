package com.example.lfy.basicframes.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityDataBinDingTestBinding;
import com.example.lfy.basicframes.entity.TestBean;
import com.example.lfy.basicframes.utill.ToastUtils;


public class DataBinDingTestActivity extends AppCompatActivity {
   private ActivityDataBinDingTestBinding bindingBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingBinding= DataBindingUtil.setContentView(this,R.layout.activity_data_bin_ding_test);
        bindingBinding.setTest(new TestBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515578578139&di=16d198923b7e770c6bfab3f6e9f8bd2f&imgtype=0&src=http%3A%2F%2Fimgs.ebrun.com%2Fresources%2F2016_12%2F2016_12_08%2F2016120864714811688057921.jpg","大佬",20));
        bindingBinding.tvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindingBinding.tvAge.setText("30");
            }
        });
    }



}
