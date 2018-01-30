package com.example.lfy.basicframes.ui.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.lfy.basicframes.R;

import java.util.List;
import java.util.regex.Pattern;

public class WebviewActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private Button bt;
    private String title="小陌钱包1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView= (WebView) findViewById(R.id.webviews);
        bt= (Button) findViewById(R.id.bt_add);

        url = getIntent().getStringExtra("url");
        Log.e("url",url.toString());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setDrawingCacheEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebChromeClient(new WVChromeClient());
        webView.setWebViewClient(new WVClient());

        //添加快捷方式
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断是否已存桌面快捷方式
                if(!hasShortcut(title)){
                    //添加图标
                    addIcon(title,R.drawable.dayu);
                }else {
                    Toast.makeText(WebviewActivity.this, "已有一个桌面图标", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //添加图标  name  名称   icons  图标
    private void addIcon(String name,int icons) {
        // 安装的Intent
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 快捷图标是否允许重复
        shortcut.putExtra("duplicate", false);

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("url", url);//url地址
        //设置一个标识  当前有快捷方式
        shortcutIntent.putExtra("isture",true);
        shortcutIntent.setClassName(getPackageName(), "com.example.lfy.basicframes.ui.activity.MainActivity");

        /**
         * 最最最重要的就是为图标添加Flag——>shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         添加这个flag的意思就是：必须要与FLAG_ACTIVITY_NEW_TASK配合使用，这个activity新启动一个栈，原来栈被清空，栈中的activity也被销毁。
         * */
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        // 快捷图标  TODO 图标处理   将网络图片下载并创建快捷方式图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, icons);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        // 发送广播
        sendBroadcast(shortcut);
        Toast.makeText(this, "创建成功，请到桌面查看", Toast.LENGTH_SHORT).show();
    }


    //判断是否已经存在相同名字的桌面图标
    protected boolean hasShortcut(String appName) {
        String authority = getAuthorityFromPermission();
        if (authority == null|| TextUtils.isEmpty(authority)) {
            return false;
        }
        String url = "content://" + authority + "/favorites?notify=true";
        try {
            Uri CONTENT_URI = Uri.parse(url);
            Cursor c = getContentResolver().query(CONTENT_URI, null, "title= ?", new String[]{appName}, null);
            if (c != null && c.moveToNext()) {
                c.close();
                return true;
            }
        } catch (Exception e) {
            Log.e("url",e.toString());
        }
        return false;
    }

    private boolean hasShortcut() {
        boolean isInstallShortcut = false;
        final ContentResolver cr = getContentResolver();
        final String AUTHORITY =getAuthorityFromPermission();
        final Uri CONTENT_URI = Uri.parse("content://" +AUTHORITY + "/favorites?notify=true");
        Cursor c = cr.query(CONTENT_URI,new String[] {"title","iconResource" },"title=?",
                new String[] {title}, null);
        if(c!=null && c.getCount()>0){
            isInstallShortcut = true ;
        }
        return isInstallShortcut ;}



    // 先得到默认的Launcher(图标)通过内容共享者获取
    protected String getAuthorityFromPermission() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager mPackageManager = getPackageManager();
        ResolveInfo resolveInfo = mPackageManager.resolveActivity(intent, 0);
        if (resolveInfo == null) {
            return null;
        }
        List<ProviderInfo> info = mPackageManager.queryContentProviders(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.applicationInfo.uid, PackageManager.GET_PROVIDERS);
        if (info != null) {
            for (int j = 0; j < info.size(); j++) {
                ProviderInfo provider = info.get(j);
                if (provider.readPermission == null) {
                    continue;
                }
                if (Pattern.matches(".*launcher.*READ_SETTINGS", provider.readPermission)) {
                    return provider.authority;
                }
            }
        }
        return null;
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

//            webBinding.progress.setVisibility(GONE);
            super.onPageFinished(view, url);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.loadUrl(url);
        }
    }

    //进度显示
    private class WVChromeClient extends WebChromeClient {


        @Override
        public void onProgressChanged(WebView view, int newProgress) {

//
//            if (newProgress == 100) {
//                webBinding.progress.setVisibility(GONE);
//            } else {
//                if (webBinding.progress.getVisibility() == GONE)
//                    webBinding.progress.setVisibility(VISIBLE);
//                webBinding.progress.setProgress(newProgress);
//            }
            super.onProgressChanged(view, newProgress);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
