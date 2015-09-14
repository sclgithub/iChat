package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easemob.chat.EMContactManager;
import com.techscl.ichat.LoveChatApp;
import com.techscl.ichat.R;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.utils.To;

public class AddContactActivity extends BaseGestureActivity {
    private EditText editText;
    private LinearLayout searchedUserLayout;
    private TextView nameText, mTextView;
    private Button searchBtn;
    private ImageView avatar;
    private InputMethodManager inputMethodManager;
    private String toAddUsername;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initView();
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        if (username != "") {
            editText.setText(username);
        }
        String strAdd = getResources().getString(R.string.add_friend);
        String strUserName = getResources().getString(R.string.user_name);
        editText.setHint(strUserName);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 初始化
     */
    private void initView() {

        editText = (EditText) findViewById(R.id.edit_note);
        searchedUserLayout = (LinearLayout) findViewById(R.id.ll_user);
        nameText = (TextView) findViewById(R.id.name);
        searchBtn = (Button) findViewById(R.id.search);
        avatar = (ImageView) findViewById(R.id.avatar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.add_friend));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back(v);
            }
        });
    }


    /**
     * 查找contact
     *
     * @param v
     */
    public void searchContact(View v) {
        final String name = editText.getText().toString();
        String saveText = searchBtn.getText().toString();

        if (getString(R.string.button_search).equals(saveText)) {
            toAddUsername = name;
            if (TextUtils.isEmpty(name)) {
                String st = getResources().getString(R.string.Please_enter_a_username);
                To.show(st);
                return;
            }

            // TODO 从服务器获取此contact,如果不存在提示不存在此用户

            //服务器存在此用户，显示此用户和添加按钮
            searchedUserLayout.setVisibility(View.VISIBLE);
            nameText.setText(toAddUsername);

        }
    }

    /**
     * 添加contact
     *
     * @param view
     */
    public void addContact(View view) {
        if (LoveChatApp.getInstance().getUserName().equals(nameText.getText().toString())) {
            String str = getString(R.string.not_add_myself);
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", str));
            return;
        }

        if (((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().containsKey(nameText.getText().toString())) {
            //提示已在好友列表中，无需添加
            if (EMContactManager.getInstance().getBlackListUsernames().contains(nameText.getText().toString())) {
                startActivity(new Intent(this, AlertDialog.class).putExtra("msg", "此用户已是你好友(被拉黑状态)，从黑名单列表中移出即可"));
                return;
            }
            String strin = getString(R.string.This_user_is_already_your_friend);
            startActivity(new Intent(this, AlertDialog.class).putExtra("msg", strin));
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo写死了个reason，实际应该让用户手动填入
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMContactManager.getInstance().addContact(toAddUsername, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            To.show(s1);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            To.show(s2 + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    public void back(View v) {
        finish();
    }
}
