package com.techscl.ichat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

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
        webView= (WebView) findViewById(R.id.webView);
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
        webView.loadUrl(intent.getStringExtra("url"));

    }
}
