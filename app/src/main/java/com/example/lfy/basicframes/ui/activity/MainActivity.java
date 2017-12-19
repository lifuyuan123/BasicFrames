package com.example.lfy.basicframes.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
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
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.ui.fragment.AndroidFragment;
import com.example.lfy.basicframes.ui.fragment.IosFragment;
import com.example.lfy.basicframes.ui.fragment.VideoFragment;
import com.example.lfy.basicframes.ui.fragment.WelfareFragment;
import com.example.lfy.basicframes.utill.StatusBarUtil;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_main)
    Toolbar toolbarMain;
    @BindView(R.id.frame_main)
    FrameLayout frameMain;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;


    private FragmentTransaction transaction;
    private Fragment fragment;
    private String[] array;
    private int lastId;
    private Fragment[] fragments = new Fragment[4];
    //radionButton的图片   需要多个分辨率的图片才可以适配
    private int[] radionback = {R.drawable.main_buttom_one, R.drawable.main_button_two, R.drawable.main_button_three, R.drawable.main_button_fore};

    private long firstTime;//第一次点击
    private long secondTime;//第二次点击
    private long spaceTime;//两次时间差

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("main");

        toolbarMain.setNavigationIcon(R.drawable.menu);
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //状态栏相关
        StatusBarUtil.darkMode(this);
        StatusBarUtil.setPaddingSmart(this, toolbarMain);

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                transaction = getSupportFragmentManager().beginTransaction();
                fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(id));
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
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            button.setGravity(Gravity.CENTER);
            button.setLayoutParams(params);
            button.setTextColor(getResources().getColorStateList(R.color.buttom_color_selector));
            rgMain.addView(button);
        }
        //设置为选中《福利》
        rgMain.getChildAt(0).performClick();

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



}
