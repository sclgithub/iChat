package com.techscl.ichat.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.techscl.ichat.R;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.skin.ExitApplication;
import com.techscl.ichat.skin.SkinSettingManager;
import com.techscl.ichat.utils.To;
import com.umeng.analytics.MobclickAgent;

public class BaseGestureActivity extends FragmentActivity implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector gestureDetector;
    private int verticalMinDistance = 30;
    private int middleDistance = 150;
    private int minVelocity = 0;
    private SkinSettingManager mSettingManager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        gestureDetector = new GestureDetector(this);

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
        mSettingManager = new SkinSettingManager(this);
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

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity && (Math.abs(e2.getY() - e1.getY()) < middleDistance)) {
            finish();
        } else if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity && (Math.abs(e2.getY() - e1.getY()) < middleDistance)) {
            To.showShort("左滑");
        }

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

}
