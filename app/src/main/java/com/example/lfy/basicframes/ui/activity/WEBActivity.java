package com.example.lfy.basicframes.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ProgressBar;

import com.example.lfy.basicframes.R;
import com.example.lfy.basicframes.ui.base.BaseActivity;
import com.example.lfy.basicframes.utill.StatusBarUtils;
import com.example.lfy.basicframes.view.TitleBar;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WEBActivity extends BaseActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.toolbar_web)
    Toolbar toolbarWeb;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        //状态栏相关
        StatusBarUtils.setColorNoTranslucent(this,Color.parseColor("#9aeaba"));
        setSupportActionBar(toolbarWeb);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setDrawingCacheEnabled(true);
        //设置缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.setWebChromeClient(new WVChromeClient());
        webview.setWebViewClient(new WVClient());

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

            progress.setVisibility(GONE);
            super.onPageFinished(view, url);

        }

    }

    //进度显示
    private class WVChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {


            if (newProgress == 100) {
                progress.setVisibility(GONE);
            } else {
                if (progress.getVisibility() == GONE)
                    progress.setVisibility(VISIBLE);
                progress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
