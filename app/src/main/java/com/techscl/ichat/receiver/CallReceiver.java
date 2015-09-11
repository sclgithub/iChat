package com.techscl.ichat.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.easemob.util.EMLog;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.activity.VideoCallActivity;
import com.techscl.ichat.activity.VoiceCallActivity;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!DemoHXSDKHelper.getInstance().isLogined())
            return;
        //拨打方username
        String from = intent.getStringExtra("from");
        //call type
        String type = intent.getStringExtra("type");
        if ("video".equals(type)) { //视频通话
            context.startActivity(new Intent(context, VideoCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else { //音频通话
            context.startActivity(new Intent(context, VoiceCallActivity.class).
                    putExtra("username", from).putExtra("isComingCall", true).
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        EMLog.d("CallReceiver", "app received a incoming call");
    }

}