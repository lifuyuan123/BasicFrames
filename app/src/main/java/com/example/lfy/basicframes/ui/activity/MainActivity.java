package com.example.lfy.basicframes.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityMainBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.ui.fragment.AndroidFragment;
import com.example.lfy.basicframes.ui.fragment.IosFragment;
import com.example.lfy.basicframes.ui.fragment.VideoFragment;
import com.example.lfy.basicframes.ui.fragment.WelfareFragment;
import com.example.lfy.basicframes.utill.EasyPermissionsEx;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.utill.ToastUtils;

public class MainActivity extends BaseActivity {

//    @BindView(R.id.viewpager)
//    ViewPager viewpager;
//    private MyPagerAdapter adapter;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String[] array;
    private int lastId;
    private Fragment[] fragments = new Fragment[4];
    //radionButton的图片   需要多个分辨率的图片才可以适配
    private int[] radionback = {R.drawable.main_buttom_one, R.drawable.main_button_two, R.drawable.main_button_three, R.drawable.main_button_fore};

    private long firstTime;//第一次点击
    private long secondTime;//第二次点击
    private long spaceTime;//两次时间差
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main);
        //获取fragment管理类
        fragmentManager=getSupportFragmentManager();
        mainBinding.titleBarMain.setLeftImageResource(R.drawable.menu);
        mainBinding.titleBarMain.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        mainBinding.titleBarMain.setRightImageResource(R.drawable.menu);
        mainBinding.titleBarMain.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ToastUtils.showShort("点击了右边");
            }
        });

        //状态栏相关
        StatusBarUtils.setColorNoTranslucent(this, Color.parseColor("#aa3e6456"));

        mainBinding.rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                transaction = fragmentManager.beginTransaction();
                fragment = fragmentManager.findFragmentByTag(String.valueOf(id));
//                viewpager.setCurrentItem(id);
                //标记最后一个点击的fragment
                Fragment lastFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(lastId));
                //不为空才能隐藏  隐藏上一次的fragment
                if (lastFragment != null) {
                    transaction.hide(lastFragment);
                }
                //点击radiobutton显示与隐藏fragment  为空的必须实例化
                if (fragment == null) {
                    fragment = fragments[id];
                }
                //没有被添加到activity中则添加
                if (!fragment.isAdded()) {
                    transaction.add(R.id.frame_main, fragment, String.valueOf(id));//设置一个string类型的tag
                }
                //提交显示fragment
                transaction.show(fragment).commitAllowingStateLoss();
                lastId = id;
            }
        });

        //添加fragment和radiobutton
        initView();

//        adapter = new MyPagerAdapter(getSupportFragmentManager());
//        viewpager.setAdapter(adapter);
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            public void onPageSelected(int position) {
//                rgMain.getChildAt(position).performClick();
//            }
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        });

    }

    //初始化radioButton
    private void initView() {
        fragments[0] = new WelfareFragment();
        fragments[1] = new AndroidFragment();
        fragments[2] = new IosFragment();
        fragments[3] = new VideoFragment();

        array = getResources().getStringArray(R.array.buttom_array);//radiobutton文字
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        for (int i = 0; i < array.length; i++) {
            RadioButton button = new RadioButton(this);
            button.setId(i);
            button.setCompoundDrawablesWithIntrinsicBounds(0, radionback[i], 0, 0);
            button.setButtonDrawable(new BitmapDrawable());
            button.setBackgroundDrawable(null);
            button.setText(array[i]);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            button.setTextColor(getResources().getColorStateList(R.color.buttom_color_selector));
            mainBinding.rgMain.addView(button);
        }
        //设置为选中《福利》
        mainBinding.rgMain.getChildAt(0).performClick();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            firstTime = System.currentTimeMillis();
            spaceTime = firstTime - secondTime;
            secondTime = firstTime;
            if (spaceTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else
                MainActivity.this.finish();
        }
        return true;
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，
     * 如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


//    class MyPagerAdapter extends FragmentPagerAdapter {
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        public Fragment getItem(int arg0) {
//            return fragments[arg0];
//        }
//
//        public int getCount() {
//            return fragments.length;
//        }
//    }



}
