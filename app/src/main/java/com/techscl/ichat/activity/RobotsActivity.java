package com.techscl.ichat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMContact;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.db.UserDao;
import com.techscl.ichat.domain.RobotUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotsActivity extends BaseGestureActivity {

    public static final String TAG = RobotsActivity.class.getSimpleName();

    private ListView mListView;
    private List<RobotUser> robotList = new ArrayList<RobotUser>();
    private RobotAdapter adapter;
    private InputMethodManager inputMethodManager;
    private View progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.fragment_robots);

        initView();

    }

    /**
     * 初始化
     */
    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.robot_chat));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mListView = (ListView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                getRobotNamesFromServer();
            }
        });
        Map<String, RobotUser> robotMap = ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getRobotList();
        if (robotMap != null) {
            robotList.addAll(robotMap.values());
        } else {
            progressBar.setVisibility(View.VISIBLE);
            getRobotNamesFromServer();
        }
        adapter = new RobotAdapter(this, 1, robotList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RobotUser user = (RobotUser) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.setClass(RobotsActivity.this, ChatActivity.class);
                intent.putExtra("userId", user.getUsername());
                startActivity(intent);
            }
        });
        mListView.setOnTouchListener(new OnTouchListener() {

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
    }

    private void getRobotNamesFromServer() {
        asyncGetRobotNamesFromServer(new EMValueCallBack<List<EMContact>>() {

            @Override
            public void onSuccess(final List<EMContact> value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        Map<String, RobotUser> mMap = new HashMap<String, RobotUser>();
                        for (EMContact item : value) {
                            RobotUser user = new RobotUser();
                            user.setUsername(item.getUsername());
                            user.setNick(item.getNick());
                            user.setHeader("#");
                            mMap.put(item.getUsername(), user);
                        }
                        robotList.clear();
                        robotList.addAll(mMap.values());
                        // 存入内存
                        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setRobotList(mMap);
                        // 存入db
                        UserDao dao = new UserDao(RobotsActivity.this);
                        dao.saveRobotUser(robotList);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void asyncGetRobotNamesFromServer(final EMValueCallBack<List<EMContact>> callback) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    List<EMContact> mList = EMChatManager.getInstance().getRobotsFromServer();
                    callback.onSuccess(mList);
                } catch (EaseMobException e) {
                    e.printStackTrace();
                    callback.onError(e.getErrorCode(), e.toString());
                }
            }
        }).start();
    }

    class RobotAdapter extends ArrayAdapter<RobotUser> {

        private LayoutInflater inflater;

        public RobotAdapter(Context context, int res, List<RobotUser> robots) {
            super(context, res, robots);
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_robots, null);
            }
            ((TextView) convertView.findViewById(R.id.name)).setText(getItem(position).getNick());
            return convertView;
        }

    }
}