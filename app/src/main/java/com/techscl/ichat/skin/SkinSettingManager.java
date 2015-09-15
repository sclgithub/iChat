package com.techscl.ichat.skin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

import com.techscl.ichat.R;


/**
 * PadQzone皮肤管理器
 *
 * @author xiaohua Deng
 * @date 2014-9-28
 */
public class SkinSettingManager {


    public final static String SKIN_PREF = "skinSetting";

    public SharedPreferences skinSettingPreference;

    private int[] skinResources = {R.mipmap.theme1,
            R.mipmap.theme2, R.mipmap.theme3, R.mipmap.theme4,
            R.mipmap.theme5, R.mipmap.theme6
    };

    private Activity mActivity;
    private LinearLayout mlayout;

    public SkinSettingManager(Activity activity, LinearLayout layout) {
        this.mActivity = activity;
        this.mlayout = layout;
        skinSettingPreference = mActivity.getSharedPreferences(SKIN_PREF, 3);
    }

    public SkinSettingManager(Activity activity) {
        this.mActivity = activity;
        skinSettingPreference = mActivity.getSharedPreferences(SKIN_PREF, 3);
    }

    /**
     * 获取当前程序的皮肤序号
     *
     * @return
     */
    public int getSkinType() {
        String key = "skin_type";
        return skinSettingPreference.getInt(key, 0);
    }

    /**
     * 把皮肤序号写到全局设置里去
     *
     * @param j
     */
    public void setSkinType(int j) {
        SharedPreferences.Editor editor = skinSettingPreference.edit();
        String key = "skin_type";

        editor.putInt(key, j);
        editor.commit();
    }

    /**
     * 获取当前皮肤的背景图资源id
     *
     * @return
     */
    public int getCurrentSkinRes() {
        int skinLen = skinResources.length;
        int getSkinLen = getSkinType();
        if (getSkinLen >= skinLen) {
            getSkinLen = 0;
        }

        return skinResources[getSkinLen];
    }

    /**
     * 用于导航栏皮肤按钮切换皮肤
     */
    public void toggleSkins(int skinType) {
        setSkinType(skinType);
        mActivity.getWindow().setBackgroundDrawable(null);
        try {
            mActivity.getWindow().setBackgroundDrawableResource(getCurrentSkinRes());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于初始化皮肤
     */
    public void initSkins() {
        if (mlayout == null) {
            mActivity.getWindow().setBackgroundDrawableResource(getCurrentSkinRes());
        } else {
            mlayout.setBackgroundResource(getCurrentSkinRes());
            ;
        }
    }
}
