package com.example.lfy.basicframes.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {
    private ActivityAddBinding addBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBinding=DataBindingUtil.setContentView(this,R.layout.activity_add);
        addBinding.setOnclick(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_add:
                addBinding.tvAdd.setText("yes");
                break;
        }
    }
}
