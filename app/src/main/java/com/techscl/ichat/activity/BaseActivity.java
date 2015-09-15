package com.techscl.ichat.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.skin.ExitApplication;
import com.techscl.ichat.skin.SkinSettingManager;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends ActionBarActivity {
    private SkinSettingManager mSettingManager;
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

        ExitApplication.getInstance().addActivity(this);
//	     Define.setTitle(this);
        mSettingManager=new SkinSettingManager(this);
        mSettingManager.initSkins();
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
