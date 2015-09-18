package com.techscl.ichat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.techscl.ichat.R;
import com.techscl.ichat.utils.L;

/**
 * Created by songchunlin on 15/9/17.
 */
public class BuyActivity extends BaseGestureActivity {
    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_buy);
        Intent intent = getIntent();
        webView = (WebView) findViewById(R.id.webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url = intent.getStringExtra("url").substring(7);
        L.i(url);
        webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new webViewClient());
        webView.loadUrl(intent.getStringExtra("url"));
        //点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键时的操作
                        webView.goBack();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }

    class webViewClient extends WebViewClient {

        //重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

        @Override

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            //如果不需要其他对点击链接事件的处理返回true，否则返回false

            return true;

        }
    }
}
