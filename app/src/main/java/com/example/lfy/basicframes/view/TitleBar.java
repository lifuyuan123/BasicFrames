package com.example.lfy.basicframes.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lfy.basicframes.R;

/**
 * titleBar  公用标题栏
 * */

public class TitleBar extends RelativeLayout {

    protected LinearLayout leftLayout;
    protected ImageView leftImage;
    protected LinearLayout rightLayout;
    protected ImageView rightImage;
    protected TextView titleView;
    protected TextView rightTitleView;
    protected TextView leftTitleView;
    protected RelativeLayout titleLayout;
    protected View line;


    public ImageView getLeftImage() {
        return leftImage;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public TextView getRightTitleView() {
        return rightTitleView;
    }

    public TextView getLeftTitleView() {
        return leftTitleView;
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
        leftLayout =findViewById(R.id.left_layout);
        leftImage =findViewById(R.id.left_image);
        rightLayout =findViewById(R.id.right_layout);
        rightImage =findViewById(R.id.right_image);
        titleView =findViewById(R.id.title);
        rightTitleView =findViewById(R.id.right_title);
        leftTitleView =findViewById(R.id.left_title);
        titleLayout =findViewById(R.id.root);
        line = findViewById(R.id.line);

        parseStyle(context, attrs);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
            String title = ta.getString(R.styleable.TitleBar_titleBarTitle);
            String rightTitle = ta.getString(R.styleable.TitleBar_titleBarRightTitle);
            String leftTitle = ta.getString(R.styleable.TitleBar_titleBarLeftTitle);
            int titleColor = ta.getColor(R.styleable.TitleBar_titleBarTitleColor, Color.WHITE);
            boolean isShowLine = ta.getBoolean(R.styleable.TitleBar_titleBarShowLine, false);
            float titleSize=ta.getDimension(R.styleable.TitleBar_titleBarTitleSize,20);
            float righttitleSize=ta.getDimension(R.styleable.TitleBar_titleBarTitleSize,18);
            titleView.setText(title);
            rightTitleView.setText(rightTitle);
            rightTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,righttitleSize);
            leftTitleView.setText(leftTitle);

            titleView.setTextColor(titleColor);
            titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,titleSize);
                rightTitleView.setTextColor(titleColor);
                leftTitleView.setTextColor(titleColor);
                if (isShowLine)
                    line.setVisibility(View.VISIBLE);
                else
                    line.setVisibility(View.GONE);



                Drawable leftDrawable = ta.getDrawable(R.styleable.TitleBar_titleBarLeftImage);
                if (null != leftDrawable) {
                    leftImage.setImageDrawable(leftDrawable);
                }
                Drawable rightDrawable = ta.getDrawable(R.styleable.TitleBar_titleBarRightImage);
                if (null != rightDrawable) {
                    rightImage.setImageDrawable(rightDrawable);
                }

                Drawable background = ta.getDrawable(R.styleable.TitleBar_titleBarBackground);
                if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

            ta.recycle();
        }
    }

    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }

    public void setLeftLayoutClickListener(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setRightLayoutClickListener(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public void setLeftLayoutVisibility(int visibility) {
        leftLayout.setVisibility(visibility);
    }

    public void setRightLayoutVisibility(int visibility) {
        rightLayout.setVisibility(visibility);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    public LinearLayout getRightLayout() {
        return rightLayout;
    }
}