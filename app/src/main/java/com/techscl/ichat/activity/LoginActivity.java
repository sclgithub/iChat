package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.techscl.ichat.LoveChatApp;
import com.techscl.ichat.R;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.base.Constant;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.db.UserDao;
import com.techscl.ichat.domain.User;
import com.techscl.ichat.utils.CommonUtils;
import com.techscl.ichat.utils.L;
import com.techscl.ichat.utils.To;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登陆页面
 */
public class LoginActivity extends BaseGestureActivity {
    public static final int REQUEST_CODE_SETNICK = 1;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private boolean progressShow;
    private boolean autoLogin = false;

    private String currentUsername;
    private String currentPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 如果用户名密码都有，直接进入主页面
        if (DemoHXSDKHelper.getInstance().isLogined()) {
            autoLogin = true;
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

            return;
        }
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);

        // 如果用户名改变，清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (LoveChatApp.getInstance().getUserName() != null) {
            usernameEditText.setText(LoveChatApp.getInstance().getUserName());
        }
    }

    /**
     * 登录
     *
     * @param view
     */
    public void login(View view) {
        if (!CommonUtils.isNetWorkConnected(this)) {
            To.showShort(R.string.network_isnot_available);
            return;
        }
        currentUsername = usernameEditText.getText().toString().trim();
        currentPassword = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            To.showShort(R.string.User_name_cannot_be_empty);
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            To.showShort(R.string.Password_cannot_be_empty);
            return;
        }

        progressShow = true;
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
        pd.show();

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                if (!progressShow) {
                    return;
                }
                // 登陆成功，保存用户名密码
                LoveChatApp.getInstance().setUserName(currentUsername);
                LoveChatApp.getInstance().setPassword(currentPassword);

                try {
                    // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                    // ** manually load all local groups and
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    // 处理好友和群组
                    initializeContacts();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 取好友或者群聊失败，不让进入主页面
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            DemoHXSDKHelper.getInstance().logout(true, null);
                            To.showShort(R.string.login_failure_failed);

                        }
                    });
                    return;
                }
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
                        LoveChatApp.currentUserNick.trim());
                if (!updatenick) {
                    L.e("update current user nick fail");
                }
                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // 进入主页面
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        To.showShort(getString(R.string.Login_failed));
                    }
                });
            }
        });
    }

    private void initializeContacts() {
        Map<String, User> userlist = new HashMap<String, User>();
        // 添加user"申请与通知"
        User newFriends = new User();
        newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
        String strChat = getResources().getString(
                R.string.Application_and_notify);
        newFriends.setNick(strChat);

        userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
        // 添加"群聊"
        User groupUser = new User();
        String strGroup = getResources().getString(R.string.group_chat);
        groupUser.setUsername(Constant.GROUP_USERNAME);
        groupUser.setNick(strGroup);
        groupUser.setHeader("");
        userlist.put(Constant.GROUP_USERNAME, groupUser);

        // 添加"Robot"
        User robotUser = new User();
        String strRobot = getResources().getString(R.string.robot_chat);
        robotUser.setUsername(Constant.CHAT_ROBOT);
        robotUser.setNick(strRobot);
        robotUser.setHeader("");
        userlist.put(Constant.CHAT_ROBOT, robotUser);

        // 存入内存
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
        // 存入db
        UserDao dao = new UserDao(LoginActivity.this);
        List<User> users = new ArrayList<User>(userlist.values());
        dao.saveContactList(users);
    }

    /**
     * 注册
     *
     * @param view
     */
    public void register(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 10) {
            usernameEditText.setText(data.getStringExtra("username"));
            passwordEditText.setText(data.getStringExtra("password"));
        }
    }
}
