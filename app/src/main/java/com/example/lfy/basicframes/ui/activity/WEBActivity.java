package com.example.lfy.basicframes.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ProgressBar;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.databinding.ActivityWebBinding;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.view.TitleBar;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WEBActivity extends BaseActivity {

    private String url;
    private ActivityWebBinding webBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webBinding=DataBindingUtil.setContentView(this,R.layout.activity_web);

        //状态栏相关
        StatusBarUtils.setColorNoTranslucent(this,Color.parseColor("#9aeaba"));
//        //状态栏一起拉动  但是湛底的activity状态栏不能是透明的   否则滑动过程中是透明色 完成滑动后会恢复
//        StatusBarUtils.setColorForSwipeBack(this,Color.parseColor("#9aeaba"),0);

        setSupportActionBar(webBinding.toolbarWeb);
        webBinding.titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        url = getIntent().getStringExtra("url");
        webBinding.webview.loadUrl(url);
        webBinding.webview.getSettings().setJavaScriptEnabled(true);
        webBinding.webview.getSettings().setAppCacheEnabled(true);
        webBinding.webview.getSettings().setDomStorageEnabled(true);
        webBinding.webview.setDrawingCacheEnabled(true);
        //设置缓存模式
        webBinding.webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webBinding.webview.setWebChromeClient(new WVChromeClient());
        webBinding.webview.setWebViewClient(new WVClient());

    }

    private class WVClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //在当前Activity打开
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //https忽略证书问题
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            webBinding.progress.setVisibility(GONE);
            super.onPageFinished(view, url);

        }

    }

    //进度显示
    private class WVChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {


            if (newProgress == 100) {
                webBinding.progress.setVisibility(GONE);
            } else {
                if (webBinding.progress.getVisibility() == GONE)
                    webBinding.progress.setVisibility(VISIBLE);
                webBinding.progress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webBinding.webview.canGoBack()) {
                webBinding.webview.goBack();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
