package com.techscl.ichat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.techscl.ichat.R;
import com.techscl.ichat.adapter.GroupAdapter;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.utils.L;

import java.util.List;

public class GroupsActivity extends BaseGestureActivity {
    public static GroupsActivity instance;
    protected List<EMGroup> grouplist;
    Handler handler = new Handler();
    private ListView groupListView;
    private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;
    private SyncListener syncListener;
    private View progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_groups);
        instance = this;

        initView();

        groupAdapter = new GroupAdapter(this, 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    // 新建群聊
                    startActivityForResult(new Intent(GroupsActivity.this, NewGroupActivity.class), 0);
                } else if (position == 2) {
                    // 添加公开群
                    startActivityForResult(new Intent(GroupsActivity.this, PublicGroupsActivity.class), 0);
                } else {
                    // 进入群聊
                    Intent intent = new Intent(GroupsActivity.this, ChatActivity.class);
                    // it is group chat
                    intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
                    intent.putExtra("groupId", groupAdapter.getItem(position - 3).getGroupId());
                    startActivityForResult(intent, 0);
                }
            }

        });
        groupListView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        progressBar = findViewById(R.id.progress_bar);

        syncListener = new SyncListener();
        HXSDKHelper.getInstance().addSyncGroupListener(syncListener);

        if (!HXSDKHelper.getInstance().isGroupsSyncedWithServer()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

        refresh();
    }

    /**
     * 初始化
     */
    private void initView() {
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        grouplist = EMGroupManager.getInstance().getAllGroups();

        groupListView = (ListView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                MainActivity.asyncFetchGroupsFromServer();
            }
        });
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.group_chat);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 进入公开群聊列表
     */
    public void onPublicGroups(View view) {
        startActivity(new Intent(this, PublicGroupsActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        grouplist = EMGroupManager.getInstance().getAllGroups();
        groupAdapter = new GroupAdapter(this, 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if (syncListener != null) {
            HXSDKHelper.getInstance().removeSyncGroupListener(syncListener);
            syncListener = null;
        }
        super.onDestroy();
        instance = null;
    }

    public void refresh() {
        if (groupListView != null && groupAdapter != null) {
            grouplist = EMGroupManager.getInstance().getAllGroups();
            groupAdapter = new GroupAdapter(GroupsActivity.this, 1,
                    grouplist);
            groupListView.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    class SyncListener implements HXSDKHelper.HXSyncListener {
        @Override
        public void onSyncSucess(final boolean success) {
            L.d("onSyncGroupsFinish success:" + success);
            runOnUiThread(new Runnable() {
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                    if (success) {
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                                progressBar.setVisibility(View.GONE);
                            }
                        }, 1000);
                    } else {
                        if (!GroupsActivity.this.isFinishing()) {
                            String s1 = getResources()
                                    .getString(
                                            R.string.Failed_to_get_group_chat_information);
                            Toast.makeText(GroupsActivity.this, s1, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }
    }
}
