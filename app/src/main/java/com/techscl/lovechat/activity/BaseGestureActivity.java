package com.techscl.lovechat.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.techscl.applib.controller.HXSDKHelper;
import com.techscl.utils.To;
import com.umeng.analytics.MobclickAgent;

public class BaseGestureActivity extends FragmentActivity implements  GestureDetector.OnGestureListener, View.OnTouchListener {

    private GestureDetector gestureDetector;
    private int verticalMinDistance = 30;
    private int middleDistance = 150;
    private int minVelocity = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        gestureDetector = new GestureDetector(this);

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

}
