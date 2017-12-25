package com.example.lfy.basicframes.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityPermissionsBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.EasyPermissionsEx;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.utill.ToastUtils;


/**
 * 权限测试   需要实现EasyPermissionsEx.PermissionCallbacks接口
 */
public class PermissionsActivity extends BaseActivity implements View.OnClickListener {

    private ActivityPermissionsBinding permissionsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionsBinding=DataBindingUtil.setContentView(this,R.layout.activity_permissions);
        StatusBarUtils.setColorNoTranslucent(this, getResources().getColor(R.color.main));
        permissionsBinding.titlePermissions.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        permissionsBinding.tvCallPhone.setOnClickListener(this);
        permissionsBinding.tvLocation.setOnClickListener(this);


    }


    //申请权限回掉
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 100:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //已经授权
                    //拨打电话
                    Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + 1008611));
                    startActivity(intent);
                }else {
                 ToastUtils.showShort("拒绝电话权限");
                }
                break;
            case 101:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                  ToastUtils.showShort("回掉获取定位权限成功");
                }else {
                    ToastUtils.showShort("拒绝定位权限");
                }
                break;
        }


    }


    @Override
    public void onClick(View view) {
            switch (view.getId()) {
                //打电话
                case R.id.tv_call_phone:
                    if (EasyPermissionsEx.hasPermissions(PermissionsActivity.this, Manifest.permission.CALL_PHONE)) {
                        //已经授权
                        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + 1008611));
                        startActivity(intent);
                    } else {
                        EasyPermissionsEx.requestPermissions(PermissionsActivity.this, "拨打电话需要授予拨号权限", 100, Manifest.permission.CALL_PHONE);
                    }
                    break;
                //定位
                case R.id.tv_location:

                    if (EasyPermissionsEx.hasPermissions(PermissionsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ToastUtils.showShort("授权成功");
                    } else {
                        EasyPermissionsEx.requestPermissions(PermissionsActivity.this, "获取位置信息需要定位权限", 101, Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                    break;
            }
    }
}
