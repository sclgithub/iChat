package com.techscl.ichat.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.techscl.ichat.R;
import com.techscl.ichat.adapter.NewFriendsMsgAdapter;
import com.techscl.ichat.applib.controller.HXSDKHelper;
import com.techscl.ichat.base.Constant;
import com.techscl.ichat.base.DemoHXSDKHelper;
import com.techscl.ichat.db.InviteMessgeDao;
import com.techscl.ichat.domain.InviteMessage;

import java.util.List;

/**
 * 申请与通知
 */
public class NewFriendsMsgActivity extends BaseGestureActivity {
    private ListView listView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        initView();

        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        //设置adapter
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);

    }

    /**
     * 初始化
     */
    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.Application_and_notify));
        listView = (ListView) findViewById(R.id.list);
    }

    public void back(View view) {
        finish();
    }


}
