package com.example.lfy.basicframes.utill.status;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * android 4.4以上沉浸式以及bar的管理
 *
 * @author LiaoDuanHong
 * @date 2017/7/10
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class ImmersionBar {

    private static Map<String, BarParams> mMap = new HashMap<>();
    private Activity mActivity;
    private Window mWindow;
    private ViewGroup mViewGroup;
    private BarParams mBarParams;
    private ViewGroup mContentView;

    private BarConfig mConfig;


    private ImmersionBar() {
    }

    private ImmersionBar(Activity activity) {

        mActivity = activity;
        mWindow = activity.getWindow();
        mViewGroup = (ViewGroup) mWindow.getDecorView();
        mContentView = mActivity.findViewById(android.R.id.content);
        mConfig = new BarConfig(activity);

        if (!mMap.isEmpty()) {
            if (mMap.get(mActivity.getClass().getName()) == null) {
                mBarParams = new BarParams();
                mMap.put(mActivity.getClass().getName(), mBarParams);
            } else {
                mBarParams = mMap.get(mActivity.getClass().getName());
            }
        } else {
            mBarParams = new BarParams();
            mMap.put(mActivity.getClass().getName(), mBarParams);
        }
    }

    /**
     * With immersion bar.
     *
     * @param activity the activity
     * @return the immersion bar
     */
    public static ImmersionBar with(Activity activity) {
        return new ImmersionBar(activity);
    }

    /**
     * 透明状态栏，默认透明
     *
     * @return the immersion bar
     */
    public ImmersionBar transparentStatusBar() {
        mBarParams.statusBarColor = Color.TRANSPARENT;
        return this;
    }

    /**
     * 透明导航栏，默认黑色
     *
     * @return the immersion bar
     */
    public ImmersionBar transparentNavigationBar() {
        mBarParams.navigationBarColor = Color.TRANSPARENT;
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        mBarParams.fullScreen = true;
        return this;
    }

    /**
     * 透明状态栏和导航栏
     *
     * @return the immersion bar
     */
    public ImmersionBar transparentBar() {
        mBarParams.statusBarColor = Color.TRANSPARENT;
        mBarParams.navigationBarColor = Color.TRANSPARENT;
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        mBarParams.fullScreen = true;
        return this;
    }

    /**
     * 状态栏颜色
     *
     * @param statusBarColor 状态栏颜色，资源文件（R.color.xxx）
     * @return the immersion bar
     */
    public ImmersionBar statusBarColor(@ColorRes int statusBarColor) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, statusBarColor);
        return this;
    }

    /**
     * 状态栏颜色
     *
     * @param statusBarColor 状态栏颜色，资源文件（R.color.xxx）
     * @param alpha          the alpha  透明度
     * @return the immersion bar
     */
    public ImmersionBar statusBarColor(@ColorRes int statusBarColor,
                                       @FloatRange(from = 0f, to = 1f) float alpha) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, statusBarColor);
        mBarParams.statusBarAlpha = alpha;
        return this;
    }

    /**
     * 状态栏颜色
     *
     * @param statusBarColor          状态栏颜色，资源文件（R.color.xxx）
     * @param statusBarColorTransform the status bar color transform 状态栏变换后的颜色
     * @param alpha                   the alpha  透明度
     * @return the immersion bar
     */
    public ImmersionBar statusBarColor(@ColorRes int statusBarColor,
                                       @ColorRes int statusBarColorTransform,
                                       @FloatRange(from = 0f, to = 1f) float alpha) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, statusBarColor);
        mBarParams.statusBarColorTransform = ContextCompat.getColor(mActivity, statusBarColorTransform);
        mBarParams.statusBarAlpha = alpha;
        return this;
    }

    /**
     * 导航栏颜色
     *
     * @param navigationBarColor the navigation bar color 导航栏颜色
     * @return the immersion bar
     */
    public ImmersionBar navigationBarColor(@ColorRes int navigationBarColor) {
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, navigationBarColor);
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        return this;
    }

    /**
     * 导航栏颜色
     *
     * @param navigationBarColor the navigation bar color 导航栏颜色
     * @param navigationAlpha    the navigation alpha 透明度
     * @return the immersion bar
     */
    public ImmersionBar navigationBarColor(@ColorRes int navigationBarColor,
                                           @FloatRange(from = 0f, to = 1f) float navigationAlpha) {
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, navigationBarColor);
        mBarParams.navigationBarAlpha = navigationAlpha;
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        return this;
    }

    /**
     * 导航栏颜色
     *
     * @param navigationBarColor          the navigation bar color 导航栏颜色
     * @param navigationBarColorTransform the navigation bar color transform  导航栏变色后的颜色
     * @param navigationAlpha             the navigation alpha  透明度
     * @return the immersion bar
     */
    public ImmersionBar navigationBarColor(@ColorRes int navigationBarColor,
                                           @ColorRes int navigationBarColorTransform,
                                           @FloatRange(from = 0f, to = 1f) float navigationAlpha) {
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, navigationBarColor);
        mBarParams.navigationBarColorTransform = ContextCompat.getColor(mActivity, navigationBarColorTransform);
        mBarParams.navigationBarAlpha = navigationAlpha;
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        return this;
    }

    /**
     * 状态栏和导航栏颜色
     *
     * @param barColor the bar color
     * @return the immersion bar
     */
    public ImmersionBar barColor(@ColorRes int barColor) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        return this;
    }

    /**
     * 状态栏和导航栏颜色
     *
     * @param barColor the bar color
     * @param barAlpha the bar alpha
     * @return the immersion bar
     */
    public ImmersionBar barColor(@ColorRes int barColor, @FloatRange(from = 0f, to = 1f) float barAlpha) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;
        mBarParams.statusBarAlpha = barAlpha;
        mBarParams.navigationBarAlpha = barAlpha;
        return this;
    }

    /**
     * 状态栏和导航栏颜色
     *
     * @param barColor          the bar color
     * @param barColorTransform the bar color transform
     * @param barAlpha          the bar alpha
     * @return the immersion bar
     */
    public ImmersionBar barColor(@ColorRes int barColor,
                                 @ColorRes int barColorTransform,
                                 @FloatRange(from = 0f, to = 1f) float barAlpha) {
        mBarParams.statusBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColor = ContextCompat.getColor(mActivity, barColor);
        mBarParams.navigationBarColorTem = mBarParams.navigationBarColor;

        mBarParams.statusBarColorTransform = ContextCompat.getColor(mActivity, barColorTransform);
        mBarParams.navigationBarColorTransform = ContextCompat.getColor(mActivity, barColorTransform);

        mBarParams.statusBarAlpha = barAlpha;
        mBarParams.navigationBarAlpha = barAlpha;
        return this;
    }


    /**
     * 状态栏根据透明度最后变换成的颜色
     *
     * @param statusBarColorTransform the status bar color transform
     * @return the immersion bar
     */
    public ImmersionBar statusBarColorTransform(@ColorRes int statusBarColorTransform) {
        mBarParams.statusBarColorTransform = ContextCompat.getColor(mActivity, statusBarColorTransform);
        return this;
    }

    /**
     * 导航栏根据透明度最后变换成的颜色
     *
     * @param navigationBarColorTransform the m navigation bar color transform
     * @return the immersion bar
     */
    public ImmersionBar navigationBarColorTransform(@ColorRes int navigationBarColorTransform) {
        mBarParams.navigationBarColorTransform = ContextCompat.getColor(mActivity, navigationBarColorTransform);
        return this;
    }

    /**
     * 状态栏和导航栏根据透明度最后变换成的颜色
     *
     * @param barColorTransform the bar color transform
     * @return the immersion bar
     */
    public ImmersionBar barColorTransform(@ColorRes int barColorTransform) {
        mBarParams.statusBarColorTransform = ContextCompat.getColor(mActivity, barColorTransform);
        mBarParams.navigationBarColorTransform = ContextCompat.getColor(mActivity, barColorTransform);
        return this;
    }

    /**
     * 颜色变换支持View
     *
     * @param view the view
     * @return the view transform color
     */
    public ImmersionBar setViewSupportTransformColor(View view) {
        mBarParams.view = view;
        mBarParams.viewColorBeforeTransform = mBarParams.statusBarColor;
        mBarParams.viewColorAfterTransform = mBarParams.statusBarColorTransform;
        return this;
    }

    /**
     * 颜色变换支持View
     *
     * @param view                    the view
     * @param viewColorAfterTransform 变换后的颜色
     * @return the view transform color
     */
    public ImmersionBar setViewSupportTransformColor(View view, @ColorRes int viewColorAfterTransform) {
        mBarParams.view = view;
        mBarParams.viewColorBeforeTransform = mBarParams.statusBarColor;
        mBarParams.viewColorAfterTransform = ContextCompat.getColor(mActivity, viewColorAfterTransform);
        return this;
    }

    /**
     * 颜色变换支持View
     *
     * @param view                     the view
     * @param viewColorBeforeTransform 变换前的颜色
     * @param viewColorAfterTransform  变换后的颜色
     * @return the immersion bar
     */
    public ImmersionBar setViewSupportTransformColor(View view, @ColorRes int viewColorBeforeTransform,
                                                     @ColorRes int viewColorAfterTransform) {
        mBarParams.view = view;
        mBarParams.viewColorBeforeTransform = ContextCompat.getColor(mActivity, viewColorBeforeTransform);
        mBarParams.viewColorAfterTransform = ContextCompat.getColor(mActivity, viewColorAfterTransform);
        return this;
    }

    /**
     * Add 颜色变换支持View
     *
     * @param view the view
     * @return the immersion bar
     */
    public ImmersionBar addViewSupportTransformColor(View view) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(mBarParams.statusBarColor, mBarParams.statusBarColorTransform);
        mBarParams.viewMap.put(view, map);
        return this;
    }

    /**
     * Add 颜色变换支持View
     *
     * @param view                    the view
     * @param viewColorAfterTransform the view color after transform
     * @return the immersion bar
     */
    public ImmersionBar addViewSupportTransformColor(View view, @ColorRes int viewColorAfterTransform) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(mBarParams.statusBarColor, ContextCompat.getColor(mActivity, viewColorAfterTransform));
        mBarParams.viewMap.put(view, map);
        return this;
    }

    /**
     * Add 颜色变换支持View
     *
     * @param view                     the view
     * @param viewColorBeforeTransform the view color before transform
     * @param viewColorAfterTransform  the view color after transform
     * @return the immersion bar
     */
    public ImmersionBar addViewSupportTransformColor(View view, @ColorRes int viewColorBeforeTransform,
                                                     @ColorRes int viewColorAfterTransform) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(ContextCompat.getColor(mActivity, viewColorBeforeTransform),
                ContextCompat.getColor(mActivity, viewColorAfterTransform));
        mBarParams.viewMap.put(view, map);
        return this;
    }

    /**
     * 移除view支持变身
     *
     * @return the immersion bar
     */
    public ImmersionBar removeSupportView() {
        mBarParams.view = null;
        return this;
    }

    /**
     * Remove support view immersion bar.
     *
     * @param view the view
     * @return the immersion bar
     */
    public ImmersionBar removeSupportView(View view) {
        if (view != null) {
            if (view == mBarParams.view) {
                mBarParams.view = null;
            }
            Map<Integer, Integer> map = mBarParams.viewMap.get(view);
            if (map.size() != 0) {
                mBarParams.viewMap.remove(view);
            }
        }
        return this;
    }

    /**
     * Remove support all view immersion bar.
     *
     * @return the immersion bar
     */
    public ImmersionBar removeSupportAllView() {
        if (mBarParams.viewMap.size() != 0) {
            mBarParams.viewMap.clear();
        }
        return this;
    }

    /**
     * 有导航栏的情况下，Activity是否全屏显示
     *
     * @param isFullScreen the is full screen
     * @return the immersion bar
     */
    public ImmersionBar fullScreen(boolean isFullScreen) {
        mBarParams.fullScreen = isFullScreen;
        return this;
    }

    /**
     * 状态栏透明度
     *
     * @param statusAlpha the status alpha
     * @return the immersion bar
     */
    public ImmersionBar statusBarAlpha(@FloatRange(from = 0f, to = 1f) float statusAlpha) {
        mBarParams.statusBarAlpha = statusAlpha;
        return this;
    }

    /**
     * 导航栏透明度
     *
     * @param navigationAlpha the navigation alpha
     * @return the immersion bar
     */
    public ImmersionBar navigationBarAlpha(@FloatRange(from = 0f, to = 1f) float navigationAlpha) {
        mBarParams.navigationBarAlpha = navigationAlpha;
        return this;
    }

    /**
     * 状态栏和导航栏透明度
     *
     * @param barAlpha the bar alpha
     * @return the immersion bar
     */
    public ImmersionBar barAlpha(@FloatRange(from = 0f, to = 1f) float barAlpha) {
        mBarParams.statusBarAlpha = barAlpha;
        mBarParams.navigationBarAlpha = barAlpha;
        return this;
    }

    /**
     * 状态栏字体深色或亮色
     *
     * @param isDarkFont true 深色
     * @return the immersion bar
     */
    public ImmersionBar statusBarDarkFont(boolean isDarkFont) {
        mBarParams.darkFont = isDarkFont;
        return this;
    }

    /**
     * 隐藏导航栏或状态栏
     *
     * @param barHide the bar hide
     * @return the immersion bar
     */
    public ImmersionBar hideBar(BarHide barHide) {
        mBarParams.barHide = barHide;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || OSUtils.isEMUI3_1()) {
            if ((mBarParams.barHide == BarHide.FLAG_HIDE_NAVIGATION_BAR) ||
                    (mBarParams.barHide == BarHide.FLAG_HIDE_BAR)) {
                mBarParams.navigationBarColor = Color.TRANSPARENT;
                mBarParams.fullScreenTemp = true;
            } else {
                mBarParams.navigationBarColor = mBarParams.navigationBarColorTem;
                mBarParams.fullScreenTemp = false;
            }
        }
        return this;
    }

    /**
     * 解决布局与状态栏重叠问题
     *
     * @param fits the fits
     * @return the immersion bar
     */
    public ImmersionBar fitsSystemWindows(boolean fits) {
        mBarParams.fits = fits;
        return this;
    }

    /**
     * 通过状态栏高度动态设置状态栏布局
     *
     * @param view the view
     * @return the immersion bar
     */
    public ImmersionBar statusBarView(View view) {
        mBarParams.statusBarViewByHeight = view;
        return this;
    }

    /**
     * 通过状态栏高度动态设置状态栏布局,只支持在activity里设置
     *
     * @param viewId the view id
     * @return the immersion bar
     */
    public ImmersionBar statusBarView(@IdRes int viewId) {
        mBarParams.statusBarViewByHeight = mActivity.findViewById(viewId);
        return this;
    }

    /**
     * 通过上面配置后初始化后方可成功调用
     */
    public void init() {
        mMap.put(mActivity.getClass().getName(), mBarParams);
        initBar();   //初始化沉浸式
        setStatusBarView();  //通过状态栏高度动态设置状态栏布局
        transformView();  //变色view
    }

    /**
     * 当Activity关闭的时候，在onDestroy方法中调用
     */
    public void destroy() {
        String key = mActivity.getClass().getName();
        if (key != null) {
            if (mBarParams != null) {
                mBarParams = null;
            }
            mMap.remove(key);
        }
    }

    /**
     * 初始化状态栏和导航栏
     */
    private void initBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  //防止系统栏隐藏时内容区域大小发生变化
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()) {
                uiFlags = initBarAboveLOLLIPOP(uiFlags); //初始化5.0以上，包含5.0
                fitsSystemWindows();  //android 5.0以上解决状态栏和布局重叠问题
            } else {
                initBarBelowLOLLIPOP(); //初始化5.0以下，4.4以上沉浸式
                solveNavigation();  //解决android4.4有导航栏的情况下，activity底部被导航栏遮挡的问题和android 5.0以下解决状态栏和布局重叠问题
            }
        }
        uiFlags = hideBar(uiFlags);  //隐藏状态栏或者导航栏
        uiFlags = setStatusBarDarkFont(uiFlags); //设置状态栏字体为暗色
        mWindow.getDecorView().setSystemUiVisibility(uiFlags);
    }

    /**
     * 初始化android 5.0以上状态栏和导航栏
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int initBarAboveLOLLIPOP(int uiFlags) {
        uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;  //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
        if (mBarParams.fullScreen) {
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; //Activity全屏显示，但导航栏不会被隐藏覆盖，导航栏依然可见，Activity底部布局部分会被导航栏遮住。
        }
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  //需要设置这个才能设置状态栏颜色
        mWindow.setStatusBarColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));  //设置状态栏颜色
        mWindow.setNavigationBarColor(ColorUtils.blendARGB(mBarParams.navigationBarColor,
                mBarParams.navigationBarColorTransform, mBarParams.navigationBarAlpha));  //设置导航栏颜色
        return uiFlags;
    }

    /**
     * 初始化android 4.4和emui3.1状态栏和导航栏
     */
    private void initBarBelowLOLLIPOP() {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏，设置这个，如果有导航栏，底部布局会被导航栏遮住
        setupStatusBarView(); //创建一个假的状态栏
        if (mConfig.hasNavigtionBar())  //判断是否存在导航栏
        {
            setupNavBarView();   //创建一个假的导航栏
        }
    }

    /**
     * 解决安卓4.4和EMUI3.1导航栏与状态栏的问题
     */
    private void solveNavigation() {
        // 解决android4.4有导航栏的情况下，activity底部被导航栏遮挡的问题
        if (mConfig.hasNavigtionBar() && !mBarParams.fullScreenTemp && !mBarParams.fullScreen) {
            if (mConfig.isNavigationAtBottom()) { //判断导航栏是否在底部
                if (mBarParams.fits) {
                    mContentView.setPadding(0, getStatusBarHeight(mActivity), 0, mConfig.getNavigationBarHeight()); //有导航栏，获得rootView的根节点，然后设置距离底部的padding值为导航栏的高度值
                } else {
                    mContentView.setPadding(0, 0, 0, mConfig.getNavigationBarHeight());
                }
            } else {
                if (mBarParams.fits) {
                    mContentView.setPadding(0, getStatusBarHeight(mActivity), mConfig.getNavigationBarWidth(), 0); //不在底部，设置距离右边的padding值为导航栏的宽度值
                } else {
                    mContentView.setPadding(0, 0, mConfig.getNavigationBarWidth(), 0);
                }
            }
        } else {
            mContentView.setPadding(0, 0, 0, 0); //没有导航栏，什么都不做
        }
    }

    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。 状态栏和导航栏的颜色不起作用，都是透明色，以最后一次调用为准
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    private int hideBar(int uiFlags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switch (mBarParams.barHide) {
                case FLAG_HIDE_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.INVISIBLE;
                    break;
                case FLAG_HIDE_STATUS_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.INVISIBLE;
                    break;
                case FLAG_HIDE_NAVIGATION_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    break;
                case FLAG_SHOW_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_VISIBLE;
                    break;
            }
        }
        return uiFlags | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    /**
     * Sets status bar dark font.
     * 设置状态栏字体颜色，android6.0以上或者miuiv6以上或者flymeOS
     */
    private int setStatusBarDarkFont(int uiFlags) {
        if (OSUtils.isMIUI6More()) {
            MIUISetStatusBarLightMode();
            return uiFlags;
        }
        if (OSUtils.isFlymeOS4More()) {
            FlymeSetStatusBarLightMode();
            return uiFlags;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mBarParams.darkFont) {
            return uiFlags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            return uiFlags;
        }
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @return boolean 成功执行返回true
     */
    private void FlymeSetStatusBarLightMode() {
        if (mWindow != null) {
            try {
                WindowManager.LayoutParams lp = mWindow.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (mBarParams.darkFont) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                mWindow.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @return boolean 成功执行返回true
     */
    private void MIUISetStatusBarLightMode() {
        if (mWindow != null) {
            Class clazz = mWindow.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (mBarParams.darkFont) {
                    extraFlagField.invoke(mWindow, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(mWindow, 0, darkModeFlag);//清除黑色字体
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 变色view
     * <p>
     * Transform view.
     */
    private void transformView() {
        if (mBarParams.view != null) {
            mBarParams.view.setBackgroundColor(ColorUtils.blendARGB(mBarParams.viewColorBeforeTransform,
                    mBarParams.viewColorAfterTransform, mBarParams.statusBarAlpha));
        }
        if (mBarParams.viewMap.size() != 0) {
            Set<Map.Entry<View, Map<Integer, Integer>>> entrySet = mBarParams.viewMap.entrySet();
            for (Map.Entry<View, Map<Integer, Integer>> entry : entrySet) {
                View view = entry.getKey();
                Map<Integer, Integer> map = entry.getValue();
                Integer colorBefore = mBarParams.statusBarColor;
                Integer colorAfter = mBarParams.statusBarColorTransform;
                for (Map.Entry<Integer, Integer> integerEntry : map.entrySet()) {
                    colorBefore = integerEntry.getKey();
                    colorAfter = integerEntry.getValue();
                }
                if (view != null) {
                    view.setBackgroundColor(ColorUtils.blendARGB(colorBefore, colorAfter, mBarParams.statusBarAlpha));
                }

            }
        }
    }


    /**
     * 通过状态栏高度动态设置状态栏布局
     */
    private void setStatusBarView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mBarParams.statusBarViewByHeight != null) {
            ViewGroup.LayoutParams params = mBarParams.statusBarViewByHeight.getLayoutParams();
            params.height = mConfig.getStatusBarHeight();
            mBarParams.statusBarViewByHeight.setLayoutParams(params);
        }
        mBarParams.statusBarViewByHeight = null;
    }

    /**
     * 解决状态栏和布局重叠问题
     * Fits system windows.
     */
    private void fitsSystemWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()) {
            if (mBarParams.fits) {
                mContentView.setPadding(0, getStatusBarHeight(mActivity), 0, 0);
            } else {
                mContentView.setPadding(0, 0, 0, 0);
            }
        }
    }

    /**
     * 设置一个可以自定义颜色的状态栏
     */
    private void setupStatusBarView() {
        if (mBarParams.statusBarView == null) {
            mBarParams.statusBarView = new View(mActivity);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(mActivity));
        params.gravity = Gravity.TOP;
        if (!isNavigationAtBottom(mActivity)) {
            params.rightMargin = getNavigationBarWidth(mActivity);
        }
        mBarParams.statusBarView.setLayoutParams(params);
        mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));
        mBarParams.statusBarView.setVisibility(View.VISIBLE);
        ViewGroup viewGroup = (ViewGroup) mBarParams.statusBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mBarParams.statusBarView);
        }
        mViewGroup.addView(mBarParams.statusBarView);
    }

    /**
     * 设置一个可以自定义颜色的导航栏
     */
    private void setupNavBarView() {
        if (mBarParams.navigationBarView == null) {
            mBarParams.navigationBarView = new View(mActivity);
        }
        FrameLayout.LayoutParams params;
        if (isNavigationAtBottom(mActivity)) {
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getNavigationBarHeight(mActivity));
            params.gravity = Gravity.BOTTOM;
        } else {
            params = new FrameLayout.LayoutParams(getNavigationBarWidth(mActivity), FrameLayout.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.END;
        }
        mBarParams.navigationBarView.setLayoutParams(params);
        if (!mBarParams.fullScreen && (mBarParams.navigationBarColorTransform == Color.TRANSPARENT)) {
            mBarParams.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.navigationBarColor,
                    Color.BLACK, mBarParams.navigationBarAlpha));
        } else {
            mBarParams.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.navigationBarColor,
                    mBarParams.navigationBarColorTransform, mBarParams.navigationBarAlpha));
        }
        mBarParams.navigationBarView.setVisibility(View.VISIBLE);
        ViewGroup viewGroup = (ViewGroup) mBarParams.navigationBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(mBarParams.navigationBarView);
        }
        mViewGroup.addView(mBarParams.navigationBarView);
    }

    /**
     * Has navigtion bar boolean.
     * 判断是否存在导航栏
     *
     * @param activity the activity
     * @return the boolean
     */
    @TargetApi(14)
    public static boolean hasNavigationBar(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.hasNavigtionBar();
    }

    /**
     * Gets navigation bar height.
     * 获得导航栏的高度
     *
     * @param activity the activity
     * @return the navigation bar height
     */
    @TargetApi(14)
    public static int getNavigationBarHeight(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.getNavigationBarHeight();
    }

    /**
     * Gets navigation bar width.
     * 获得导航栏的宽度
     *
     * @param activity the activity
     * @return the navigation bar width
     */
    @TargetApi(14)
    public static int getNavigationBarWidth(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.getNavigationBarWidth();
    }

    /**
     * Is navigation at bottom boolean.
     * 判断导航栏是否在底部
     *
     * @param activity the activity
     * @return the boolean
     */
    @TargetApi(14)
    public static boolean isNavigationAtBottom(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.isNavigationAtBottom();
    }

    /**
     * Gets status bar height.
     * 或得状态栏的高度
     *
     * @param activity the activity
     * @return the status bar height
     */
    @TargetApi(14)
    public static int getStatusBarHeight(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.getStatusBarHeight();
    }

    /**
     * Gets action bar height.
     * 或得ActionBar得高度
     *
     * @param activity the activity
     * @return the action bar height
     */
    @TargetApi(14)
    public static int getActionBarHeight(Activity activity) {
        BarConfig config = new BarConfig(activity);
        return config.getActionBarHeight();
    }

    /**
     * 判断手机支不支持状态栏变色
     * Is support status bar dark font boolean.
     *
     * @return the boolean
     */
    public static boolean isSupportStatusBarDarkFont() {
        if (OSUtils.isMIUI6More() || OSUtils.isFlymeOS4More()
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets bar params.
     *
     * @return the bar params
     */
    public BarParams getBarParams() {
        return mBarParams;
    }
}
