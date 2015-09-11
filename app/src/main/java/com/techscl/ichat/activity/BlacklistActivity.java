package com.techscl.ichat.activity;

import android.app.ProgressDialog;
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
import android.widget.TextView;

import com.easemob.chat.EMContactManager;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.utils.To;

import java.util.Collections;
import java.util.List;

/**
 * 黑名单列表页面
 */
public class BlacklistActivity extends BaseGestureActivity {
    private ListView listView;
    private BlacklistAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);

        listView = (ListView) findViewById(R.id.list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.blacklist));
        // 从本地获取黑名单
        List<String> blacklist = EMContactManager.getInstance().getBlackListUsernames();

        // 显示黑名单列表
        if (blacklist != null) {
            Collections.sort(blacklist);
            adapter = new BlacklistAdapter(this, 1, blacklist);
            listView.setAdapter(adapter);
        }

        // 注册上下文菜单
        registerForContextMenu(listView);

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
            // 把目标user移出黑名单
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
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.be_removing));
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    // 移出黑民单
                    EMContactManager.getInstance().deleteUserFromBlackList(tobeRemoveUser);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            adapter.remove(tobeRemoveUser);
                        }
                    });
                } catch (EaseMobException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            To.show(getString(R.string.Removed_from_the_failure));
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
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
