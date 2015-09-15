package com.techscl.ichat.skin;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 完全退出
 * 
 * @date 2014-9-28
 * @author xiaohua Deng
 */
public class ExitApplication extends Application {

    private List<Activity> list = new ArrayList<Activity>();
 
    private static ExitApplication ea;

    private ExitApplication() {

    }

    public static ExitApplication getInstance() {
        if (null == ea) {
            ea = new ExitApplication();
        }
        return ea;
    }

   
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }
}