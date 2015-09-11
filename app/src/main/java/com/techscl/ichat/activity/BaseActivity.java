package com.techscl.ichat.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onresume时，取消notification显示
        HXSDKHelper.getInstance().getNotifier().reset();

        // umeng
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // umeng
        MobclickAgent.onPause(this);
    }


    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
