package com.techscl.ichat.skin;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 标题管理器
 * 
 * @date 2014-9-28
 * @author xiaohua Deng
 */
public class Define
{
	public final static String SHARED_PREFERENCES_NAME = "andnav_preferences";

    public final static String PREF_THEME_RESID_ID = "theme_resid";
    
    public final static String TITLE_ID ="title_id";
    
//    public static void setTitle(Activity act)
//    {
//    	  SharedPreferences sPref = act.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
//        TextView title = (TextView) act.findViewById(R.id.titleTv);
//        int title_resid = sPref.getInt(TITLE_ID, R.drawable.color1);
//        title.setBackgroundResource(title_resid);
//    }
    public static void setValue(Activity act,int theme_resid,int title_resid)
    {
    	 SharedPreferences sPref = act.getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
    	 Editor editor = sPref.edit();
    	 editor.putInt(PREF_THEME_RESID_ID, theme_resid);
    	 editor.putInt(TITLE_ID, title_resid);
    	 editor.commit();
    }


}
