package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupInfo;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.utils.To;

public class GroupSimpleDetailActivity extends BaseGestureActivity {
    private Button btn_add_group;
    private TextView tv_admin;
    private TextView tv_name;
    private TextView tv_introduction;
    private EMGroup group;
    private String groupid;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_simle_details);

        initView();

        EMGroupInfo groupInfo = (EMGroupInfo) getIntent().getSerializableExtra("groupinfo");
        String groupname = null;
        if (groupInfo != null) {
            groupname = groupInfo.getGroupName();
            groupid = groupInfo.getGroupId();
        } else {
            group = PublicGroupsSeachActivity.searchedGroup;
            if (group == null)
                return;
            groupname = group.getGroupName();
            groupid = group.getGroupId();
        }

        tv_name.setText(groupname);


        if (group != null) {
            showGroupDetail();
            return;
        }
        new Thread(new Runnable() {

            public void run() {
                //从服务器获取详情
                try {
                    group = EMGroupManager.getInstance().getGroupFromServer(groupid);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            showGroupDetail();
                        }
                    });
                } catch (final EaseMobException e) {
                    e.printStackTrace();
                    final String st1 = getResources().getString(R.string.Failed_to_get_group_chat_information);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            To.show(st1 + e.getMessage());
                        }
                    });
                }

            }
        }).start();

    }

    /**
     * 初始化
     */
    private void initView() {
        tv_name = (TextView) findViewById(R.id.name);
        tv_admin = (TextView) findViewById(R.id.tv_admin);
        btn_add_group = (Button) findViewById(R.id.btn_add_to_group);
        tv_introduction = (TextView) findViewById(R.id.tv_introduction);
        progressBar = (ProgressBar) findViewById(R.id.loading);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Group_chat_information);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //加入群聊
    public void addToGroup(View view) {
        String st1 = getResources().getString(R.string.Is_sending_a_request);
        final String st2 = getResources().getString(R.string.Request_to_join);
        final String st3 = getResources().getString(R.string.send_the_request_is);
        final String st4 = getResources().getString(R.string.Join_the_group_chat);
        final String st5 = getResources().getString(R.string.Failed_to_join_the_group_chat);
        final ProgressDialog pd = new ProgressDialog(this);
//		getResources().getString(R.string)
        pd.setMessage(st1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    //如果是membersOnly的群，需要申请加入，不能直接join
                    if (group.isMembersOnly()) {
                        EMGroupManager.getInstance().applyJoinToGroup(groupid, st2);
                    } else {
                        EMGroupManager.getInstance().joinGroup(groupid);
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            if (group.isMembersOnly())
                                To.show(st3);
                            else
                                To.show(st4);
                            btn_add_group.setEnabled(false);
                        }
                    });
                } catch (final EaseMobException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            To.show(st5 + e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    private void showGroupDetail() {
        progressBar.setVisibility(View.INVISIBLE);
        //获取详情成功，并且自己不在群中，才让加入群聊按钮可点击
        if (!group.getMembers().contains(EMChatManager.getInstance().getCurrentUser()))
            btn_add_group.setEnabled(true);
        tv_name.setText(group.getGroupName());
        tv_admin.setText(group.getOwner());
        tv_introduction.setText(group.getDescription());
    }

    public void back(View view) {
        finish();
    }
}
