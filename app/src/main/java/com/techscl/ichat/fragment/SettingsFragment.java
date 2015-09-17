package com.techscl.ichat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.techscl.ichat.R;
import com.techscl.ichat.activity.BlacklistActivity;
import com.techscl.ichat.activity.LoginActivity;
import com.techscl.ichat.activity.MainActivity;
import com.techscl.ichat.activity.OfflinePushNickActivity;
import com.techscl.ichat.activity.UserProfileActivity;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.base.Constant;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.base.DemoHXSDKModel;

/**
 * 设置界面
 *
 * @author Administrator
 */
public class SettingsFragment extends Fragment implements OnClickListener {

    DemoHXSDKModel model;
    private RelativeLayout rl_switch_notification;
    private RelativeLayout rl_switch_sound;
    private RelativeLayout rl_switch_vibrate;
    private RelativeLayout rl_switch_speaker;
    private ImageView iv_switch_open_notification;
    private ImageView iv_switch_close_notification;
    private ImageView iv_switch_open_sound;
    private ImageView iv_switch_close_sound;
    private ImageView iv_switch_open_vibrate;
    private ImageView iv_switch_close_vibrate;
    private ImageView iv_switch_open_speaker;
    private ImageView iv_switch_close_speaker;
    private TextView textview1, textview2;
    private LinearLayout blacklistContainer;
    private LinearLayout userProfileContainer;
    private Button logoutBtn;
    private RelativeLayout rl_switch_chatroom_leave;
    private ImageView iv_switch_room_owner_leave_allow;
    private ImageView iv_switch_room_owner_leave_disallow;
    private EMChatOptions chatOptions;
    private LinearLayout pushNick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        return inflater.inflate(R.layout.fragment_conversation_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;

        initView();

        chatOptions = EMChatManager.getInstance().getChatOptions();

        model = (DemoHXSDKModel) HXSDKHelper.getInstance().getModel();

        // 震动和声音总开关，来消息时，是否允许此开关打开
        // the vibrate and sound notification are allowed or not?
        if (model.getSettingMsgNotification()) {
            iv_switch_open_notification.setVisibility(View.VISIBLE);
            iv_switch_close_notification.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_notification.setVisibility(View.INVISIBLE);
            iv_switch_close_notification.setVisibility(View.VISIBLE);
        }

        // 是否打开声音
        // sound notification is switched on or not?
        if (model.getSettingMsgSound()) {
            iv_switch_open_sound.setVisibility(View.VISIBLE);
            iv_switch_close_sound.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_sound.setVisibility(View.INVISIBLE);
            iv_switch_close_sound.setVisibility(View.VISIBLE);
        }

        // 是否打开震动
        // vibrate notification is switched on or not?
        if (model.getSettingMsgVibrate()) {
            iv_switch_open_vibrate.setVisibility(View.VISIBLE);
            iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
            iv_switch_close_vibrate.setVisibility(View.VISIBLE);
        }

        // 是否打开扬声器
        // the speaker is switched on or not?
        if (model.getSettingMsgSpeaker()) {
            iv_switch_open_speaker.setVisibility(View.VISIBLE);
            iv_switch_close_speaker.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_speaker.setVisibility(View.INVISIBLE);
            iv_switch_close_speaker.setVisibility(View.VISIBLE);
        }

        // 是否允许聊天室owner leave
        if (model.isChatroomOwnerLeaveAllowed()) {
            iv_switch_room_owner_leave_allow.setVisibility(View.VISIBLE);
            iv_switch_room_owner_leave_disallow.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_room_owner_leave_allow.setVisibility(View.INVISIBLE);
            iv_switch_room_owner_leave_disallow.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化
     */
    private void initView() {
        rl_switch_notification = (RelativeLayout) getView().findViewById(R.id.rl_switch_notification);
        rl_switch_sound = (RelativeLayout) getView().findViewById(R.id.rl_switch_sound);
        rl_switch_vibrate = (RelativeLayout) getView().findViewById(R.id.rl_switch_vibrate);
        rl_switch_speaker = (RelativeLayout) getView().findViewById(R.id.rl_switch_speaker);
        rl_switch_chatroom_leave = (RelativeLayout) getView().findViewById(R.id.rl_switch_chatroom_owner_leave);

        iv_switch_open_notification = (ImageView) getView().findViewById(R.id.iv_switch_open_notification);
        iv_switch_close_notification = (ImageView) getView().findViewById(R.id.iv_switch_close_notification);
        iv_switch_open_sound = (ImageView) getView().findViewById(R.id.iv_switch_open_sound);
        iv_switch_close_sound = (ImageView) getView().findViewById(R.id.iv_switch_close_sound);
        iv_switch_open_vibrate = (ImageView) getView().findViewById(R.id.iv_switch_open_vibrate);
        iv_switch_close_vibrate = (ImageView) getView().findViewById(R.id.iv_switch_close_vibrate);
        iv_switch_open_speaker = (ImageView) getView().findViewById(R.id.iv_switch_open_speaker);
        iv_switch_close_speaker = (ImageView) getView().findViewById(R.id.iv_switch_close_speaker);

        iv_switch_room_owner_leave_allow = (ImageView) getView().findViewById(R.id.iv_switch_chatroom_owner_leave_allow);
        iv_switch_room_owner_leave_disallow = (ImageView) getView().findViewById(R.id.iv_switch_chatroom_owner_leave_not_allow);


        logoutBtn = (Button) getView().findViewById(R.id.btn_logout);
        if (!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())) {
            logoutBtn.setText(getString(R.string.button_logout) + "(" + EMChatManager.getInstance().getCurrentUser() + ")");
        }

        textview1 = (TextView) getView().findViewById(R.id.textview1);
        textview2 = (TextView) getView().findViewById(R.id.textview2);

        blacklistContainer = (LinearLayout) getView().findViewById(R.id.ll_black_list);
        userProfileContainer = (LinearLayout) getView().findViewById(R.id.ll_user_profile);
        pushNick = (LinearLayout) getView().findViewById(R.id.ll_set_push_nick);

        blacklistContainer.setOnClickListener(this);
        userProfileContainer.setOnClickListener(this);
        rl_switch_notification.setOnClickListener(this);
        rl_switch_sound.setOnClickListener(this);
        rl_switch_vibrate.setOnClickListener(this);
        rl_switch_speaker.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        pushNick.setOnClickListener(this);
        rl_switch_chatroom_leave.setOnClickListener(this);
    }

    /**
     * 点击事件监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_switch_notification:
                if (iv_switch_open_notification.getVisibility() == View.VISIBLE) {
                    iv_switch_open_notification.setVisibility(View.INVISIBLE);
                    iv_switch_close_notification.setVisibility(View.VISIBLE);
                    rl_switch_sound.setVisibility(View.GONE);
                    rl_switch_vibrate.setVisibility(View.GONE);
                    textview1.setVisibility(View.GONE);
                    textview2.setVisibility(View.GONE);
                    chatOptions.setNotificationEnable(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);

                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(false);
                } else {
                    iv_switch_open_notification.setVisibility(View.VISIBLE);
                    iv_switch_close_notification.setVisibility(View.INVISIBLE);
                    rl_switch_sound.setVisibility(View.VISIBLE);
                    rl_switch_vibrate.setVisibility(View.VISIBLE);
                    textview1.setVisibility(View.VISIBLE);
                    textview2.setVisibility(View.VISIBLE);
                    chatOptions.setNotificationEnable(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgNotification(true);
                }
                break;
            case R.id.rl_switch_sound:
                if (iv_switch_open_sound.getVisibility() == View.VISIBLE) {
                    iv_switch_open_sound.setVisibility(View.INVISIBLE);
                    iv_switch_close_sound.setVisibility(View.VISIBLE);
                    chatOptions.setNoticeBySound(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
                } else {
                    iv_switch_open_sound.setVisibility(View.VISIBLE);
                    iv_switch_close_sound.setVisibility(View.INVISIBLE);
                    chatOptions.setNoticeBySound(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
                }
                break;
            case R.id.rl_switch_vibrate:
                if (iv_switch_open_vibrate.getVisibility() == View.VISIBLE) {
                    iv_switch_open_vibrate.setVisibility(View.INVISIBLE);
                    iv_switch_close_vibrate.setVisibility(View.VISIBLE);
                    chatOptions.setNoticedByVibrate(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(false);
                } else {
                    iv_switch_open_vibrate.setVisibility(View.VISIBLE);
                    iv_switch_close_vibrate.setVisibility(View.INVISIBLE);
                    chatOptions.setNoticedByVibrate(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_speaker:
                if (iv_switch_open_speaker.getVisibility() == View.VISIBLE) {
                    iv_switch_open_speaker.setVisibility(View.INVISIBLE);
                    iv_switch_close_speaker.setVisibility(View.VISIBLE);
                    chatOptions.setUseSpeaker(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgSpeaker(false);
                } else {
                    iv_switch_open_speaker.setVisibility(View.VISIBLE);
                    iv_switch_close_speaker.setVisibility(View.INVISIBLE);
                    chatOptions.setUseSpeaker(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                break;
            case R.id.rl_switch_chatroom_owner_leave:
                if (this.iv_switch_room_owner_leave_allow.getVisibility() == View.VISIBLE) {
                    iv_switch_room_owner_leave_allow.setVisibility(View.INVISIBLE);
                    iv_switch_room_owner_leave_disallow.setVisibility(View.VISIBLE);
                    chatOptions.allowChatroomOwnerLeave(false);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    model.allowChatroomOwnerLeave(false);

                } else {
                    iv_switch_room_owner_leave_allow.setVisibility(View.VISIBLE);
                    iv_switch_room_owner_leave_disallow.setVisibility(View.INVISIBLE);
                    chatOptions.allowChatroomOwnerLeave(true);
                    EMChatManager.getInstance().setChatOptions(chatOptions);
                    model.allowChatroomOwnerLeave(true);
                }
                break;
            case R.id.btn_logout: //退出登陆
                showPopupWindow();
                break;
            case R.id.ll_black_list:
                startActivity(new Intent(getActivity(), BlacklistActivity.class));
                break;
            case R.id.ll_set_push_nick:
                startActivity(new Intent(getActivity(), OfflinePushNickActivity.class));
                break;
            case R.id.ll_user_profile:
                startActivity(new Intent(getActivity(), UserProfileActivity.class).putExtra("setting", true));
                break;
            default:
                break;
        }

    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHXSDKHelper.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        ((MainActivity) getActivity()).finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(getActivity(), "unbind devicetokens failed", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((MainActivity) getActivity()).isConflict) {
            outState.putBoolean("isConflict", true);
        } else if (((MainActivity) getActivity()).getCurrentAccountRemoved()) {
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
    /**
     * 显示popupWindow
     */
    private void showPopupWindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logout_window, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);


        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(getActivity().findViewById(R.id.btn_logout),
                Gravity.BOTTOM, 0, 0);

        // 这里检验popWindow里的button是否可以点击
        Button cancel_btn = (Button) view.findViewById(R.id.cancel_btn);
        Button logout_btn = (Button) view.findViewById(R.id.logout_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

    }
}
