package com.techscl.ichat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easemob.EMError;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.exceptions.EaseMobException;
import com.techscl.ichat.R;
import com.techscl.ichat.utils.To;

public class PublicGroupsSeachActivity extends BaseGestureActivity {
    public static EMGroup searchedGroup;
    private RelativeLayout containerLayout;
    private EditText idET;
    private TextView nameText;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_public_groups_search);

        initView();

    }

    /**
     * 初始化
     */
    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.search_pubic_group));
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        containerLayout = (RelativeLayout) findViewById(R.id.rl_searched_group);
        idET = (EditText) findViewById(R.id.et_search_id);
        nameText = (TextView) findViewById(R.id.name);

        searchedGroup = null;
    }

    /**
     * 搜索
     *
     * @param v
     */
    public void searchGroup(View v) {
        if (TextUtils.isEmpty(idET.getText())) {
            return;
        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.searching));
        pd.setCancelable(false);
        pd.show();

        new Thread(new Runnable() {

            public void run() {
                try {
                    searchedGroup = EMGroupManager.getInstance().getGroupFromServer(idET.getText().toString());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            containerLayout.setVisibility(View.VISIBLE);
                            nameText.setText(searchedGroup.getGroupName());
                        }
                    });

                } catch (final EaseMobException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            pd.dismiss();
                            searchedGroup = null;
                            containerLayout.setVisibility(View.GONE);
                            if (e.getErrorCode() == EMError.GROUP_NOT_EXIST) {
                                To.show(getResources().getString(R.string.group_not_existed));
                            } else {
                                To.show(getResources().getString(R.string.group_search_failed) + " : " + getString(R.string.connect_failuer_toast));
                            }
                        }
                    });
                }
            }
        }).start();

    }


    /**
     * 点击搜索到的群组进入群组信息页面
     *
     * @param view
     */
    public void enterToDetails(View view) {
        startActivity(new Intent(this, GroupSimpleDetailActivity.class));
    }
}
