package com.techscl.ichat.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.utils.To;

import java.util.Collections;
import java.util.List;

public class GroupBlacklistActivity extends BaseGestureActivity {
    private ListView listView;
    private ProgressBar progressBar;
    private BlacklistAdapter adapter;
    private String groupId;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_group_blacklist);

        initView();

        groupId = getIntent().getStringExtra("groupId");
        // 注册上下文菜单
        registerForContextMenu(listView);
        final String st1 = getResources().getString(R.string.get_failed_please_check);
        new Thread(new Runnable() {

            public void run() {
                try {
                    List<String> blockedList = EMGroupManager.getInstance().getBlockedUsers(groupId);
                    if (blockedList != null) {
                        Collections.sort(blockedList);
                        adapter = new BlacklistAdapter(GroupBlacklistActivity.this, 1, blockedList);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (EaseMobException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            To.show(st1);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        }).start();

    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.list);
        toolbar.setTitle(R.string.blacklist);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.remove_from_blacklist, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.remove) {
            final String tobeRemoveUser = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
            // 移出黑名单
            removeOutBlacklist(tobeRemoveUser);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 移出黑民单
     *
     * @param tobeRemoveUser
     */
    void removeOutBlacklist(final String tobeRemoveUser) {
        final String st2 = getResources().getString(R.string.Removed_from_the_failure);
        try {
            // 移出黑民单
            EMGroupManager.getInstance().unblockUser(groupId, tobeRemoveUser);
            adapter.remove(tobeRemoveUser);
        } catch (EaseMobException e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run() {
                    To.show(st2);
                }
            });
        }
    }

    /**
     * adapter
     */
    private class BlacklistAdapter extends ArrayAdapter<String> {

        public BlacklistAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.row_contact, null);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(getItem(position));

            return convertView;
        }

    }

}