package com.techscl.lovechat.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.easemob.chatuidemo.R;
import com.techscl.applib.controller.HXSDKHelper;
import com.techscl.lovechat.Constant;
import com.techscl.lovechat.DemoHXSDKHelper;
import com.techscl.lovechat.adapter.NewFriendsMsgAdapter;
import com.techscl.lovechat.db.InviteMessgeDao;
import com.techscl.lovechat.domain.InviteMessage;

import java.util.List;

/**
 * 申请与通知
 */
public class NewFriendsMsgActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends_msg);

        listView = (ListView) findViewById(R.id.list);
        InviteMessgeDao dao = new InviteMessgeDao(this);
        List<InviteMessage> msgs = dao.getMessagesList();
        //设置adapter
        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getContactList().get(Constant.NEW_FRIENDS_USERNAME).setUnreadMsgCount(0);

    }

    public void back(View view) {
        finish();
    }


}
