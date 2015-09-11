package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.utils.To;

public class NewGroupActivity extends BaseGestureActivity {
    private EditText groupNameEditText;
    private ProgressDialog progressDialog;
    private EditText introductionEditText;
    private CheckBox checkBox;
    private CheckBox memberCheckbox;
    private LinearLayout openInviteContainer;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        initView();

    }

    /**
     * 初始化
     */
    private void initView() {
        groupNameEditText = (EditText) findViewById(R.id.edit_group_name);
        introductionEditText = (EditText) findViewById(R.id.edit_group_introduction);
        checkBox = (CheckBox) findViewById(R.id.cb_public);
        memberCheckbox = (CheckBox) findViewById(R.id.cb_member_inviter);
        openInviteContainer = (LinearLayout) findViewById(R.id.ll_open_invite);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.The_new_group_chat));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    openInviteContainer.setVisibility(View.INVISIBLE);
                } else {
                    openInviteContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * @param v
     */
    public void save(View v) {
        String str6 = getResources().getString(R.string.Group_name_cannot_be_empty);
        String name = groupNameEditText.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Intent intent = new Intent(this, AlertDialog.class);
            intent.putExtra("msg", str6);
            startActivity(intent);
        } else {
            // 进通讯录选人
            startActivityForResult(new Intent(this, GroupPickContactsActivity.class).putExtra("groupName", name), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String st1 = getResources().getString(R.string.Is_to_create_a_group_chat);
        final String st2 = getResources().getString(R.string.Failed_to_create_groups);
        if (resultCode == RESULT_OK) {
            //新建群组
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(st1);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 调用sdk创建群组方法
                    String groupName = groupNameEditText.getText().toString().trim();
                    String desc = introductionEditText.getText().toString();
                    String[] members = data.getStringArrayExtra("newmembers");
                    try {
                        if (checkBox.isChecked()) {
                            //创建公开群，此种方式创建的群，可以自由加入
                            //创建公开群，此种方式创建的群，用户需要申请，等群主同意后才能加入此群
                            EMGroupManager.getInstance().createPublicGroup(groupName, desc, members, true, 200);
                        } else {
                            //创建不公开群
                            EMGroupManager.getInstance().createPrivateGroup(groupName, desc, members, memberCheckbox.isChecked(), 200);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    } catch (final EaseMobException e) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                To.show(st2 + e.getLocalizedMessage());
                            }
                        });
                    }

                }
            }).start();
        }
    }

    public void back(View view) {
        finish();
    }
}
