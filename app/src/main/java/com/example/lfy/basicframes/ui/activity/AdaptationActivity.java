package com.example.lfy.basicframes.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityAdaptationBinding;

//适配demo
public class AdaptationActivity extends AppCompatActivity {
    private ActivityAdaptationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_adaptation);

        binding.ivBack.setVisibility(View.VISIBLE);
        binding.tvText.setVisibility(View.VISIBLE);

    }
}
