package com.example.lfy.basicframes.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.AdapActivityBinding;

//适配demo
public class AdaptationActivity extends AppCompatActivity {
    private AdapActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.adap_activity);

        binding.ivBack.setVisibility(View.VISIBLE);
        binding.tvText.setVisibility(View.VISIBLE);
        //设置监听
        binding.setClick(this);

    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_text:
                startActivity(new Intent(this,AddActivity.class));
                break;
            case R.id.iv_back:
                binding.ivBack.setVisibility(View.GONE);
                startActivity(new Intent(this,PlayerActivity.class));
                break;
            //跳转网页
            case R.id.tv_jump:
                Intent intent=new Intent(AdaptationActivity.this,WebviewActivity.class);
                intent.putExtra("url","https://www.baidu.com/");
                startActivity(intent);
                break;
        }
    }
}
