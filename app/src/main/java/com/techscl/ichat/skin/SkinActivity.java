package com.techscl.ichat.skin;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.techscl.ichat.R;
import com.techscl.ichat.activity.BaseGestureActivity;


/**
 * 皮肤设置界面
 *
 * @author xiaohua Deng
 * @date 2014-9-28
 */
public class SkinActivity extends BaseGestureActivity implements OnClickListener {

    private SkinSettingManager mSettingManager;
    private TextView title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myskin);
        mSettingManager = new SkinSettingManager(this);
        mSettingManager.initSkins();

        findViewById(R.id.imageView1).setOnClickListener(this);
        findViewById(R.id.imageView2).setOnClickListener(this);
        findViewById(R.id.imageView3).setOnClickListener(this);
        findViewById(R.id.imageView4).setOnClickListener(this);
        findViewById(R.id.imageView5).setOnClickListener(this);
        findViewById(R.id.imageView6).setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setTitle(R.string.change_skin);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView1:
                mSettingManager.toggleSkins(0);
                break;
            case R.id.imageView2:
                mSettingManager.toggleSkins(1);
                break;
            case R.id.imageView3:
                mSettingManager.toggleSkins(2);
                break;
            case R.id.imageView4:
                mSettingManager.toggleSkins(3);
                break;
            case R.id.imageView5:
                mSettingManager.toggleSkins(4);
                break;
            case R.id.imageView6:
                mSettingManager.toggleSkins(5);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return false;
        }
        return false;
    }

}
